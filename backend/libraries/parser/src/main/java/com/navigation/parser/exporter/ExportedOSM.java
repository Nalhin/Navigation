package com.navigation.parser.exporter;

import com.navigation.parser.elements.*;

import java.util.HashMap;
import java.util.Map;

public class ExportedOSM {

  private final Map<Long, Node> nodes;
  private final Map<Long, Way> ways;
  private final Map<Long, Relation> relations;
  private final Bounds bounds;
  private final Metadata metadata;

  public ExportedOSM(
      Map<Long, Node> nodes,
      Map<Long, Way> ways,
      Map<Long, Relation> relations,
      Bounds bounds,
      Metadata metadata) {
    this.nodes = Map.copyOf(nodes);
    this.ways = Map.copyOf(ways);
    this.relations = Map.copyOf(relations);
    this.bounds = bounds;
    this.metadata = metadata;
  }

  public Map<Long, Node> getNodes() {
    return nodes;
  }

  public Map<Long, Way> getWays() {
    return ways;
  }

  public Bounds getBounds() {
    return bounds;
  }

  public Metadata getMetadata() {
    return metadata;
  }

  public Map<Long, Relation> getRelations() {
    return relations;
  }
}
