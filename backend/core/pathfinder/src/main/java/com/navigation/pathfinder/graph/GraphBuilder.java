package com.navigation.pathfinder.graph;

import com.navigation.pathfinder.weight.DistanceEdgeWeightCalculator;
import com.navigation.pathfinder.weight.EdgeWeightCalculator;

import java.util.*;

public class GraphBuilder {

  private final Map<Vertex, List<Edge>> nodes = new HashMap<>();
  private final EdgeWeightCalculator edgeWeightCalculator;

  public GraphBuilder() {
    this.edgeWeightCalculator = new DistanceEdgeWeightCalculator();
  }

  public GraphBuilder(EdgeWeightCalculator edgeWeightCalculator) {
    this.edgeWeightCalculator = edgeWeightCalculator;
  }

  public GraphBuilder addVertex(Vertex vertex) {
    nodes.put(vertex, new ArrayList<>());
    return this;
  }

  public GraphBuilder connect(Vertex from, Vertex to) {
    // TODO might not be needed
    if (!nodes.containsKey(from)) {
      nodes.put(from, new ArrayList<>());
    }
    var edges = nodes.get(from);
    edges.add(new Edge(from, to));

    return this;
  }

  public Graph asGraph() {
    return new Graph(nodes, edgeWeightCalculator);
  }
}
