package com.github.codeteapot.maven.plugin.testing;

import java.util.stream.Stream;

/**
 * Plug-in goal configuration.
 */
public interface MavenPluginGoalConfiguration {

  /**
   * Add a configuration element.
   *
   * @param element Configuration element to be added.
   * 
   * @return The configuration itself.
   */
  MavenPluginGoalConfiguration set(MavenPluginGoalConfigurationElement element);

  /**
   * Map this configuration to an stream of supported configuration elements.
   *
   * @param <T> Supported type.
   * @param mapper Mapper to be used.
   * 
   * @return An stream of supported configuration elements.
   */
  <T> Stream<T> map(MavenPluginGoalConfigurationElementMapper<T> mapper);
}
