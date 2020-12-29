package com.github.codeteapot.maven.plugin.testing.plexus;

import com.github.codeteapot.maven.plugin.testing.MavenPluginGoalConfigurationElement;
import com.github.codeteapot.maven.plugin.testing.MavenPluginGoalConfigurationElementMapper;
import java.util.List;
import org.codehaus.plexus.configuration.PlexusConfiguration;
import org.codehaus.plexus.configuration.xml.XmlPlexusConfiguration;

class PlexusMavenPluginGoalConfigurationElementMapper
    implements MavenPluginGoalConfigurationElementMapper<PlexusConfiguration> {

  @Override
  public PlexusConfiguration map(String name, List<MavenPluginGoalConfigurationElement> elements) {
    PlexusConfiguration config = new XmlPlexusConfiguration(name);
    elements.stream()
        .map(element -> element.map(this))
        .forEach(config::addChild);
    return config;
  }

  @Override
  public PlexusConfiguration map(String name, String value) {
    PlexusConfiguration config = new XmlPlexusConfiguration(name);
    config.setValue(value);
    return config;
  }
}
