package com.navigation.pathfinder.path;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class PathBuilder {
  public PathSummary buildPath(Map<Vertex, Edge> predecessorTree, Vertex last, Vertex start) {
    var result = new ArrayList<Edge>();

    var currNode = last;
    while (currNode != null && currNode != start) {
      var edge = predecessorTree.get(currNode);
      result.add(edge);
      currNode = edge.getFrom();
    }
    if (currNode != start) {
      return new SingleDirectionalPathSummary(Collections.emptyList(), predecessorTree);
    }
    Collections.reverse(result);

    return new SingleDirectionalPathSummary(result, predecessorTree);
  }
}
