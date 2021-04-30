package com.navigation.pathfinder.path;

import com.navigation.pathfinder.convexhull.ConvexHullCalculator;
import com.navigation.pathfinder.convexhull.AndrewMonotoneChainConvexHullCalculator;
import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.weight.DistanceEdgeWeightCalculator;
import com.navigation.pathfinder.weight.DurationEdgeWeightCalculator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class SingleDirectionalPathSummary implements PathSummary{

  private final List<Edge> path;
  private final Map<Vertex, Edge> predecessorTree;
  private static final DistanceEdgeWeightCalculator distanceCalculator =
      new DistanceEdgeWeightCalculator();
  private static final DurationEdgeWeightCalculator durationCalculator =
      new DurationEdgeWeightCalculator();
  private static final ConvexHullCalculator convexHullCalculator =
      new AndrewMonotoneChainConvexHullCalculator();

  public SingleDirectionalPathSummary(List<Edge> path, Map<Vertex, Edge> predecessorTree) {
    this.path = path;
    this.predecessorTree = predecessorTree;
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
    return predecessorTree.size();
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
    return List.of(convexHullCalculator.calculateConvexHull(predecessorTree.keySet()));
  }
}
