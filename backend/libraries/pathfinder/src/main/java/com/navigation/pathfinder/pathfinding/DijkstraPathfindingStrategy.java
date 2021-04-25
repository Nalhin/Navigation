package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.graph.Path;

import java.util.*;

public class DijkstraPathfindingStrategy implements PathfindingStrategy {

  @Override
  public Path findShortestPath(Vertex start, Vertex target, Graph graph) {
    var minDistances = new HashMap<Vertex, Double>();
    var prevEdges = new HashMap<Vertex, Vertex>();
    minDistances.put(start, Double.MAX_VALUE);

    var pq = new PriorityQueue<GraphNodeWithDistance>();
    pq.add(new GraphNodeWithDistance(start, 0.0));

    while (!pq.isEmpty()) {
      var curr = pq.poll();

      if (curr.distanceSoFar > minDistances.getOrDefault(curr.node, Double.MAX_VALUE)) {
        continue;
      }

      for (var edge : graph.getNodeEdges(curr.node)) {
        double distance = curr.distanceSoFar + graph.calculateEdgeDistance(edge);

        if (distance < minDistances.getOrDefault(edge.getTo(), Double.MAX_VALUE)) {
          minDistances.put(edge.getTo(), distance);
          prevEdges.put(edge.getTo(), curr.node);
          pq.add(new GraphNodeWithDistance(edge.getTo(), distance));
        }
      }
    }

    return buildShortestPath(prevEdges, target, start);
  }

  private Path buildShortestPath(Map<Vertex, Vertex> prevEdges, Vertex last, Vertex start) {
    var result = new ArrayList<Vertex>();

    var currNode = last;
    while (currNode != null && currNode != start) {
      result.add(currNode);
      currNode = prevEdges.get(currNode);
    }
    result.add(start);
    Collections.reverse(result);

    return new Path(result);
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
