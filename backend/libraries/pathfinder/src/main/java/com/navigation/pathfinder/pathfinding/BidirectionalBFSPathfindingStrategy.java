package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;

import com.navigation.pathfinder.path.PathSummaryCreator;
import java.util.*;

public class BidirectionalBFSPathfindingStrategy implements PathfindingStrategy {

  private static final PathSummaryCreator pathSummaryCreator = new PathSummaryCreator();

  @Override
  public PathSummary findShortestPath(Vertex start, Vertex end, Graph graph) {
    var predecessorTreeForward = new HashMap<Vertex, Edge>();
    var predecessorTreeBackward = new HashMap<Vertex, Edge>();
    predecessorTreeForward.put(start, null);
    predecessorTreeBackward.put(end, null);

    var queueForward = new ArrayDeque<Vertex>();
    var queueBackward = new ArrayDeque<Vertex>();
    queueForward.add(start);
    queueBackward.add(end);

    var reversedGraph = graph.reversed();

    while (!queueForward.isEmpty() || !queueBackward.isEmpty()) {
      if (!queueForward.isEmpty()) {
        var curr = queueForward.poll();
        if (predecessorTreeBackward.containsKey(curr)) {
          return pathSummaryCreator.createBidirectionalPath(
              start, curr, end, predecessorTreeForward, predecessorTreeBackward);
        }
        visitVertex(curr, graph, queueForward, predecessorTreeForward);
      }
      if (!queueBackward.isEmpty()) {
        var curr = queueBackward.poll();

        if (predecessorTreeForward.containsKey(curr)) {
          return pathSummaryCreator.createBidirectionalPath(
              start, curr, end, predecessorTreeForward, predecessorTreeBackward);
        }
        visitVertex(curr, reversedGraph, queueBackward, predecessorTreeBackward);
      }
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
