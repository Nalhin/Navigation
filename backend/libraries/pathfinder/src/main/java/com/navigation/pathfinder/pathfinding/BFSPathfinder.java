package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;

import com.navigation.pathfinder.path.PathSummaryCreator;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;

public class BFSPathfinder implements PathfindingStrategy {

  private static final PathSummaryCreator pathSummaryCreator = new PathSummaryCreator();

  @Override
  public PathSummary findPath(Vertex start, Vertex end, Graph graph) {
    var predecessorTree = new HashMap<Vertex, Edge>();
    predecessorTree.put(start, null);

    var queue = new ArrayDeque<Vertex>();
    queue.add(start);

    while (!queue.isEmpty()) {
      var curr = queue.poll();

      if (curr.equals(end)) {
        return pathSummaryCreator.createUnidirectionalPath(start, end, predecessorTree);
      }

      for (var edge : graph.getVertexEdges(curr)) {
        if (!predecessorTree.containsKey(edge.getTo())) {
          predecessorTree.put(edge.getTo(), edge);
          queue.add(edge.getTo());
        }
      }
    }

    return pathSummaryCreator.createUnidirectionalPath(start, end, predecessorTree);
  }
}
