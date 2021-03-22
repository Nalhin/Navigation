package com.navigation.pathfinder.graph;

import java.util.List;

public class Path {

  private final List<GraphNode> nodes;

  public Path(List<GraphNode> nodes) {
    this.nodes = nodes;
  }

  public List<GraphNode> getNodes() {
    return nodes;
  }
}
