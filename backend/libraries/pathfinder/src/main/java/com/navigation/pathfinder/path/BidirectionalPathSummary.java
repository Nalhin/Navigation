package com.navigation.pathfinder.path;

import com.navigation.pathfinder.convexhull.AndrewMonotoneChainConvexHullCalculator;
import com.navigation.pathfinder.convexhull.ConvexHullCalculator;
import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.weight.DistanceEdgeWeightCalculator;
import com.navigation.pathfinder.weight.DurationEdgeWeightCalculator;

import java.util.*;
import java.util.stream.Collectors;

public class BidirectionalPathSummary implements PathSummary {

  private final List<Edge> path;
  private final Map<Vertex, Edge> predecessorTreeStart;
  private final Map<Vertex, Edge> predecessorTreeEnd;
  private static final DistanceEdgeWeightCalculator distanceCalculator =
      new DistanceEdgeWeightCalculator();
  private static final DurationEdgeWeightCalculator durationCalculator =
      new DurationEdgeWeightCalculator();
  private static final ConvexHullCalculator convexHullCalculator =
      new AndrewMonotoneChainConvexHullCalculator();

  public BidirectionalPathSummary(
      List<Edge> path,
      Map<Vertex, Edge> predecessorTreeStart,
      Map<Vertex, Edge> predecessorTreeEnd) {
    this.path = path;
    this.predecessorTreeStart = predecessorTreeStart;
    this.predecessorTreeEnd = predecessorTreeEnd;
  }

  @Override
  public List<Vertex> getSimplePath() {
    var withoutLast = path.stream().map(Edge::getFrom).collect(Collectors.toList());
    withoutLast.add(path.get(path.size() - 1).getTo());
    return withoutLast;
  }

  @Override
  public int numberOfVertices() {
    return path.size() + 1;
  }

  @Override
  public int totalVisitedVertices() {
    return predecessorTreeStart.size() + predecessorTreeEnd.size();
  }

  @Override
  public double totalDistance() {
    return path.stream().mapToDouble(distanceCalculator::calculateWeight).sum();
  }

  @Override
  public double totalDuration() {
    return path.stream().mapToDouble(durationCalculator::calculateWeight).sum();
  }

  @Override
  public Collection<List<Vertex>> convexHulls() {

    return List.of(
        convexHullCalculator.calculateConvexHull(predecessorTreeStart.keySet()),
        convexHullCalculator.calculateConvexHull(predecessorTreeEnd.keySet()));
  }
}
