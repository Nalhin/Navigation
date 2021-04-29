package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.weight.EdgeWeightCalculator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class AStarPathfindingStrategy implements PathfindingStrategy {

  private final EdgeWeightCalculator calculator;
  private final PathBuilder pathBuilder = new PathBuilder();

  public AStarPathfindingStrategy(EdgeWeightCalculator calculator) {
    this.calculator = calculator;
  }

  @Override
  public PathSummary findShortestPath(Vertex start, Vertex target, Graph graph) {
    var predecessorTree = new HashMap<Vertex, Edge>();
    var gScores = new HashMap<Vertex, Double>();
    var fScores = new HashMap<Vertex, Double>();

    gScores.put(start, 0.0);
    fScores.put(start, calculateHScore(start, target));

    var open = new PriorityQueue<Vertex>(Comparator.comparingDouble(fScores::get));
    var inOpen = new HashSet<>();
    open.add(start);
    inOpen.add(start);

    while (!open.isEmpty()) {
      var curr = open.poll();
      if (curr.equals(target)) {
        return pathBuilder.buildPath(predecessorTree, target, start);
      }
      inOpen.remove(curr);

      for (var edge : graph.getVertexEdges(curr)) {

        double tentScore = gScores.get(curr) + calculator.calculateWeight(edge);
        var neighbour = edge.getTo();
        if (tentScore < gScores.getOrDefault(neighbour, Double.MAX_VALUE)) {
          predecessorTree.put(neighbour, edge);
          gScores.put(neighbour, tentScore);
          fScores.put(neighbour, calculateHScore(neighbour, target));

          if (!inOpen.contains(neighbour)) {
            open.add(neighbour);
            inOpen.add(neighbour);
          }
        }
      }
    }

    return pathBuilder.buildPath(predecessorTree, target, start);
  }

  private double calculateHScore(Vertex from, Vertex to) {
    return calculator.calculateWeight(new Edge(from, to, 50));
  }
}
