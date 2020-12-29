package com.github.codeteapot.maven.plugin.testing;

import static java.util.Objects.requireNonNull;

class MavenPluginGoalConfigurationValueImpl implements MavenPluginGoalConfigurationElement {

  private final String name;
  private final String value;

  MavenPluginGoalConfigurationValueImpl(String name, String value) {
    this.name = requireNonNull(name);
    this.value = requireNonNull(value);
  }

  @Override
  public <T> T map(MavenPluginGoalConfigurationElementMapper<T> mapper) {
    return mapper.map(name, value);
  }
}
