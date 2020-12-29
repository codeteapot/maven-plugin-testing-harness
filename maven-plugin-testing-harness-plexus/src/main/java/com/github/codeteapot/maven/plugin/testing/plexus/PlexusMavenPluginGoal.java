package com.github.codeteapot.maven.plugin.testing.plexus;

import static java.lang.String.format;
import static java.lang.Thread.currentThread;
import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;
import static org.apache.maven.repository.internal.MavenRepositorySystemUtils.newSession;
import static org.codehaus.plexus.PlexusConstants.SCANNING_INDEX;
import static org.codehaus.plexus.util.xml.Xpp3Dom.mergeXpp3Dom;

import com.github.codeteapot.maven.plugin.testing.MavenPluginExecutionException;
import com.github.codeteapot.maven.plugin.testing.MavenPluginGoalConfiguration;
import com.github.codeteapot.maven.plugin.testing.MavenPluginGoalConfigurator;
import com.github.codeteapot.maven.plugin.testing.MavenPluginGoalExecutor;
import com.github.codeteapot.maven.plugin.testing.logger.MavenPluginLogger;
import com.github.codeteapot.maven.plugin.testing.plexus.logger.EmptyMavenPluginLogger;
import com.github.codeteapot.maven.plugin.testing.plexus.logger.PlexusMavenPluginLoggerWraper;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.DefaultMavenExecutionResult;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.execution.MojoExecutionEvent;
import org.apache.maven.execution.MojoExecutionListener;
import org.apache.maven.execution.scope.internal.MojoExecutionScope;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.PluginParameterExpressionEvaluator;
import org.apache.maven.plugin.descriptor.MojoDescriptor;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugin.descriptor.PluginDescriptorBuilder;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.apache.maven.session.scope.internal.SessionScope;
import org.codehaus.plexus.DefaultContainerConfiguration;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.PlexusContainerException;
import org.codehaus.plexus.classworlds.ClassWorld;
import org.codehaus.plexus.component.composition.CycleDetectedInComponentGraphException;
import org.codehaus.plexus.component.configurator.ComponentConfigurationException;
import org.codehaus.plexus.component.configurator.ComponentConfigurator;
import org.codehaus.plexus.component.repository.ComponentDescriptor;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.codehaus.plexus.configuration.PlexusConfiguration;
import org.codehaus.plexus.configuration.PlexusConfigurationException;
import org.codehaus.plexus.configuration.xml.XmlPlexusConfiguration;
import org.codehaus.plexus.util.InterpolationFilterReader;
import org.codehaus.plexus.util.xml.Xpp3Dom;

/*
 * https://github.com/apache/maven-plugin-testing/blob/
 * bd4dc14d3811a2f4bdc4867fdc15621992810a0d/maven-plugin-testing-harness/
 * src/main/java/org/apache/maven/plugin/testing/AbstractMojoTestCase.java
 * 
 * https://github.com/apache/maven-plugin-testing/blob/
 * bd4dc14d3811a2f4bdc4867fdc15621992810a0d/maven-plugin-testing-harness/
 * src/main/java/org/apache/maven/plugin/testing/MojoRule.java
 */
class PlexusMavenPluginGoal implements MavenPluginGoalConfigurator {

  private static final String REALM_ID = "plexus.core";
  private static final String CONTAINER_NAME = "maven";

  private final Set<PlexusInjectionCommand> injectionCommandSet;
  private final String name;
  private final File baseDir;
  private final Log log;
  private final List<PlexusConfiguration> configurations;

  PlexusMavenPluginGoal(
      Set<PlexusInjectionCommand> injectionCommandSet,
      String name,
      File baseDir,
      MavenPluginLogger logger) {
    this.injectionCommandSet = new HashSet<>(injectionCommandSet);
    this.name = name;
    this.baseDir = baseDir;
    this.log = new PlexusMavenPluginLoggerWraper(ofNullable(logger)
        .orElseGet(EmptyMavenPluginLogger::new));
    configurations = new ArrayList<>();
  }

  @Override
  public MavenPluginGoalExecutor set(MavenPluginGoalConfiguration configuration) {
    configuration.map(new PlexusMavenPluginGoalConfigurationElementMapper())
        .forEach(configurations::add);
    return this;
  }

  @Override
  public void execute() throws MavenPluginExecutionException {
    try {
      PlexusContainer container = newContainer();
      injectionCommandSet.forEach(command -> command.execute(container));
      MavenProject project = newMavenProject();
      executeMojo(
          container,
          project,
          newMojoExecution(container, name),
          newMavenSession(container, project, baseDir),
          configurations,
          log);
    } catch (IOException | PlexusContainerException | PlexusConfigurationException
        | ComponentLookupException | ComponentConfigurationException
        | CycleDetectedInComponentGraphException | MojoExecutionException
        | MojoFailureException e) {
      throw new MavenPluginExecutionException(e);
    }
  }

  private static PlexusContainer newContainer() throws PlexusContainerException {
    return new DefaultPlexusContainer(
        new DefaultContainerConfiguration()
            .setClassWorld(new ClassWorld(REALM_ID, currentThread().getContextClassLoader()))
            .setClassPathScanning(SCANNING_INDEX)
            .setAutoWiring(true)
            .setName(CONTAINER_NAME));
  }

  private static MavenProject newMavenProject() {
    MavenProject project = new MavenProject();
    project.getBuild().setDirectory("${basedir}/target");
    return project;
  }

  @SuppressWarnings("deprecation")
  private static MavenSession newMavenSession(PlexusContainer container, MavenProject project,
      File basedir) {
    MavenSession session = new MavenSession(
        container,
        newSession(),
        new DefaultMavenExecutionRequest()
            .setBaseDirectory(basedir),
        new DefaultMavenExecutionResult());
    session.setCurrentProject(project);
    session.setProjects(singletonList(project));
    return session;
  }

  private static MojoExecution newMojoExecution(PlexusContainer container, String goal)
      throws IOException, PlexusConfigurationException, CycleDetectedInComponentGraphException {
    try (Reader reader = new InterpolationFilterReader(
        new InputStreamReader(PlexusMavenPluginGoal.class
            .getResourceAsStream("/META-INF/maven/plugin.xml")),
        container.getContext().getContextData(), "${", "}")) {
      PluginDescriptor pluginDescriptor = new PluginDescriptorBuilder().build(reader);
      for (ComponentDescriptor<?> componentDescriptor : pluginDescriptor.getComponents()) {
        container.addComponentDescriptor(componentDescriptor);
      }
      return pluginDescriptor.getMojos().stream()
          .filter(mojo -> goal.equals(mojo.getGoal()))
          .findAny()
          .map(MojoExecution::new)
          .orElseThrow(() -> new IllegalArgumentException(
              format("Mojo with goal %s was not found", goal)));
    }
  }

  private static Xpp3Dom newConfiguration(MojoDescriptor mojoDescriptor) {
    return Stream.of(mojoDescriptor.getMojoConfiguration().getChildren())
        .map(child -> ofNullable(child.getAttribute("default-value"))
            .map(defaultValue -> newXpp3Dom(child.getName(), defaultValue)))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .reduce(new Xpp3Dom("configuration"), PlexusMavenPluginGoal::newXpp3DomChild);
  }

  private static Xpp3Dom newConfiguration(Plugin plugin) {
    return plugin != null ? (Xpp3Dom) plugin.getConfiguration() : new Xpp3Dom("configuration");
  }

  private static Xpp3Dom newXpp3Dom(String name, String value) {
    Xpp3Dom node = new Xpp3Dom(name);
    node.setValue(value);
    return node;
  }

  private static Xpp3Dom newXpp3DomChild(Xpp3Dom parent, Xpp3Dom child) {
    parent.addChild(child);
    return parent;
  }

  private static void executeMojo(PlexusContainer container,
      MavenProject project, MojoExecution execution, MavenSession session,
      List<PlexusConfiguration> configurations, Log log)
      throws ComponentLookupException, MojoExecutionException, MojoFailureException,
      ComponentConfigurationException {
    executeMojo(
        container,
        session,
        project,
        execution,
        container.lookup(SessionScope.class),
        configurations,
        log);
  }

  private static void executeMojo(PlexusContainer container, MavenSession session,
      MavenProject project, MojoExecution execution, SessionScope sessionScope,
      List<PlexusConfiguration> configurations, Log log) throws ComponentLookupException,
      MojoExecutionException, MojoFailureException, ComponentConfigurationException {
    try {
      sessionScope.enter();
      sessionScope.seed(MavenSession.class, session);
      executeMojo(
          container,
          session,
          project,
          execution,
          container.lookup(MojoExecutionScope.class),
          configurations,
          log);
    } finally {
      sessionScope.exit();
    }
  }

  private static void executeMojo(PlexusContainer container, MavenSession session,
      MavenProject project, MojoExecution execution, MojoExecutionScope executionScope,
      List<PlexusConfiguration> configurations, Log log) throws ComponentLookupException,
      ComponentConfigurationException, MojoExecutionException, MojoFailureException {
    try {
      executionScope.enter();
      executionScope.seed(MavenProject.class, project);
      executionScope.seed(MojoExecution.class, execution);
      Mojo mojo = lookupConfiguredMojo(
          container,
          session,
          execution,
          configurations);
      mojo.setLog(log);
      mojo.execute();
      MojoExecutionEvent event = new MojoExecutionEvent(
          session,
          project,
          execution,
          mojo);
      for (MojoExecutionListener listener : container.lookupList(MojoExecutionListener.class)) {
        listener.afterMojoExecutionSuccess(event);
      }
    } finally {
      executionScope.exit();
    }
  }

  private static Mojo lookupConfiguredMojo(PlexusContainer container, MavenSession session,
      MojoExecution execution, List<PlexusConfiguration> configurations)
      throws ComponentLookupException, ComponentConfigurationException {
    MojoDescriptor mojoDescriptor = execution.getMojoDescriptor();
    Mojo mojo = (Mojo) container.lookup(
        mojoDescriptor.getRole(),
        mojoDescriptor.getRoleHint());
    PlexusConfiguration configuration = new XmlPlexusConfiguration(
        mergeXpp3Dom(
            newConfiguration(mojoDescriptor),
            mergeXpp3Dom(
                newConfiguration(session.getCurrentProject()
                    .getPlugin(mojoDescriptor.getPluginDescriptor().getPluginLookupKey())),
                execution.getConfiguration())));
    configurations.forEach(configuration::addChild);
    container.lookup(
        ComponentConfigurator.class,
        ofNullable(mojoDescriptor.getComponentConfigurator())
            .orElse("basic"))
        .configureComponent(
            mojo,
            configuration,
            new PluginParameterExpressionEvaluator(session, execution),
            container.getContainerRealm());
    return mojo;
  }
}
