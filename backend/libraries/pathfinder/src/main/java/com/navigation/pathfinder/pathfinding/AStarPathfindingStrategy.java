package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.path.PathSummaryCreator;
import com.navigation.pathfinder.weight.EdgeWeightCalculator;

import java.util.*;

public class AStarPathfindingStrategy implements PathfindingStrategy {

  private final EdgeWeightCalculator calculator;
  private static final PathSummaryCreator pathSummaryCreator = new PathSummaryCreator();

  public AStarPathfindingStrategy(EdgeWeightCalculator calculator) {
    this.calculator = calculator;
  }

  @Override
  public PathSummary findShortestPath(Vertex start, Vertex end, Graph graph) {
    var predecessorTree = new HashMap<Vertex, Edge>();
    var gScores = new HashMap<Vertex, Double>();
    gScores.put(start, 0.0);

    var open = new PriorityQueue<GraphNodeWithDistance>();
    open.add(new GraphNodeWithDistance(start, heuristic(start, end)));

    while (!open.isEmpty()) {
      var curr = open.poll().node;
      if (curr.equals(end)) {
        return pathSummaryCreator.createUnidirectionalPath(start, end, predecessorTree);
      }

      for (var edge : graph.getVertexEdges(curr)) {
        var neighbour = edge.getTo();
        double newScore = gScores.get(curr) + calculator.calculateWeight(edge);

        if (newScore < gScores.getOrDefault(neighbour, Double.MAX_VALUE)) {
          gScores.put(neighbour, newScore);
          predecessorTree.put(neighbour, edge);
          open.add(new GraphNodeWithDistance(neighbour, newScore + heuristic(neighbour, end)));
        }
      }
    }

    return pathSummaryCreator.createUnidirectionalPath(start, end, predecessorTree);
  }

  private double heuristic(Vertex from, Vertex to) {
    return calculator.calculateWeight(new Edge(from, to, 140));
  }

  private static final class GraphNodeWithDistance implements Comparable<GraphNodeWithDistance> {
    private final Vertex node;
    private final double fScore;

    public GraphNodeWithDistance(Vertex node, double fScore) {
      this.node = node;
      this.fScore = fScore;
    }

    @Override
    public int compareTo(GraphNodeWithDistance graphNodeWithDistance) {
      return Double.compare(fScore, graphNodeWithDistance.fScore);
    }
  }
}
