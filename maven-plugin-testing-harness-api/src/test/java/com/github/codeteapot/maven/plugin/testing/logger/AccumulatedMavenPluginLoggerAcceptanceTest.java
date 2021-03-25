package com.github.codeteapot.maven.plugin.testing.logger;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccumulatedMavenPluginLoggerAcceptanceTest {

  private static final MavenPluginLoggerMessage SOME_MESSAGE = new TestMavenPluginLoggerMessage();

  private AccumulatedMavenPluginLogger logger;

  @BeforeEach
  public void setUp() {
    logger = new AccumulatedMavenPluginLogger();
  }

  @Test
  public void logAndGetAccumulated() {
    logger.log(SOME_MESSAGE);
    List<MavenPluginLoggerMessage> result = logger.getAccumulated();

    assertThat(result).containsExactly(SOME_MESSAGE);
  }
}
