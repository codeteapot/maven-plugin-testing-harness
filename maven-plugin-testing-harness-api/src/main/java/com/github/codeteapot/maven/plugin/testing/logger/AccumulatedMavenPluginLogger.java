package com.github.codeteapot.maven.plugin.testing.logger;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Plug-in goal execution logger that accumulates all logged messages.
 */
public class AccumulatedMavenPluginLogger implements MavenPluginLogger {

  private final List<MavenPluginLoggerMessage> accumulated;

  /**
   * Empty accumulation logger.
   */
  public AccumulatedMavenPluginLogger() {
    this(new ArrayList<>());
  }
  
  AccumulatedMavenPluginLogger(List<MavenPluginLoggerMessage> accumulated) {
    this.accumulated = requireNonNull(accumulated);
  }

  /**
   * Accumulates a new logger message.
   */
  @Override
  public void log(MavenPluginLoggerMessage message) {
    accumulated.add(message);
  }

  /**
   * Get accumulated logger messages.
   *
   * @return The accumulation list, sorted by arrival.
   */
  public List<MavenPluginLoggerMessage> getAccumulated() {
    return unmodifiableList(accumulated);
  }
}
