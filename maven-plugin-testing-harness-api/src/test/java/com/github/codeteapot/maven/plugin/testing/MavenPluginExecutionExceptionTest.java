package com.github.codeteapot.maven.plugin.testing;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class MavenPluginExecutionExceptionTest {

  private static final Throwable SOME_CAUSE = new Exception();

  @Test
  public void withSomeCause() {
    MavenPluginExecutionException exception = new MavenPluginExecutionException(SOME_CAUSE);

    assertThat(exception).hasCause(SOME_CAUSE);
  }
}
