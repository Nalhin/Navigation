package com.navigation.pathfinder.graph;


import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Graph {

  private final Map<GraphNode, List<GraphEdge>> nodesWithEdges;

  public Graph(Map<GraphNode, List<GraphEdge>> nodesWithEdges) {
    this.nodesWithEdges = nodesWithEdges;
  }

  public Collection<GraphEdge> getNodeEdges(GraphNode node) {
    return nodesWithEdges.get(node);
  }
}
