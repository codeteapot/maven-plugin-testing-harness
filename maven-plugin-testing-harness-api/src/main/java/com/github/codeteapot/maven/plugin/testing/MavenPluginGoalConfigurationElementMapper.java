package com.github.codeteapot.maven.plugin.testing;

import java.util.List;

/**
 * Mapper that converts a configuration element to a supported one.
 *
 * @param <T> Supported type.
 */
public interface MavenPluginGoalConfigurationElementMapper<T> {

  /**
   * Does a mapping of a configuration node.
   *
   * @param name Element name.
   * @param elements Children elements.
   * 
   * @return The supported configuration element node.
   */
  T map(String name, List<MavenPluginGoalConfigurationElement> elements);

  /**
   * Does mapping of a configuration element value.
   *
   * @param name Element name.
   * @param value Element value.
   * 
   * @return The supported configuration element value.
   */
  T map(String name, String value);
}
