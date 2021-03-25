package com.github.codeteapot.maven.plugin.testing;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class MavenPluginGoalConfigurationImpl implements MavenPluginGoalConfiguration {

  private final List<MavenPluginGoalConfigurationElement> elements;

  MavenPluginGoalConfigurationImpl() {
    this(new ArrayList<>());
  }

  MavenPluginGoalConfigurationImpl(List<MavenPluginGoalConfigurationElement> elements) {
    this.elements = requireNonNull(elements);
  }

  @Override
  public MavenPluginGoalConfigurationImpl set(MavenPluginGoalConfigurationElement element) {
    elements.add(element);
    return this;
  }

  @Override
  public <T> Stream<T> map(MavenPluginGoalConfigurationElementMapper<T> mapper) {
    return elements.stream()
        .map(element -> element.map(mapper));
  }
}
