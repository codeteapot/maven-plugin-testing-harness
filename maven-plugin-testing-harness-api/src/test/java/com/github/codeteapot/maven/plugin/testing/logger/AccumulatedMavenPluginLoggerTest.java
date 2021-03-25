package com.github.codeteapot.maven.plugin.testing.logger;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccumulatedMavenPluginLoggerTest {

  private static final MavenPluginLoggerMessage SOME_MESSAGE = new TestMavenPluginLoggerMessage();

  private List<MavenPluginLoggerMessage> accumulated;

  private AccumulatedMavenPluginLogger logger;

  @BeforeEach
  public void setUp() {
    accumulated = new ArrayList<>();
    logger = new AccumulatedMavenPluginLogger(accumulated);
  }

  @Test
  public void getAccumulated() {
    accumulated.add(SOME_MESSAGE);

    List<MavenPluginLoggerMessage> result = logger.getAccumulated();

    assertThat(result).containsExactly(SOME_MESSAGE);
  }

  @Test
  public void logMessage() {
    logger.log(SOME_MESSAGE);

    assertThat(accumulated).containsExactly(SOME_MESSAGE);
  }
}
