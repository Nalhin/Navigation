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
    var predecessorTreeStart = new HashMap<Vertex, Edge>();
    var predecessorTreeEnd = new HashMap<Vertex, Edge>();
    var visitedStart = new HashSet<Vertex>();
    var visitedEnd = new HashSet<Vertex>();
    var queueStart = new ArrayDeque<Vertex>();
    var queueEnd = new ArrayDeque<Vertex>();
    var reversedGraph = graph.reversed();
    visitedStart.add(start);
    visitedEnd.add(end);
    queueStart.add(start);
    queueEnd.add(end);

    while (!queueStart.isEmpty() || !queueEnd.isEmpty()) {
      if (!queueStart.isEmpty()) {
        var curr = queueStart.poll();
        if (visitedEnd.contains(curr)) {
          return pathSummaryCreator.createBidirectionalPath(
              start, curr, end, predecessorTreeStart, predecessorTreeEnd);
        }
        visitOne(curr, graph, queueStart, predecessorTreeStart, visitedStart);
      }
      if (!queueEnd.isEmpty()) {
        var curr = queueEnd.poll();

        if (visitedStart.contains(curr)) {
          return pathSummaryCreator.createBidirectionalPath(
              start, curr, end, predecessorTreeStart, predecessorTreeEnd);
        }
        visitOne(curr, reversedGraph, queueEnd, predecessorTreeEnd, visitedEnd);
      }
    }
    return pathSummaryCreator.createBidirectionalPath(
        start, start, end, predecessorTreeStart, predecessorTreeEnd);
  }

  private void visitOne(
      Vertex curr,
      Graph graph,
      Queue<Vertex> queue,
      Map<Vertex, Edge> predecessorTree,
      Set<Vertex> visited) {

    for (var edge : graph.getVertexEdges(curr)) {
      if (!visited.contains(edge.getTo())) {
        visited.add(edge.getTo());
        predecessorTree.put(edge.getTo(), edge);
        queue.add(edge.getTo());
      }
    }
  }
}
