package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;

import com.navigation.pathfinder.path.PathSummaryCreator;
import java.util.*;

public class BidirectionalBFSPathfinder implements PathfindingStrategy {

  private static final PathSummaryCreator pathSummaryCreator = new PathSummaryCreator();

  @Override
  public PathSummary findPath(Vertex start, Vertex end, Graph graph) {
    var predecessorTreeForward = new HashMap<Vertex, Edge>();
    var predecessorTreeBackward = new HashMap<Vertex, Edge>();
    predecessorTreeForward.put(start, null);
    predecessorTreeBackward.put(end, null);

    var queueForward = new ArrayDeque<Vertex>();
    var queueBackward = new ArrayDeque<Vertex>();
    queueForward.add(start);
    queueBackward.add(end);

    var reversedGraph = graph.reversed();

    while (!queueForward.isEmpty() && !queueBackward.isEmpty()) {
      var currForward = queueForward.poll();
      if (predecessorTreeBackward.containsKey(currForward)) {
        return pathSummaryCreator.createBidirectionalPath(
            start, currForward, end, predecessorTreeForward, predecessorTreeBackward);
      }
      visitVertex(currForward, graph, queueForward, predecessorTreeForward);

      var currBackward = queueBackward.poll();
      if (predecessorTreeForward.containsKey(currBackward)) {
        return pathSummaryCreator.createBidirectionalPath(
            start, currBackward, end, predecessorTreeForward, predecessorTreeBackward);
      }
      visitVertex(currBackward, reversedGraph, queueBackward, predecessorTreeBackward);
    }
    return pathSummaryCreator.createBidirectionalPath(
        start, start, end, predecessorTreeForward, predecessorTreeBackward);
  }

  private void visitVertex(
      Vertex curr, Graph graph, Queue<Vertex> queue, Map<Vertex, Edge> predecessorTree) {

    for (var edge : graph.getVertexEdges(curr)) {
      if (!predecessorTree.containsKey(edge.getTo())) {
        predecessorTree.put(edge.getTo(), edge);
        queue.add(edge.getTo());
      }
    }
  }
}
