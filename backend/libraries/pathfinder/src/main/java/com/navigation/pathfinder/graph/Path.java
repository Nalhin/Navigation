package com.navigation.pathfinder.graph;

import com.navigation.pathfinder.weight.DistanceEdgeWeightCalculator;
import com.navigation.pathfinder.weight.DurationEdgeWeightCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Path {

  private final List<Edge> edges;
  private final DistanceEdgeWeightCalculator distanceCalculator =
      new DistanceEdgeWeightCalculator();
  private final DurationEdgeWeightCalculator durationCalculator =
      new DurationEdgeWeightCalculator();

  public Path(List<Edge> edges) {
    this.edges = edges;
  }

  public List<Vertex> getSimplePath() {
    var withoutLast = edges.stream().map(Edge::getFrom).collect(Collectors.toList());
    withoutLast.add(edges.get(edges.size() - 1).getTo());
    return withoutLast;
  }

  public List<PathDetailsVertex> getDetailedPath() {
    double totalTime = getTotalDuration();
    var result = new ArrayList<PathDetailsVertex>();
    result.add(new PathDetailsVertex(0, 0, 0, 0, 0, 0, edges.get(0).getFrom()));

    double cumulativeDist = 0.0;
    double cumulativeTime = 0.0;

    for (var edge : edges) {
      var time = durationCalculator.calculateWeight(edge);
      var dist = distanceCalculator.calculateWeight(edge);

      result.add(
          new PathDetailsVertex(
              cumulativeDist + dist,
              dist,
              cumulativeTime + time,
              time,
              edge.getMaxSpeed(),
              cumulativeTime / totalTime,
              edge.getTo()));
      cumulativeTime += time;
      cumulativeDist += dist;
    }

    return result;
  }

  public int getNumberOfVertices() {
    return edges.size() + 1;
  }

  public double getTotalDistance() {
    return edges.stream().mapToDouble(distanceCalculator::calculateWeight).sum();
  }

  public double getTotalDuration() {
    return edges.stream().mapToDouble(durationCalculator::calculateWeight).sum();
  }
}
