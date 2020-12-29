package com.github.codeteapot.maven.plugin.testing.plexus.logger;

import static java.util.Optional.ofNullable;

import com.github.codeteapot.maven.plugin.testing.logger.MavenPluginLoggerMessage;
import com.github.codeteapot.maven.plugin.testing.logger.MavenPluginLoggerMessageLevel;
import java.util.Optional;

class MavenPluginLoggerMessageImpl implements MavenPluginLoggerMessage {

  private final MavenPluginLoggerMessageLevel level;
  private final String content;
  private final Throwable error;

  public MavenPluginLoggerMessageImpl(MavenPluginLoggerMessageLevel level, String content) {
    this(level, content, null);
  }

  public MavenPluginLoggerMessageImpl(MavenPluginLoggerMessageLevel level, Throwable error) {
    this(level, null, error);
  }

  MavenPluginLoggerMessageImpl(
      MavenPluginLoggerMessageLevel level,
      String content,
      Throwable error) {
    this.level = level;
    this.content = content;
    this.error = error;
  }

  @Override
  public MavenPluginLoggerMessageLevel getLevel() {
    return level;
  }

  @Override
  public Optional<String> getContent() {
    return ofNullable(content);
  }

  @Override
  public Optional<Throwable> getError() {
    return ofNullable(error);
  }
}
