package com.github.codeteapot.maven.plugin.testing.plexus;

import static com.github.codeteapot.maven.plugin.testing.MavenPluginContext.configuration;
import static com.github.codeteapot.maven.plugin.testing.MavenPluginContext.configurationNode;
import static com.github.codeteapot.maven.plugin.testing.MavenPluginContext.configurationValue;
import static com.github.codeteapot.maven.plugin.testing.logger.MavenPluginLoggerMessageLevel.LOG_DEBUG;
import static com.github.codeteapot.maven.plugin.testing.logger.MavenPluginLoggerMessageLevel.LOG_ERROR;
import static com.github.codeteapot.maven.plugin.testing.logger.MavenPluginLoggerMessageLevel.LOG_INFO;
import static com.github.codeteapot.maven.plugin.testing.logger.MavenPluginLoggerMessageLevel.LOG_WARN;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.ServiceLoader.load;
import static java.util.stream.StreamSupport.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;
import com.github.codeteapot.maven.plugin.testing.MavenPluginContext;
import com.github.codeteapot.maven.plugin.testing.logger.MavenPluginLogger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("integration")
@ExtendWith(MockitoExtension.class)
public class PlexusMavenPluginContextTest {

  private static final String PLEXUS_PLUGIN_CONTEXT_NAME = "plexus";
  
  private static final String OPERATION_FACTORY_ROLE =
      "com.github.codeteapot.maven.plugin.testing.plexus.OperationFactory";

  private static final String TEST_DESCRIPTOR_PATH =
      "/com/github/codeteapot/maven/plugin/testing/plexus/test-plugin.xml";
  private static final String TEST_BAD_DESCRIPTOR_PATH =
      "/com/github/codeteapot/maven/plugin/testing/plexus/test-bad-plugin.xml";

  private static final List<Long> EMPTY_OPERANDS = emptyList();
  private static final long ZERO_RESULT = 0L;

  private static final long SOME_OPERAND = 1L;
  private static final List<Long> SOME_OPERANDS = singletonList(SOME_OPERAND);
  private static final long SOME_RESULT = 2L;

  private static final String SOME_OPERAND_VALUE = "1";


  private PlexusMavenPluginContext context;

  @Mock
  private OperationFactory operationFactory;

  @Mock
  private Operation operation;

  private File outputFile;

  @BeforeEach
  public void setUp(@TempDir File baseDir) {
    context = stream(load(MavenPluginContext.class).spliterator(), false)
        .filter(ctx -> ctx.getName().equals(PLEXUS_PLUGIN_CONTEXT_NAME))
        .findAny()
        .map(PlexusMavenPluginContext.class::cast)
        .get();
    context.setBaseDir(baseDir);
    context.inject(operationFactory, OPERATION_FACTORY_ROLE);

    outputFile = new File(baseDir, "output.txt");
  }

  @Test
  public void executeAndApplyOperation() throws Exception {
    context.descriptorPath = TEST_DESCRIPTOR_PATH;
    when(operationFactory.getOperation())
        .thenReturn(operation);
    when(operation.apply(SOME_OPERANDS))
        .thenReturn(SOME_RESULT);

    context.goal("test")
        .set(configuration()
            .set(configurationNode("operands")
                .set(configurationValue("operand", SOME_OPERAND_VALUE))))
        .execute();
    long result = getResult().longValue();

    assertThat(result).isEqualTo(SOME_RESULT);
  }

  @Test
  public void executeAndSkip() throws Exception {
    context.descriptorPath = TEST_DESCRIPTOR_PATH;
    context.goal("test")
        .set(configuration()
            .set(configurationValue("skip", "true")))
        .execute();
    boolean missingOutputFile = isMissingOutputFile();

    assertThat(missingOutputFile).isTrue();
  }

  @Test
  public void executeUsingAllLoggerCapabilities(@Mock MavenPluginLogger logger) throws Exception {
    context.descriptorPath = TEST_DESCRIPTOR_PATH;
    when(operationFactory.getOperation())
        .thenReturn(operation);
    when(operation.apply(EMPTY_OPERANDS))
        .thenReturn(ZERO_RESULT);

    context.setLogger(logger);
    context.goal("test")
        .execute();

    InOrder inOrder = inOrder(logger);
    inOrder.verify(logger).log(argThat(message -> message.getLevel() == LOG_DEBUG
        && message.getContent()
            .map(value -> "debug message".equals(value))
            .orElse(false)
        && !message.getError().isPresent()));
    inOrder.verify(logger).log(argThat(message -> message.getLevel() == LOG_DEBUG
        && !message.getContent().isPresent()
        && message.getError()
            .map(error -> "debug error".equals(error.getMessage()))
            .orElse(false)));
    inOrder.verify(logger).log(argThat(message -> message.getLevel() == LOG_DEBUG
        && message.getContent()
            .map(value -> "debug message".equals(value))
            .orElse(false)
        && message.getError()
            .map(error -> "debug error".equals(error.getMessage()))
            .orElse(false)));
    inOrder.verify(logger).log(argThat(message -> message.getLevel() == LOG_ERROR
        && message.getContent()
            .map(value -> "error message".equals(value))
            .orElse(false)
        && !message.getError().isPresent()));
    inOrder.verify(logger).log(argThat(message -> message.getLevel() == LOG_ERROR
        && !message.getContent().isPresent()
        && message.getError()
            .map(error -> "error error".equals(error.getMessage()))
            .orElse(false)));
    inOrder.verify(logger).log(argThat(message -> message.getLevel() == LOG_ERROR
        && message.getContent()
            .map(value -> "error message".equals(value))
            .orElse(false)
        && message.getError()
            .map(error -> "error error".equals(error.getMessage()))
            .orElse(false)));
    inOrder.verify(logger).log(argThat(message -> message.getLevel() == LOG_WARN
        && message.getContent()
            .map(value -> "warn message".equals(value))
            .orElse(false)
        && !message.getError().isPresent()));
    inOrder.verify(logger).log(argThat(message -> message.getLevel() == LOG_WARN
        && !message.getContent().isPresent()
        && message.getError()
            .map(error -> "warn error".equals(error.getMessage()))
            .orElse(false)));
    inOrder.verify(logger).log(argThat(message -> message.getLevel() == LOG_WARN
        && message.getContent()
            .map(value -> "warn message".equals(value))
            .orElse(false)
        && message.getError()
            .map(error -> "warn error".equals(error.getMessage()))
            .orElse(false)));
    inOrder.verify(logger).log(argThat(message -> message.getLevel() == LOG_INFO
        && message.getContent()
            .map(value -> "info message".equals(value))
            .orElse(false)
        && !message.getError().isPresent()));
    inOrder.verify(logger).log(argThat(message -> message.getLevel() == LOG_INFO
        && !message.getContent().isPresent()
        && message.getError()
            .map(error -> "info error".equals(error.getMessage()))
            .orElse(false)));
    inOrder.verify(logger).log(argThat(message -> message.getLevel() == LOG_INFO
        && message.getContent()
            .map(value -> "info message".equals(value))
            .orElse(false)
        && message.getError()
            .map(error -> "info error".equals(error.getMessage()))
            .orElse(false)));
  }

  @Test
  public void executeWithErrorWhenLoadingPlugin() throws Exception {
    context.descriptorPath = TEST_BAD_DESCRIPTOR_PATH;

    Throwable e = catchThrowable(() -> context.goal("test")
        .execute());

    assertThat(e).isNotNull();
  }

  @Test
  public void executeWithErrorWhenLookingForGoal() throws Exception {
    context.descriptorPath = TEST_DESCRIPTOR_PATH;

    Throwable e = catchThrowable(() -> context.goal("test-bad")
        .execute());

    assertThat(e).isNotNull();
  }

  private boolean isMissingOutputFile() {
    return !outputFile.exists();
  }

  private Number getResult() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
      return reader.lines()
          .findAny()
          .map(Double::parseDouble)
          .orElseThrow(() -> new IllegalStateException("Result is not available"));
    }
  }
}
