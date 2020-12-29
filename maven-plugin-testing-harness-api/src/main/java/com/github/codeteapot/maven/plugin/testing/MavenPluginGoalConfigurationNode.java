package com.github.codeteapot.maven.plugin.testing;

/**
 * Plug-in goal configuration element which has children elements.
 */
public interface MavenPluginGoalConfigurationNode extends MavenPluginGoalConfigurationElement {

  /**
   * Add a child configuration element.
   *
   * @param element Child configuration element.
   * 
   * @return The configuration node itself.
   */
  MavenPluginGoalConfigurationNode set(MavenPluginGoalConfigurationElement element);
}
