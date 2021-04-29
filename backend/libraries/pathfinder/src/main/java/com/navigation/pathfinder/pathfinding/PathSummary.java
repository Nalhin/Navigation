package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.convexhull.ConvexHullCalculator;
import com.navigation.pathfinder.convexhull.AndrewMonotoneChainConvexHullCalculator;
import com.navigation.pathfinder.convexhull.GrahamScanConvexHullCalculator;
import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.weight.DistanceEdgeWeightCalculator;
import com.navigation.pathfinder.weight.DurationEdgeWeightCalculator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class PathSummary {

  private final List<Edge> path;
  private final Map<Vertex, Edge> predecessorTree;
  private static final DistanceEdgeWeightCalculator distanceCalculator =
      new DistanceEdgeWeightCalculator();
  private static final DurationEdgeWeightCalculator durationCalculator =
      new DurationEdgeWeightCalculator();
  private static final ConvexHullCalculator convexHullCalculator =
      new AndrewMonotoneChainConvexHullCalculator();

  public PathSummary(List<Edge> path, Map<Vertex, Edge> predecessorTree) {
    this.path = path;
    this.predecessorTree = predecessorTree;
  }

  public List<Vertex> getSimplePath() {
    var withoutLast = path.stream().map(Edge::getFrom).collect(Collectors.toList());
    withoutLast.add(path.get(path.size() - 1).getTo());
    return withoutLast;
  }

  public int numberOfVertices() {
    return path.size() + 1;
  }

  public int totalVisitedVertices() {
    return predecessorTree.size();
  }

  public double totalDistance() {
    return path.stream().mapToDouble(distanceCalculator::calculateWeight).sum();
  }

  public double totalDuration() {
    return path.stream().mapToDouble(durationCalculator::calculateWeight).sum();
  }

  public List<Vertex> convexHull() {
    return convexHullCalculator.calculateConvexHull(predecessorTree.keySet());
  }
}
