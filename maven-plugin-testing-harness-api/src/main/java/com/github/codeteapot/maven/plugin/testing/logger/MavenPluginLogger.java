package com.github.codeteapot.maven.plugin.testing.logger;

/**
 * Logger used by plug-in goal execution.
 */
public interface MavenPluginLogger {
  
  /**
   * Message logged by a plug-in goal execution.
   *
   * @param message Logged message.
   */
  void log(MavenPluginLoggerMessage message);
}
