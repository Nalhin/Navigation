package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;

public class BFSPathfindingStrategy implements PathfindingStrategy {
  private static final PathBuilder pathBuilder = new PathBuilder();

  @Override
  public PathSummary findShortestPath(Vertex start, Vertex target, Graph graph) {
    var predecessorTree = new HashMap<Vertex, Edge>();
    var visited = new HashSet<Vertex>();
    var queue = new ArrayDeque<Vertex>();
    visited.add(start);
    queue.add(start);
    while (!queue.isEmpty()) {

      var curr = queue.poll();

      if (curr.equals(target)) {
        return pathBuilder.buildPath(predecessorTree, target, start);
      }

      for (var edge : graph.getVertexEdges(curr)) {
        if (!visited.contains(edge.getTo())) {
          visited.add(edge.getTo());
          predecessorTree.put(edge.getTo(), edge);
          queue.add(edge.getTo());
        }
      }
    }

    return pathBuilder.buildPath(predecessorTree, target, start);
  }
}
