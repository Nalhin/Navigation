package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.path.PathSummaryCreator;
import com.navigation.pathfinder.weight.EdgeWeightCalculator;

import java.util.*;

public class AStarPathfinder implements PathfindingStrategy {

  private final EdgeWeightCalculator calculator;
  private static final PathSummaryCreator pathSummaryCreator = new PathSummaryCreator();

  public AStarPathfinder(EdgeWeightCalculator calculator) {
    this.calculator = calculator;
  }

  @Override
  public PathSummary findPath(Vertex start, Vertex end, Graph graph) {
    var predecessorTree = new HashMap<Vertex, Edge>();
    predecessorTree.put(start, null);

    var gScores = new HashMap<Vertex, Double>();
    gScores.put(start, 0.0);

    var open = new PriorityQueue<ScoredGraphVertex>();
    open.add(new ScoredGraphVertex(start, heuristic(start, end)));

    while (!open.isEmpty()) {
      var curr = open.poll().vertex();

      if (curr.equals(end)) {
        return pathSummaryCreator.createUnidirectionalPath(start, end, predecessorTree);
      }

      for (var edge : graph.getVertexEdges(curr)) {
        var neighbour = edge.getTo();
        var newScore = gScores.get(curr) + calculator.calculateWeight(edge);

        if (newScore < gScores.getOrDefault(neighbour, Double.MAX_VALUE)) {
          gScores.put(neighbour, newScore);
          predecessorTree.put(neighbour, edge);
          open.add(new ScoredGraphVertex(neighbour, newScore + heuristic(neighbour, end)));
        }
      }
    }

    return pathSummaryCreator.createUnidirectionalPath(start, end, predecessorTree);
  }

  private double heuristic(Vertex from, Vertex to) {
    return calculator.estimateWeight(from, to);
  }
}
