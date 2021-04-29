package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.weight.EdgeWeightCalculator;

import java.util.*;

public class DijkstraPathfindingStrategy implements PathfindingStrategy {

  private final EdgeWeightCalculator calculator;
  private final static PathBuilder pathBuilder = new PathBuilder();

  public DijkstraPathfindingStrategy(EdgeWeightCalculator calculator) {
    this.calculator = calculator;
  }

  @Override
  public PathSummary findShortestPath(Vertex start, Vertex target, Graph graph) {
    var minDistances = new HashMap<Vertex, Double>();
    var predecessorTree = new HashMap<Vertex, Edge>();
    minDistances.put(start, Double.MAX_VALUE);

    var pq = new PriorityQueue<GraphNodeWithDistance>();
    pq.add(new GraphNodeWithDistance(start, 0.0));

    while (!pq.isEmpty()) {
      var curr = pq.poll();

      if(curr.distanceSoFar > minDistances.getOrDefault(target, Double.MAX_VALUE)){
        return pathBuilder.buildPath(predecessorTree, target, start);
      }

      if (curr.distanceSoFar > minDistances.getOrDefault(curr.node, Double.MAX_VALUE)) {
        continue;
      }

      for (var edge : graph.getVertexEdges(curr.node)) {
        double distance = curr.distanceSoFar + calculator.calculateWeight(edge);

        if (distance < minDistances.getOrDefault(edge.getTo(), Double.MAX_VALUE)) {
          minDistances.put(edge.getTo(), distance);
          predecessorTree.put(edge.getTo(), edge);
          pq.add(new GraphNodeWithDistance(edge.getTo(), distance));
        }
      }
    }

    return pathBuilder.buildPath(predecessorTree, target, start);
  }



  private static final class GraphNodeWithDistance implements Comparable<GraphNodeWithDistance> {
    private final Vertex node;
    private final double distanceSoFar;

    public GraphNodeWithDistance(Vertex node, double distanceSoFar) {
      this.node = node;
      this.distanceSoFar = distanceSoFar;
    }

    @Override
    public int compareTo(GraphNodeWithDistance graphNodeWithDistance) {
      return Double.compare(distanceSoFar, graphNodeWithDistance.distanceSoFar);
    }
  }
}
