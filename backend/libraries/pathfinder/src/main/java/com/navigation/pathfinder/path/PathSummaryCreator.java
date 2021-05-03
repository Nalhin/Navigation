package com.navigation.pathfinder.path;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.pathfinding.PathSummary;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PathSummaryCreator {
  public PathSummary createBidirectionalPath(
      Vertex start,
      Vertex mid,
      Vertex end,
      Map<Vertex, Edge> predecessorTreeStart,
      Map<Vertex, Edge> predecessorTreeEnd) {

    var fromMidToStart = reconstructPathFromPredecessorTree(mid, start, predecessorTreeStart);
    Collections.reverse(fromMidToStart);

    var fromEndToMid = reconstructPathFromPredecessorTree(end, mid, predecessorTreeEnd);
    fromMidToStart.addAll(fromEndToMid);

    return new BidirectionalPathSummary(
        fromMidToStart, predecessorTreeStart.keySet(), predecessorTreeEnd.keySet());
  }

  private List<Edge> reconstructPathFromPredecessorTree(
      Vertex from, Vertex to, Map<Vertex, Edge> predecessorTree) {
    var result = new ArrayList<Edge>();

    var currNode = from;
    while (currNode != null && currNode != to) {
      var edge = predecessorTree.get(currNode);
      result.add(edge);
      currNode = edge.getFrom();
    }

    if (currNode == null) {
      return new ArrayList<>();
    }

    return result;
  }

  public PathSummary createUnidirectionalPath(
      Vertex start, Vertex end, Map<Vertex, Edge> predecessorTree) {
    var fromEndToStart = reconstructPathFromPredecessorTree(end, start, predecessorTree);
    Collections.reverse(fromEndToStart);

    return new SingleDirectionalPathSummary(fromEndToStart, predecessorTree.keySet());
  }
}
