package com.github.codeteapot.maven.plugin.testing.plexus.logger;

import static com.github.codeteapot.maven.plugin.testing.logger.MavenPluginLoggerMessageLevel.LOG_DEBUG;
import static com.github.codeteapot.maven.plugin.testing.logger.MavenPluginLoggerMessageLevel.LOG_ERROR;
import static com.github.codeteapot.maven.plugin.testing.logger.MavenPluginLoggerMessageLevel.LOG_INFO;
import static com.github.codeteapot.maven.plugin.testing.logger.MavenPluginLoggerMessageLevel.LOG_WARN;
import static java.util.Objects.requireNonNull;

import com.github.codeteapot.maven.plugin.testing.logger.MavenPluginLogger;
import org.apache.maven.plugin.logging.Log;

/**
 * Wrapper of Plexus logger.
 */
public class PlexusMavenPluginLoggerWraper implements Log {

  private final MavenPluginLogger logger;

  /**
   * Wrapper constructor.
   */
  public PlexusMavenPluginLoggerWraper(MavenPluginLogger logger) {
    this.logger = requireNonNull(logger);
  }

  @Override
  public boolean isErrorEnabled() {
    return true;
  }

  @Override
  public boolean isWarnEnabled() {
    return true;
  }

  @Override
  public boolean isInfoEnabled() {
    return true;
  }

  @Override
  public boolean isDebugEnabled() {
    return true;
  }

  @Override
  public void error(CharSequence content) {
    logger.log(new MavenPluginLoggerMessageImpl(LOG_ERROR, content.toString()));
  }

  @Override
  public void error(CharSequence content, Throwable error) {
    logger.log(new MavenPluginLoggerMessageImpl(LOG_ERROR, content.toString(), error));
  }

  @Override
  public void error(Throwable error) {
    logger.log(new MavenPluginLoggerMessageImpl(LOG_ERROR, error));
  }

  @Override
  public void warn(CharSequence content) {
    logger.log(new MavenPluginLoggerMessageImpl(LOG_WARN, content.toString()));
  }

  @Override
  public void warn(CharSequence content, Throwable error) {
    logger.log(new MavenPluginLoggerMessageImpl(LOG_WARN, content.toString(), error));
  }

  @Override
  public void warn(Throwable error) {
    logger.log(new MavenPluginLoggerMessageImpl(LOG_WARN, error));
  }

  @Override
  public void info(CharSequence content) {
    logger.log(new MavenPluginLoggerMessageImpl(LOG_INFO, content.toString()));
  }

  @Override
  public void info(CharSequence content, Throwable error) {
    logger.log(new MavenPluginLoggerMessageImpl(LOG_INFO, content.toString(), error));
  }

  @Override
  public void info(Throwable error) {
    logger.log(new MavenPluginLoggerMessageImpl(LOG_INFO, error));
  }

  @Override
  public void debug(CharSequence content) {
    logger.log(new MavenPluginLoggerMessageImpl(LOG_DEBUG, content.toString()));
  }

  @Override
  public void debug(CharSequence content, Throwable error) {
    logger.log(new MavenPluginLoggerMessageImpl(LOG_DEBUG, content.toString(), error));
  }

  @Override
  public void debug(Throwable error) {
    logger.log(new MavenPluginLoggerMessageImpl(LOG_DEBUG, error));
  }
}
