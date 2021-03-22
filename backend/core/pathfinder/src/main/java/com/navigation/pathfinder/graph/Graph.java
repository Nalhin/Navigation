package com.navigation.pathfinder.graph;


import com.navigation.pathfinder.weight.EdgeWeightCalculator;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Graph {

  private final Map<GraphNode, List<GraphEdge>> nodesWithEdges;
  private final EdgeWeightCalculator edgeWeightCalculator;

  public Graph(Map<GraphNode, List<GraphEdge>> nodesWithEdges, EdgeWeightCalculator edgeWeightCalculator) {
    this.nodesWithEdges = nodesWithEdges;
    this.edgeWeightCalculator = edgeWeightCalculator;
  }

  public Collection<GraphEdge> getNodeEdges(GraphNode node) {
    return nodesWithEdges.get(node);
  }

  public double calculateEdgeDistance(GraphEdge edge) {
    return edgeWeightCalculator.calculateWeight(edge);
  }
}
