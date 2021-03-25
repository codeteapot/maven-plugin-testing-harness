package com.github.codeteapot.maven.plugin.testing;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

class MavenPluginGoalConfigurationNodeImpl implements MavenPluginGoalConfigurationNode {

  private final String name;
  private final List<MavenPluginGoalConfigurationElement> elements;

  MavenPluginGoalConfigurationNodeImpl(String name) {
    this(name, new ArrayList<>());
  }

  MavenPluginGoalConfigurationNodeImpl(
      String name,
      List<MavenPluginGoalConfigurationElement> elements) {
    this.name = requireNonNull(name);
    this.elements = requireNonNull(elements);
  }

  @Override
  public MavenPluginGoalConfigurationNode set(MavenPluginGoalConfigurationElement element) {
    elements.add(element);
    return this;
  }

  @Override
  public <T> T map(MavenPluginGoalConfigurationElementMapper<T> mapper) {
    return mapper.map(name, elements);
  }
}
