package com.navigation.pathfinder.path;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.pathfinding.PathSummary;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PathSummaryCreator {

  private List<Edge> reconstructPathFromPredecessorTree(
      Vertex from, Vertex to, Map<Vertex, Edge> predecessorTree) {
    var result = new ArrayList<Edge>();

    var currNode = from;

    while (predecessorTree.containsKey(currNode) && !currNode.equals(to)) {
      var edge = predecessorTree.get(currNode);
      result.add(edge);
      currNode = edge.getFrom();
    }

    if (!currNode.equals(to)) {
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

  public PathSummary createBidirectionalPath(
      Vertex start,
      Vertex mid,
      Vertex end,
      Map<Vertex, Edge> predecessorTreeStart,
      Map<Vertex, Edge> predecessorTreeEnd) {

    var fromMidToStart = reconstructPathFromPredecessorTree(mid, start, predecessorTreeStart);
    Collections.reverse(fromMidToStart);

    var fromEndToMid = reconstructPathFromPredecessorTree(mid, end, predecessorTreeEnd);

    if (fromMidToStart.isEmpty() || fromEndToMid.isEmpty()) {
      return new BidirectionalPathSummary(
          Collections.emptyList(), predecessorTreeStart.keySet(), predecessorTreeEnd.keySet());
    }

    fromMidToStart.addAll(fromEndToMid.stream().map(Edge::reversed).collect(Collectors.toList()));

    return new BidirectionalPathSummary(
        fromMidToStart, predecessorTreeStart.keySet(), predecessorTreeEnd.keySet());
  }
}
