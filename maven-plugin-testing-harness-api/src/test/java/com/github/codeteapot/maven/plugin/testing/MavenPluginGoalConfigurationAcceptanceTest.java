package com.github.codeteapot.maven.plugin.testing;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MavenPluginGoalConfigurationAcceptanceTest {

  private static final MavenPluginGoalConfigurationElementMapper<Object> SOME_MAPPER =
      new TestMavenPluginGoalConfigurationElementMapper<>();
  private static final Object SOME_MAPPING_RESULT = new Object();

  private MavenPluginGoalConfiguration configuration;

  @BeforeEach
  public void setUp() {
    configuration = MavenPluginContext.configuration();
  }

  @Test
  public void mapSettedElement(@Mock MavenPluginGoalConfigurationElement someElement) {
    when(someElement.map(SOME_MAPPER))
        .thenReturn(SOME_MAPPING_RESULT);

    configuration.set(someElement);
    List<Object> results = configuration.map(SOME_MAPPER)
        .collect(toList());

    assertThat(results).containsExactly(SOME_MAPPING_RESULT);
  }
}
