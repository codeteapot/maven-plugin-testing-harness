package com.github.codeteapot.maven.plugin.testing.logger;

import java.util.Optional;

/**
 * Logger message of plug-in goal execution.
 */
public interface MavenPluginLoggerMessage {
  
  /**
   * Logger level.
   *
   * @return The logger level.
   */
  MavenPluginLoggerMessageLevel getLevel();
  
  /**
   * Optional message content.
   *
   * @return The message content.
   */
  Optional<String> getContent();
  
  /**
   * Optional message error.
   *
   * @return The message error.
   */
  Optional<Throwable> getError();
}
