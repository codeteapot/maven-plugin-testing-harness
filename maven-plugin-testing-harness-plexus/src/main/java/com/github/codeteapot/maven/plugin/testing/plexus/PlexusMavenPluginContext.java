package com.github.codeteapot.maven.plugin.testing.plexus;

import com.github.codeteapot.maven.plugin.testing.MavenPluginContext;
import com.github.codeteapot.maven.plugin.testing.MavenPluginGoalConfigurator;
import com.github.codeteapot.maven.plugin.testing.logger.MavenPluginLogger;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Plexus implementation of {@code MavenPluginContext}.
 */
public class PlexusMavenPluginContext implements MavenPluginContext {

  private static final String DESCRIPTOR_PATH = "/META-INF/maven/plugin.xml";
  
  private final Set<PlexusInjectionCommand> injectionCommandSet;
  private File baseDir;
  private MavenPluginLogger logger;
  
  String descriptorPath;

  /**
   * Default constructor.
   */
  public PlexusMavenPluginContext() {
    injectionCommandSet = new HashSet<>();
    baseDir = null;
    logger = null;
    descriptorPath = DESCRIPTOR_PATH;
  }

  @Override
  public void setBaseDir(File baseDir) {
    this.baseDir = baseDir;
  }
  
  @Override
  public void setLogger(MavenPluginLogger logger) {
    this.logger = logger;
  }

  @Override
  public void inject(Object component, String role) {
    injectionCommandSet.add(new PlexusInjectionCommandRule(component, role));
  }

  @Override
  public MavenPluginGoalConfigurator goal(String name) {
    return new PlexusMavenPluginGoal(descriptorPath, injectionCommandSet, name, baseDir, logger);
  }
}
