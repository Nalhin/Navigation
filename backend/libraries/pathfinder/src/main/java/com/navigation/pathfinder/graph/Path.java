package com.navigation.pathfinder.graph;

import com.navigation.pathfinder.weight.DistanceEdgeWeightCalculator;
import com.navigation.pathfinder.weight.DurationEdgeWeightCalculator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Path {

  private final List<Edge> edges;
  private final Map<Vertex, Edge> predecessorTree;
  private final DistanceEdgeWeightCalculator distanceCalculator =
      new DistanceEdgeWeightCalculator();
  private final DurationEdgeWeightCalculator durationCalculator =
      new DurationEdgeWeightCalculator();

  public Path(List<Edge> edges, Map<Vertex, Edge> predecessorTree) {
    this.edges = edges;
    this.predecessorTree = predecessorTree;
  }

  public List<Vertex> getSimplePath() {
    var withoutLast = edges.stream().map(Edge::getFrom).collect(Collectors.toList());
    withoutLast.add(edges.get(edges.size() - 1).getTo());
    return withoutLast;
  }

  public int numberOfVertices() {
    return edges.size() + 1;
  }

  public int totalVisitedVertices() {
    return predecessorTree.keySet().size();
  }

  public double totalDistance() {
    return edges.stream().mapToDouble(distanceCalculator::calculateWeight).sum();
  }

  public double totalDuration() {
    return edges.stream().mapToDouble(durationCalculator::calculateWeight).sum();
  }
}
