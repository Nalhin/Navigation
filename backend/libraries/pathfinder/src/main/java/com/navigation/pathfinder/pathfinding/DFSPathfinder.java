package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.path.PathSummaryCreator;
import java.util.ArrayDeque;
import java.util.HashMap;

public class DFSPathfinder implements PathfindingStrategy {

  private static final PathSummaryCreator pathSummaryCreator = new PathSummaryCreator();

  @Override
  public PathSummary findPath(Vertex start, Vertex end, Graph graph) {
    var predecessorTree = new HashMap<Vertex, Edge>();
    predecessorTree.put(start, null);

    var stack = new ArrayDeque<Vertex>();
    stack.add(start);

    while (!stack.isEmpty()) {
      var curr = stack.pop();

      if (curr.equals(end)) {
        return pathSummaryCreator.createUnidirectionalPath(start, end, predecessorTree);
      }

      for (var edge : graph.getVertexEdges(curr)) {
        if (!predecessorTree.containsKey(edge.getTo())) {
          predecessorTree.put(edge.getTo(), edge);
          stack.push(edge.getTo());
        }
      }
    }

    return pathSummaryCreator.createUnidirectionalPath(start, end, predecessorTree);
  }
}
