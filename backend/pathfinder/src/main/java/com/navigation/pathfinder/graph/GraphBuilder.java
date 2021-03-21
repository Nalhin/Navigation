package com.navigation.pathfinder.graph;

import com.navigation.pathfinder.distance.DistanceCalculator;
import com.navigation.pathfinder.distance.HaversineDistanceCalculator;

import java.util.*;

public class GraphBuilder {

  private final Map<GraphNode, List<GraphEdge>> nodes = new HashMap<>();
  private final DistanceCalculator distanceCalculator;

  public GraphBuilder() {
    this.distanceCalculator = new HaversineDistanceCalculator();
  }

  public GraphBuilder(DistanceCalculator distanceCalculator) {
    this.distanceCalculator = distanceCalculator;
  }

  public void addNode(GraphNode node) {
    nodes.put(node, new ArrayList<>());
  }

  public void connect(GraphNode from, GraphNode to) {
    // TODO might not be needed
    if (!nodes.containsKey(from)) {
      nodes.put(from, new ArrayList<>());
    }
    var edges = nodes.get(from);
    edges.add(new GraphEdge(from, to, distanceCalculator));
  }

  public Graph asGraph() {
    return new Graph(nodes);
  }
}
