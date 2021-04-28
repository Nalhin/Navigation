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
  public void export(Node node) {
    nodes.put(node.getId(), node);
  }

  @Override
  public void export(Way way) {
    ways.put(way.getId(), way);
  }

  @Override
  public void export(Bounds bounds) {
    this.bounds = bounds;
  }

  @Override
  public void export(Metadata metadata) {
    this.metadata = metadata;
  }

  @Override
  public void export(Relation relation) {
    relations.put(relation.getId(), relation);
  }

  public ExportedOSM getExportedData() {
    return new ExportedOSM(nodes, ways, relations, bounds, metadata);
  }
}