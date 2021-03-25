package com.github.codeteapot.maven.plugin.testing;

import java.util.List;

class TestMavenPluginGoalConfigurationElementMapper<T>
    implements MavenPluginGoalConfigurationElementMapper<T> {

  TestMavenPluginGoalConfigurationElementMapper() {}

  @Override
  public T map(String name, List<MavenPluginGoalConfigurationElement> elements) {
    throw new UnsupportedOperationException();
  }

  @Override
  public T map(String name, String value) {
    throw new UnsupportedOperationException();
  }
}
