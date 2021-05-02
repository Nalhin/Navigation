package com.navigation.parser.exporter;

import com.navigation.parser.elements.*;

import java.util.HashMap;
import java.util.Map;

public class OSMExporterInMemory implements OSMExporter {

  private final Map<Long, Node> nodes = new HashMap<>();
  private final Map<Long, Way> ways = new HashMap<>();
  private final Map<Long, Relation> relations = new HashMap<>();
  private Bounds bounds;
  private Metadata metadata;

  @Override
  public boolean accept(Node node) {
    nodes.put(node.getId(), node);
    return true;
  }

  @Override
  public boolean accept(Way way) {
    ways.put(way.getId(), way);
    return true;
  }

  @Override
  public boolean accept(Bounds bounds) {
    this.bounds = bounds;
    return true;
  }

  @Override
  public boolean accept(Metadata metadata) {
    this.metadata = metadata;
    return true;
  }

  @Override
  public boolean accept(Relation relation) {
    relations.put(relation.getId(), relation);
    return true;
  }

  public ExportedOSM getExportedData() {
    return new ExportedOSM(nodes, ways, relations, bounds, metadata);
  }
}
