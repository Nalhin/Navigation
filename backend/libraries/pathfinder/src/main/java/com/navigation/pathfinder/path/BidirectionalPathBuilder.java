package com.navigation.pathfinder.path;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Vertex;

import com.navigation.pathfinder.pathfinding.PathSummary;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class BidirectionalPathBuilder {
  public PathSummary buildPath(
      Map<Vertex, Edge> predecessorTreeStart,
      Map<Vertex, Edge> predecessorTreeEnd,
      Vertex mid,
      Vertex end,
      Vertex start) {
    var path = new ArrayList<Edge>();

    var currNode = mid;
    while (currNode != null && currNode != start) {
      var edge = predecessorTreeStart.get(currNode);
      path.add(edge);
      currNode = edge.getFrom();
    }
    if (currNode != start) {
      throw new RuntimeException();
    }
    Collections.reverse(path);

    currNode = mid;

    while (currNode != null && currNode != end) {
      var edge = predecessorTreeEnd.get(currNode);
      path.add(edge);
      currNode = edge.getFrom();
    }
    if (currNode != end) {
      throw new RuntimeException();
    }

    return new BidirectionalPathSummary(path, predecessorTreeStart.keySet(), predecessorTreeEnd.keySet());
  }
}
