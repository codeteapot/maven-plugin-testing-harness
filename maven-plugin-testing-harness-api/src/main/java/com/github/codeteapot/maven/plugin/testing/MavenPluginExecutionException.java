package com.github.codeteapot.maven.plugin.testing;

/**
 * Exception occurred when an error has been occurred while executing a Maven goal.
 */
public class MavenPluginExecutionException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Execution exception associated to an underlying cause.
   *
   * @param cause Exception cause.
   */
  public MavenPluginExecutionException(Throwable cause) {
    super(cause);
  }
}
