package com.navigation.pathfinder.graph;


import com.navigation.pathfinder.weight.EdgeWeightCalculator;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Graph {

  private final Map<Vertex, List<Edge>> nodesWithEdges;
  private final EdgeWeightCalculator edgeWeightCalculator;

  public Graph(Map<Vertex, List<Edge>> nodesWithEdges, EdgeWeightCalculator edgeWeightCalculator) {
    this.nodesWithEdges = nodesWithEdges;
    this.edgeWeightCalculator = edgeWeightCalculator;
  }

  public Collection<Edge> getNodeEdges(Vertex node) {
    return nodesWithEdges.get(node);
  }

  public double calculateEdgeDistance(Edge edge) {
    return edgeWeightCalculator.calculateWeight(edge);
  }
}
