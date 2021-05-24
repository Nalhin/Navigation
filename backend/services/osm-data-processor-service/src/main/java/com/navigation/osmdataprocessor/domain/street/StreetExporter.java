package com.navigation.osmdataprocessor.domain.street;

import com.navigation.osmdataprocessor.domain.ExportNotSupportedException;
import com.navigation.parser.elements.*;
import com.navigation.parser.exporter.OSMExporter;

import java.util.List;
import java.util.regex.Pattern;

public class StreetExporter implements OSMExporter {
  public static final Pattern PATTERN = Pattern.compile("^\\d+$");
  private final ProcessedStreetExporter processedExporter;

  public StreetExporter(ProcessedStreetExporter processedExporter) {
    this.processedExporter = processedExporter;
  }

  @Override
  public boolean accept(Node node) {
    var streetNode = new StreetNode(node.getId(), node.getLatitude(), node.getLongitude() );
    processedExporter.exportProcessedStreetNode(String.valueOf(node.getId()), streetNode);
    return true;
  }

  @Override
  public boolean accept(Way way) {
    List<Long> nodeReferences = way.getNodeReferences();
    int maxSpeed = extractMaxSpeed(way);
    for (int i = 1; i < nodeReferences.size(); i++) {
      long from = nodeReferences.get(i - 1);
      long to = nodeReferences.get(i);
      processedExporter.exportProcessedStreetConnection(
          generateId(from, to), new StreetConnection(from, to, maxSpeed));
    }
    if (!way.containsTagWithValue("oneway", "yes")) {
      for (int i = nodeReferences.size() - 2; i >= 0; i--) {
        long from = nodeReferences.get(i + 1);
        long to = nodeReferences.get(i);
        processedExporter.exportProcessedStreetConnection(
            generateId(from, to), new StreetConnection(from, to, maxSpeed));
      }
    }
    return true;
  }

  private int extractMaxSpeed(Way way) {
    if (!way.containsTag("maxspeed")) {
      return 50;
    }
    String maxSpeed = way.getTag("maxspeed");
    if (!PATTERN.matcher(maxSpeed).matches()) {
      return 50;
    }
    return Integer.parseInt(maxSpeed);
  }

  private String generateId(long from, long to) {
    return from + "#" + to;
  }

  @Override
  public boolean accept(Bounds bounds) {
    throw new ExportNotSupportedException();
  }

  @Override
  public boolean accept(Metadata metadata) {
    throw new ExportNotSupportedException();
  }

  @Override
  public boolean accept(Relation relation) {
    throw new ExportNotSupportedException();
  }
}
