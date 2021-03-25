package com.github.codeteapot.maven.plugin.testing.junit.jupiter;

import com.github.codeteapot.maven.plugin.testing.MavenPluginContext;
import com.github.codeteapot.maven.plugin.testing.MavenPluginGoalConfigurator;
import com.github.codeteapot.maven.plugin.testing.logger.MavenPluginLogger;
import java.io.File;

class TestMavenPluginContext implements MavenPluginContext {

  TestMavenPluginContext() {}

  @Override
  public void setBaseDir(File baseDir) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setLogger(MavenPluginLogger logger) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void inject(Object component, String role) {
    throw new UnsupportedOperationException();
  }

  @Override
  public MavenPluginGoalConfigurator goal(String name) {
    throw new UnsupportedOperationException();
  }
}
