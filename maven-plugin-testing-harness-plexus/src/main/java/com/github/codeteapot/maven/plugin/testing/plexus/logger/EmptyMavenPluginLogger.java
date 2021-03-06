package com.github.codeteapot.maven.plugin.testing.plexus.logger;

import com.github.codeteapot.maven.plugin.testing.logger.MavenPluginLogger;
import com.github.codeteapot.maven.plugin.testing.logger.MavenPluginLoggerMessage;

/**
 * Empty logger.
 */
public class EmptyMavenPluginLogger implements MavenPluginLogger {

  /**
   * Default constructor.
   */
  public EmptyMavenPluginLogger() {}

  /**
   * It does nothing.
   */
  @Override
  public void log(MavenPluginLoggerMessage message) {
    // It does nothing...
  }
}
