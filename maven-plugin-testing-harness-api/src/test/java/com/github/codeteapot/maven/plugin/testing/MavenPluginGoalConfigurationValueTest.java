package com.github.codeteapot.maven.plugin.testing;

import static com.github.codeteapot.maven.plugin.testing.MavenPluginContext.configurationValue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MavenPluginGoalConfigurationValueTest {

  private static final String SOME_NAME = "some-name";
  private static final String SOME_VALUE = "some-value";
  private static final Object SOME_MAPPING_RESULT = new Object();

  private MavenPluginGoalConfigurationElement configurationValue;

  @BeforeEach
  public void setUp() {
    configurationValue = configurationValue(SOME_NAME, SOME_VALUE);
  }

  @Test
  public void mapThroughGivenMapper(
      @Mock MavenPluginGoalConfigurationElementMapper<Object> someMapper) {
    when(someMapper.map(SOME_NAME, SOME_VALUE))
        .thenReturn(SOME_MAPPING_RESULT);

    Object result = configurationValue.map(someMapper);

    assertThat(result).isEqualTo(SOME_MAPPING_RESULT);
  }
}
