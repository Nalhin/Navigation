package com.navigation.pathfinder.path;

import com.navigation.pathfinder.convexhull.ConvexHullCalculator;
import com.navigation.pathfinder.convexhull.AndrewMonotoneChainConvexHullCalculator;
import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.pathfinding.PathSummary;
import com.navigation.pathfinder.weight.DistanceEdgeWeightCalculator;
import com.navigation.pathfinder.weight.DurationEdgeWeightCalculator;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

final class SingleDirectionalPathSummary implements PathSummary {

  private static final DistanceEdgeWeightCalculator distanceCalculator =
      new DistanceEdgeWeightCalculator();
  private static final DurationEdgeWeightCalculator durationCalculator =
      new DurationEdgeWeightCalculator();
  private static final ConvexHullCalculator convexHullCalculator =
      new AndrewMonotoneChainConvexHullCalculator();

  private final List<Edge> path;
  private final Set<Vertex> searchedVertices;

  SingleDirectionalPathSummary(List<Edge> path, Set<Vertex> searchedVertices) {
    this.path = path;
    this.searchedVertices = searchedVertices;
  }

  @Override
  public List<Vertex> simplePath() {
    if (!isFound()) {
      return Collections.emptyList();
    }

    var withoutLast = path.stream().map(Edge::getFrom).collect(Collectors.toList());
    withoutLast.add(path.get(path.size() - 1).getTo());
    return withoutLast;
  }

  @Override
  public int numberOfVertices() {
    return isFound() ? path.size() + 1 : 0;
  }

  @Override
  public int totalVisitedVertices() {
    return searchedVertices.size();
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
  public Collection<List<Vertex>> searchBoundaries() {
    return List.of(convexHullCalculator.calculateConvexHull(searchedVertices));
  }

  @Override
  public boolean isFound() {
    return !path.isEmpty();
  }
}
