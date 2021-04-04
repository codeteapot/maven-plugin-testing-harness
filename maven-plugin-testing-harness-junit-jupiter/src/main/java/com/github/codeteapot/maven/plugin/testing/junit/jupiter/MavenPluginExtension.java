package com.github.codeteapot.maven.plugin.testing.junit.jupiter;

import static java.util.ServiceLoader.load;
import static java.util.stream.StreamSupport.stream;

import com.github.codeteapot.maven.plugin.testing.MavenPluginContext;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * Extension that holds a Maven plug-in context for testing purposes.
 *
 * @see MavenPluginContext
 */
public final class MavenPluginExtension
    implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

  // private static final Namespace MAVEN_PLUGIN = create(
  // "com.github.codeteapot.maven.test");

  String pluginContextName;
  MavenPluginContext pluginContext;

  /**
   * Default constructor.
   */
  public MavenPluginExtension() {
    pluginContextName = null;
    pluginContext = null;
  }

  /**
   * Creates a plug-in context to be handled.
   */
  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    pluginContext = stream(load(MavenPluginContext.class).spliterator(), false)
        .filter(this::pluginContextMatch)
        .findAny()
        .orElseThrow(() -> new IllegalStateException("Maven plugin context is not available"));
  }

  /**
   * Discards the handled plug-in context.
   */
  @Override
  public void afterEach(ExtensionContext context) throws Exception {
    pluginContext = null;
  }

  /**
   * Supports a {@link MavenPluginContext} parameter.
   */
  @Override
  public boolean supportsParameter(ParameterContext parameterContext,
      ExtensionContext extensionContext) throws ParameterResolutionException {
    return parameterContext.getParameter()
        .getType()
        .isAssignableFrom(MavenPluginContext.class);
  }

  /**
   * Resolve the handled plug-in context as parameter.
   */
  @Override
  public Object resolveParameter(ParameterContext parameterContext,
      ExtensionContext extensionContext) throws ParameterResolutionException {
    return pluginContext;
  }

  private boolean pluginContextMatch(MavenPluginContext context) {
    return pluginContextName == null || context.getName().equals(pluginContextName);
  }
}
