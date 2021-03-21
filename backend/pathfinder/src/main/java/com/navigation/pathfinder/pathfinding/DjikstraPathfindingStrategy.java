package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.GraphNode;
import com.navigation.pathfinder.graph.Path;

import java.util.*;

public class DjikstraPathfindingStrategy implements PathfindingStrategy {

  @Override
  public Path findShortestPath(GraphNode start, GraphNode target, Graph graph) {
    var minDistances = new HashMap<GraphNode, Double>();
    var prevEdges = new HashMap<GraphNode, GraphNode>();
    minDistances.put(start, Double.MAX_VALUE);

    var pq = new PriorityQueue<GraphNodeWithDistance>();
    pq.add(new GraphNodeWithDistance(start, 0.0));

    while (!pq.isEmpty()) {
      var curr = pq.poll();

      if (curr.distance > minDistances.getOrDefault(curr.node, Double.MAX_VALUE)) {
        continue;
      }

      for (var edge : graph.getNodeEdges(curr.node)) {
        double distance = curr.distance + edge.getDistance();

        if (distance < minDistances.getOrDefault(edge.getTo(), Double.MAX_VALUE)) {
          minDistances.put(edge.getTo(), distance);
          prevEdges.put(edge.getTo(), curr.node);
          pq.add(new GraphNodeWithDistance(edge.getTo(), distance));
        }
      }
    }

    return buildShortestPath(prevEdges, target);
  }

  private Path buildShortestPath(Map<GraphNode, GraphNode> prevEdges, GraphNode last) {
    var result = new ArrayList<GraphNode>();

    var currNode = last;
    while (currNode != null) {
      result.add(currNode);
      currNode = prevEdges.get(currNode);
    }

    Collections.reverse(result);

    return new Path(result);
  }

  private static final class GraphNodeWithDistance implements Comparable<GraphNodeWithDistance> {
    private final GraphNode node;
    private final double distance;

    public GraphNodeWithDistance(GraphNode node, double distance) {
      this.node = node;
      this.distance = distance;
    }

    @Override
    public int compareTo(GraphNodeWithDistance graphNodeWithDistance) {
      return Double.compare(distance, graphNodeWithDistance.distance);
    }
  }

}
