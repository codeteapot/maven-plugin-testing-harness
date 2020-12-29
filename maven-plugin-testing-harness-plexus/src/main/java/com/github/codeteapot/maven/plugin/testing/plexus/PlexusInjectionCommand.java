package com.github.codeteapot.maven.plugin.testing.plexus;

import org.codehaus.plexus.PlexusContainer;

interface PlexusInjectionCommand {

  void execute(PlexusContainer container);
}
