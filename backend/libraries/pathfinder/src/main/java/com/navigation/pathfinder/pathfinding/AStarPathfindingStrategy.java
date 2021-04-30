package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.path.PathSummary;
import com.navigation.pathfinder.path.PathBuilder;
import com.navigation.pathfinder.weight.EdgeWeightCalculator;

import java.util.*;

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
    gScores.put(start, 0.0);

    var open = new PriorityQueue<GraphNodeWithDistance>();
    open.add(new GraphNodeWithDistance(start, heuristic(start, target)));

    while (!open.isEmpty()) {
      var curr = open.poll().node;
      if (curr.equals(target)) {
        return pathBuilder.buildPath(predecessorTree, target, start);
      }

      for (var edge : graph.getVertexEdges(curr)) {
        var neighbour = edge.getTo();
        double newScore = gScores.get(curr) + calculator.calculateWeight(edge);

        if (newScore < gScores.getOrDefault(neighbour, Double.MAX_VALUE)) {
          gScores.put(neighbour, newScore);
          predecessorTree.put(neighbour, edge);
          open.add(new GraphNodeWithDistance(neighbour, newScore + heuristic(neighbour, target)));
        }
      }
    }

    return pathBuilder.buildPath(predecessorTree, target, start);
  }

  private double heuristic(Vertex from, Vertex to) {
    return calculator.calculateWeight(new Edge(from, to, 50));
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

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      GraphNodeWithDistance that = (GraphNodeWithDistance) o;
      return Objects.equals(node, that.node);
    }

    @Override
    public int hashCode() {
      return Objects.hash(node);
    }
  }
}
