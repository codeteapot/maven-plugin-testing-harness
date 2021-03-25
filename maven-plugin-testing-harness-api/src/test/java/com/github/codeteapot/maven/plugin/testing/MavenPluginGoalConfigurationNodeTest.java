package com.github.codeteapot.maven.plugin.testing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MavenPluginGoalConfigurationNodeTest {

  private static final String SOME_NAME = "some-name";
  private static final MavenPluginGoalConfigurationElement SOME_ELEMENT =
      new TestMavenPluginGoalConfigurationElement();
  private static final Object SOME_MAPPING_RESULT = new Object();

  private List<MavenPluginGoalConfigurationElement> elements;

  private MavenPluginGoalConfigurationNode configurationNode;

  @BeforeEach
  public void setUp() {
    elements = new ArrayList<>();
    configurationNode = new MavenPluginGoalConfigurationNodeImpl(SOME_NAME, elements);
  }

  @Test
  public void setElement() {
    configurationNode.set(SOME_ELEMENT);

    assertThat(elements).containsExactly(SOME_ELEMENT);
  }

  @Test
  public void mapThroughGivenMapper(
      @Mock MavenPluginGoalConfigurationElementMapper<Object> someMapper) {
    elements.add(SOME_ELEMENT);
    when(someMapper.map(SOME_NAME, elements))
        .thenReturn(SOME_MAPPING_RESULT);

    Object result = configurationNode.map(someMapper);

    assertThat(result).isEqualTo(SOME_MAPPING_RESULT);
  }
}
