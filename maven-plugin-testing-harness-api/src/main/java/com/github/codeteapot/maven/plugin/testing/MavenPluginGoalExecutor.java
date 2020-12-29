package com.github.codeteapot.maven.plugin.testing;

/**
 * Plug-in goal executor.
 */
public interface MavenPluginGoalExecutor {

  /**
   * Executes the associated goal.
   *
   * @throws MavenPluginExecutionException When some executor error has been occurred.
   */
  void execute() throws MavenPluginExecutionException;
}
