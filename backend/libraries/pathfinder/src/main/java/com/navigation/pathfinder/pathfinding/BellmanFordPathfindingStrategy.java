package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.path.PathSummary;
import com.navigation.pathfinder.path.PathBuilder;
import com.navigation.pathfinder.weight.EdgeWeightCalculator;

import java.util.HashMap;

public class BellmanFordPathfindingStrategy implements PathfindingStrategy {

  private final EdgeWeightCalculator calculator;
  private final static PathBuilder pathBuilder = new PathBuilder();

  public BellmanFordPathfindingStrategy(EdgeWeightCalculator calculator) {
    this.calculator = calculator;
  }

  @Override
  public PathSummary findShortestPath(Vertex start, Vertex target, Graph graph) {
    var minWeights = new HashMap<Vertex, Double>();
    minWeights.put(start, 0.0);
    var predecessorTree = new HashMap<Vertex, Edge>();

    var edges = graph.edges();
    var vertices = graph.vertices();

    for (int i = 0; i < vertices.size() - 1; i++) {
      boolean changed = false;

      for (var edge : edges) {
        if (!minWeights.containsKey(edge.getFrom())) {
          continue;
        }
        double weight = minWeights.get(edge.getFrom()) + calculator.calculateWeight(edge);
        if (weight < minWeights.getOrDefault(edge.getTo(), Double.MAX_VALUE)) {
          minWeights.put(edge.getTo(), weight);
          predecessorTree.put(edge.getTo(), edge);
          changed = true;
        }
      }
      if (!changed) {
        return pathBuilder.buildPath(predecessorTree, target, start);
      }
    }
    return pathBuilder.buildPath(predecessorTree, target, start);
  }
}
