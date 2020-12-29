package com.github.codeteapot.maven.plugin.testing;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class MavenPluginGoalConfigurationImpl implements MavenPluginGoalConfiguration {

  private final List<MavenPluginGoalConfigurationElement> elements;

  MavenPluginGoalConfigurationImpl() {
    elements = new ArrayList<>();
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
