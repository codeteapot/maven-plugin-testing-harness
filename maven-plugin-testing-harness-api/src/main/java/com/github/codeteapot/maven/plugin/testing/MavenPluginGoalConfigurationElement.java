package com.github.codeteapot.maven.plugin.testing;

/**
 * Plug-in goal configuration element.
 */
public interface MavenPluginGoalConfigurationElement {

  /**
   * Map to a supported element.
   *
   * @param <T> Supported type.
   * @param mapper Element mapper.
   * 
   * @return The supported element.
   */
  <T> T map(MavenPluginGoalConfigurationElementMapper<T> mapper);
}
