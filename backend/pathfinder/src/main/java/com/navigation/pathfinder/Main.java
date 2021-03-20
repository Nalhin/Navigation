package com.navigation.pathfinder;


import com.navigation.parser.exporter.OSMExporterInMemory;
import com.navigation.parser.loader.OSMLoader;
import com.navigation.parser.loader.specification.OSMStreetDataSpecification;
import com.navigation.parser.provider.OSMProviderFile;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException, XMLStreamException {
    var provider = new OSMProviderFile(args[0]);
    var exporter = new OSMExporterInMemory();
    var loader = new OSMLoader(provider, exporter, new OSMStreetDataSpecification());
    loader.loadOSM();
    var data = exporter.getExportedData();
    System.out.println(data.getBounds());
    System.out.println(data.getNodes().size());
    System.out.println(data.getNodes().size());
  }
}
