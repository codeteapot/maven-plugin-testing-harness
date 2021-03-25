package com.github.codeteapot.maven.plugin.testing.logger;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class MavenPluginLoggerMessageLevelTest {

  @Test
  public void availableLevels() {
    assertThat(MavenPluginLoggerMessageLevel.values())
        .hasSize(4)
        .contains(MavenPluginLoggerMessageLevel.LOG_INFO)
        .contains(MavenPluginLoggerMessageLevel.LOG_WARN)
        .contains(MavenPluginLoggerMessageLevel.LOG_ERROR)
        .contains(MavenPluginLoggerMessageLevel.LOG_DEBUG);
  }
}
