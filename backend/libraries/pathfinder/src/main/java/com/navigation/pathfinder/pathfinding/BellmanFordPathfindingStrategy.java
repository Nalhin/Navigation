package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.path.PathSummaryCreator;
import com.navigation.pathfinder.weight.EdgeWeightCalculator;

import java.util.HashMap;

public class BellmanFordPathfindingStrategy implements PathfindingStrategy {

  private final EdgeWeightCalculator calculator;
  private static final PathSummaryCreator pathSummaryCreator = new PathSummaryCreator();

  public BellmanFordPathfindingStrategy(EdgeWeightCalculator calculator) {
    this.calculator = calculator;
  }

  @Override
  public PathSummary findPath(Vertex start, Vertex end, Graph graph) {
    var minWeights = new HashMap<Vertex, Double>();
    minWeights.put(start, 0.0);

    var predecessorTree = new HashMap<Vertex, Edge>();
    predecessorTree.put(start, null);

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
        return pathSummaryCreator.createUnidirectionalPath(start, end, predecessorTree);
      }
    }
    return pathSummaryCreator.createUnidirectionalPath(start, end, predecessorTree);
  }
}
