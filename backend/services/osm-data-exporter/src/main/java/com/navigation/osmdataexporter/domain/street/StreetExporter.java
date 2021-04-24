package com.navigation.osmdataexporter.domain.street;

import com.navigation.parser.loader.OSMLoader;
import com.navigation.parser.provider.OSMProvider;

class StreetExporter {

  private final OSMProvider osmProvider;

  public StreetExporter(OSMProvider osmProvider) {
    this.osmProvider = osmProvider;
  }

  public void export() {}
}
