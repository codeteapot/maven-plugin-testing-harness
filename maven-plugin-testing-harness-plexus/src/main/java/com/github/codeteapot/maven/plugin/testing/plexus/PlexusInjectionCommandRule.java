package com.github.codeteapot.maven.plugin.testing.plexus;

import org.codehaus.plexus.PlexusContainer;

class PlexusInjectionCommandRule implements PlexusInjectionCommand {

  private final Object component;
  private final String role;

  PlexusInjectionCommandRule(Object component, String role) {
    this.component = component;
    this.role = role;
  }

  @Override
  public void execute(PlexusContainer container) {
    container.addComponent(component, role);
  }
}
