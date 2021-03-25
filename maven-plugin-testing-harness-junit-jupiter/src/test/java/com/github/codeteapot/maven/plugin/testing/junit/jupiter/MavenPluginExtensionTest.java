package com.github.codeteapot.maven.plugin.testing.junit.jupiter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import com.github.codeteapot.maven.plugin.testing.MavenPluginContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MavenPluginExtensionTest {

  private static final String PLEXUS_PLUGIN_CONTEXT_CLASS_NAME =
      "com.github.codeteapot.maven.plugin.testing.plexus.PlexusMavenPluginContext";
  private static final String TEST_PLUGIN_CONTEXT_CLASS_NAME =
      "com.github.codeteapot.maven.plugin.testing.junit.jupiter.TestMavenPluginContext";
  
  private static final MavenPluginContext TEST_MAVEN_PLUGIN_CONTEXT = new TestMavenPluginContext();

  @Mock
  private ExtensionContext extensionContext;

  @Test
  public void plexusAsDefaultPluginContext() {
    MavenPluginExtension extension = new MavenPluginExtension();

    assertThat(extension.pluginContextClassName).isEqualTo(PLEXUS_PLUGIN_CONTEXT_CLASS_NAME);
  }

  @Test
  public void createContextBeforeEachTest() throws Exception {
    MavenPluginExtension extension = new MavenPluginExtension();
    extension.pluginContextClassName = TEST_PLUGIN_CONTEXT_CLASS_NAME;
    extension.pluginContext = null;

    extension.beforeEach(extensionContext);

    assertThat(extension.pluginContext).isInstanceOf(TestMavenPluginContext.class);
  }

  @Test
  public void removeContextAfterEachTest() throws Exception {
    MavenPluginExtension extension = new MavenPluginExtension();
    extension.pluginContext = TEST_MAVEN_PLUGIN_CONTEXT;

    extension.afterEach(extensionContext);

    assertThat(extension.pluginContext).isNull();
  }

  @Test
  public void supportsMavenPluginContextParameter(
      @Mock ParameterContext parameterContext) throws Exception {
    when(parameterContext.getParameter())
        .thenReturn(getClass()
            .getDeclaredMethod("parameterSupport", MavenPluginContext.class)
            .getParameters()[0]);
    MavenPluginExtension extension = new MavenPluginExtension();

    boolean supports = extension.supportsParameter(parameterContext, extensionContext);

    assertThat(supports).isTrue();
  }
  
  @Test
  public void resolveMavenPluginContextParameter(@Mock ParameterContext parameterContext) {
    MavenPluginExtension extension = new MavenPluginExtension();
    extension.pluginContext = TEST_MAVEN_PLUGIN_CONTEXT;
    
    Object resolvedParameter = extension.resolveParameter(parameterContext, extensionContext);
    
    assertThat(resolvedParameter).isEqualTo(TEST_MAVEN_PLUGIN_CONTEXT);
  }

  @SuppressWarnings("unused")
  private void parameterSupport(MavenPluginContext mavenPluginContext) {
    // Nothing to be done...
  }
}