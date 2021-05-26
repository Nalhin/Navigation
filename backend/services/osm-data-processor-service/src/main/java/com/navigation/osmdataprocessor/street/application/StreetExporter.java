package com.navigation.osmdataprocessor.street.application;

import com.navigation.osmdataprocessor.shared.exceptions.ExportNotSupportedException;
import com.navigation.osmdataprocessor.street.domain.StreetConnectionExtractor;
import com.navigation.osmdataprocessor.street.domain.StreetNodeExtractor;
import com.navigation.parser.elements.*;
import com.navigation.parser.exporter.OSMExporter;
import org.springframework.stereotype.Component;

@Component
public class StreetExporter implements OSMExporter {
  private final ProcessedStreetExporter processedExporter;
  private final StreetConnectionExtractor streetConnectionExtractor;
  private final StreetNodeExtractor streetNodeExtractor;

  public StreetExporter(
      ProcessedStreetExporter processedExporter,
      StreetConnectionExtractor streetConnectionExtractor,
      StreetNodeExtractor streetNodeExtractor) {
    this.processedExporter = processedExporter;
    this.streetConnectionExtractor = streetConnectionExtractor;
    this.streetNodeExtractor = streetNodeExtractor;
  }

  @Override
  public boolean accept(Node node) {
    var streetNode = streetNodeExtractor.extractFromNode(node);
    processedExporter.exportProcessedStreetNode(String.valueOf(streetNode.getId()), streetNode);
    return true;
  }

  @Override
  public boolean accept(Way way) {
    var streetConnections = streetConnectionExtractor.extractFromWay(way);
    streetConnections.forEach(
        conn -> processedExporter.exportProcessedStreetConnection(conn.getId(), conn));
    return true;
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
