package com.github.codeteapot.maven.plugin.testing.plexus;

import static java.nio.file.Files.write;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import java.io.File;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

public class TestGoalMojo extends AbstractMojo {

  private OperationFactory operationFactory;
  private boolean skip;
  private List<Long> operands;
  private File baseDir;

  public TestGoalMojo() {
    operationFactory = null;
    skip = false;
    operands = null;
    baseDir = null;
  }

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    try {
      if (!skip) {
        write(new File(baseDir, "output.txt").toPath(),
            Stream.of(operationFactory.getOperation()
                .apply(ofNullable(operands)
                    .orElseGet(ArrayList::new)))
                .map(Object::toString)
                .collect(toList()),
            StandardOpenOption.CREATE);
      }
      if (getLog().isDebugEnabled()) {
        getLog().debug("debug message");
        getLog().debug(new Exception("debug error"));
        getLog().debug("debug message", new Exception("debug error"));
      }
      if (getLog().isErrorEnabled()) {
        getLog().error("error message");
        getLog().error(new Exception("error error"));
        getLog().error("error message", new Exception("error error"));
      }
      if (getLog().isWarnEnabled()) {
        getLog().warn("warn message");
        getLog().warn(new Exception("warn error"));
        getLog().warn("warn message", new Exception("warn error"));
      }
      if (getLog().isInfoEnabled()) {
        getLog().info("info message");
        getLog().info(new Exception("info error"));
        getLog().info("info message", new Exception("info error"));
      }
    } catch (IOException e) {
      throw new MojoExecutionException(e.getMessage());
    }
  }
}
