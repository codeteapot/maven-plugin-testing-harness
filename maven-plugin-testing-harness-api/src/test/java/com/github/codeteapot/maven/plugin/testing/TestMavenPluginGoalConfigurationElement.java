package com.github.codeteapot.maven.plugin.testing;

class TestMavenPluginGoalConfigurationElement
    implements MavenPluginGoalConfigurationElement {

  TestMavenPluginGoalConfigurationElement() {}

  @Override
  public <T> T map(MavenPluginGoalConfigurationElementMapper<T> mapper) {
    throw new UnsupportedOperationException();
  }
}
