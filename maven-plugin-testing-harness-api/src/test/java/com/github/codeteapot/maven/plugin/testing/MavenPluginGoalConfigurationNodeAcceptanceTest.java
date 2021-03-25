package com.github.codeteapot.maven.plugin.testing;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MavenPluginGoalConfigurationNodeAcceptanceTest {

  private static final String SOME_NAME = "some-name";
  private static final MavenPluginGoalConfigurationElement SOME_ELEMENT =
      new TestMavenPluginGoalConfigurationElement();
  private static final Object SOME_MAPPING_RESULT = new Object();

  private MavenPluginGoalConfigurationNode configurationNode;

  @BeforeEach
  public void setUp() {
    configurationNode = MavenPluginContext.configurationNode(SOME_NAME);
  }

  @Test
  public void mapSettedElement(@Mock MavenPluginGoalConfigurationElementMapper<Object> someMapper) {
    when(someMapper.map(SOME_NAME, singletonList(SOME_ELEMENT)))
        .thenReturn(SOME_MAPPING_RESULT);

    configurationNode.set(SOME_ELEMENT);
    Object result = configurationNode.map(someMapper);

    assertThat(result).isEqualTo(SOME_MAPPING_RESULT);
  }
}
