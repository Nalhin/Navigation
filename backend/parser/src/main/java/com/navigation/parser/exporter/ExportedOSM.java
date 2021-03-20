package com.navigation.parser.exporter;

import com.navigation.parser.elements.*;

import java.util.Map;

public class ExportedOSM {

  private final Map<String, Node> nodes;
  private final Map<String, Way> ways;
  private final Map<String, Relation> relations;
  private final Bounds bounds;
  private final Metadata metadata;

  public ExportedOSM(Map<String, Node> nodes, Map<String, Way> ways, Map<String, Relation> relations, Bounds bounds, Metadata metadata) {
    this.nodes = nodes;
    this.ways = ways;
    this.relations = relations;
    this.bounds = bounds;
    this.metadata = metadata;
  }

  public Map<String, Node> getNodes() {
    return nodes;
  }

  public Map<String, Way> getWays() {
    return ways;
  }

  public Bounds getBounds() {
    return bounds;
  }

  public Metadata getMetadata() {
    return metadata;
  }

  public Map<String, Relation> getRelations() {
    return relations;
  }
}
