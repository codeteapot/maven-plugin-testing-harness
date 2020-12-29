package com.github.codeteapot.maven.plugin.testing;

/**
 * Plug-in goal configuration.
 * 
 * <p>Execution can be done here, so configuration step can be avoided.
 */
public interface MavenPluginGoalConfigurator extends MavenPluginGoalExecutor {

  /**
   * Set the plug-in goal configuration.
   *
   * @param configuration Configuration to be used.
   * 
   * @return The associated plug-in goal executor.
   */
  MavenPluginGoalExecutor set(MavenPluginGoalConfiguration configuration);
}
