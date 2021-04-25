package com.navigation.pathfinder.graph;


import com.navigation.pathfinder.weight.EdgeWeightCalculator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Graph {

  private final Map<Vertex, List<Edge>> nodesWithEdges;
  private final Map<Long, Vertex> vertices;
  private final EdgeWeightCalculator edgeWeightCalculator;

  Graph(Map<Vertex, List<Edge>> nodesWithEdges, Map<Long,Vertex> vertices, EdgeWeightCalculator edgeWeightCalculator) {
    this.nodesWithEdges = nodesWithEdges;
    this.vertices = vertices;
    this.edgeWeightCalculator = edgeWeightCalculator;
  }

  public Vertex getVertexById(long id){
    return vertices.get(id);
  }

  public Collection<Edge> getNodeEdges(Vertex node) {
    return nodesWithEdges.get(node);
  }

  public double calculateEdgeDistance(Edge edge) {
    return edgeWeightCalculator.calculateWeight(edge);
  }
}
