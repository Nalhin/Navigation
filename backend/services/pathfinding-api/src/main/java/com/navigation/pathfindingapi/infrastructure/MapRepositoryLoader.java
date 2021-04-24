package com.navigation.pathfindingapi.infrastructure;

import com.navigation.parser.exporter.OSMExporterInMemory;
import com.navigation.parser.loader.OSMLoader;
import com.navigation.parser.provider.OSMProviderBzipFile;
import org.springframework.stereotype.Component;

@Component
public class MapRepositoryLoader {

  private final OsmMapper osmMapper;

  public MapRepositoryLoader(OsmMapper osmMapper) {
    this.osmMapper = osmMapper;
  }

  LoadedMap loadMap(String filePath) {
    try {
      var provider = new OSMProviderBzipFile(filePath);
      var exporter = new OSMExporterInMemory();
//      new OSMLoader(provider, exporter, new OSMStreetDataSpecification()).export();
      return osmMapper.exportedOSMToLoadedMap(exporter.getExportedData());
    } catch (Exception e) {
      return null;
    }
  }
}
