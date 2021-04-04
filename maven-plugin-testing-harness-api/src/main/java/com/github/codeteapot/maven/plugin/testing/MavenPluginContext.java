package com.github.codeteapot.maven.plugin.testing;

import com.github.codeteapot.maven.plugin.testing.logger.MavenPluginLogger;
import java.io.File;

/**
 * Maven plug-in testing context.
 */
public interface MavenPluginContext {

  /**
   * Name used to determine what context implementation must be used.
   *
   * @return Implementation name.
   */
  String getName();

  /**
   * Set the base directory used where plug-in is executed.
   *
   * @param baseDir The new base directory. May be {@code null} for default one.
   */
  void setBaseDir(File baseDir);

  /**
   * Set the logger used by the plug-in execution.
   *
   * @param logger The new logger.
   */
  void setLogger(MavenPluginLogger logger);

  /**
   * Injects a Plexus component with the given role.
   *
   * @param component Component to be injected.
   * @param role Role for component lookup.
   */
  void inject(Object component, String role);

  /**
   * Initiates a plug-in goal configuration for execution.
   *
   * @param name The name of the plug-in goal.
   *
   * @return A configurator for the wanted plug-in goal.
   */
  MavenPluginGoalConfigurator goal(String name);

  /**
   * Instantiates a goal configuration.
   *
   * @return The goal configuration instance.
   */
  public static MavenPluginGoalConfiguration configuration() {
    return new MavenPluginGoalConfigurationImpl();
  }

  /**
   * Instantiates a goal configuration node.
   *
   * @param name Name of the configuration node.
   * 
   * @return The goal configuration instance node.
   */
  public static MavenPluginGoalConfigurationNode configurationNode(String name) {
    return new MavenPluginGoalConfigurationNodeImpl(name);
  }

  /**
   * Instantiates a goal configuration element.
   *
   * @param name Name of the configuration element.
   * @param value Value of the configuration element.
   * 
   * @return The goal configuration instance element.
   */
  public static MavenPluginGoalConfigurationElement configurationValue(String name, String value) {
    return new MavenPluginGoalConfigurationValueImpl(name, value);
  }
}
