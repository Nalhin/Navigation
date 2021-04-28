package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Path;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.weight.EdgeWeightCalculator;

import java.util.HashMap;
import java.util.Map;

public class BellmanFordPathfindingStrategy implements PathfindingStrategy {

  private final EdgeWeightCalculator calculator;
  private final PathBuilder pathBuilder = new PathBuilder();

  public BellmanFordPathfindingStrategy(EdgeWeightCalculator calculator) {
    this.calculator = calculator;
  }

  @Override
  public Path findShortestPath(Vertex start, Vertex target, Graph graph) {
    var minDistances = new HashMap<Vertex, Double>();
    minDistances.put(start, 0.0);
    var predecessorTree = new HashMap<Vertex, Vertex>();

    var edges = graph.edges();
    for (int i = 0; i < graph.vertices().size() - 1; i++) {
      boolean changed = false;

      for (var edge : edges) {
        if (!minDistances.containsKey(edge.getFrom())) {
          continue;
        }
        double distance = minDistances.get(edge.getFrom()) + calculator.calculateWeight(edge);
        if (distance < minDistances.getOrDefault(edge.getTo(), Double.MAX_VALUE)) {
          minDistances.put(edge.getTo(), distance);
          predecessorTree.put(edge.getTo(), edge.getFrom());
          changed = true;
        }
      }
      if (!changed) {
        return pathBuilder.buildPath(predecessorTree, start, target);
      }
    }
    return pathBuilder.buildPath(predecessorTree, start, target);
  }
}
