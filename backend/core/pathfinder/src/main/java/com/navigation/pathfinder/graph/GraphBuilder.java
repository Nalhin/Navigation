package com.navigation.pathfinder.graph;

import com.navigation.pathfinder.weight.DistanceEdgeWeightCalculator;
import com.navigation.pathfinder.weight.EdgeWeightCalculator;

import java.util.*;

public class GraphBuilder {

  private final Map<GraphNode, List<GraphEdge>> nodes = new HashMap<>();
  private final EdgeWeightCalculator edgeWeightCalculator;

  public GraphBuilder() {
    this.edgeWeightCalculator = new DistanceEdgeWeightCalculator();
  }

  public GraphBuilder(EdgeWeightCalculator edgeWeightCalculator) {
    this.edgeWeightCalculator = edgeWeightCalculator;
  }

  public GraphBuilder addNode(GraphNode node) {
    nodes.put(node, new ArrayList<>());
    return this;
  }

  public GraphBuilder connect(GraphNode from, GraphNode to) {
    // TODO might not be needed
    if (!nodes.containsKey(from)) {
      nodes.put(from, new ArrayList<>());
    }
    var edges = nodes.get(from);
    edges.add(new GraphEdge(from, to));

    return this;
  }

  public Graph asGraph() {
    return new Graph(nodes, edgeWeightCalculator);
  }
}
