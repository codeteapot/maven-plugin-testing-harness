package com.github.codeteapot.maven.plugin.testing.logger;

import java.util.Optional;

class TestMavenPluginLoggerMessage implements MavenPluginLoggerMessage {

  TestMavenPluginLoggerMessage() {}

  @Override
  public MavenPluginLoggerMessageLevel getLevel() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Optional<String> getContent() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Optional<Throwable> getError() {
    throw new UnsupportedOperationException();
  }
}
