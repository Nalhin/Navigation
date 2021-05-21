package com.navigation.pathfinder.weight;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Vertex;

public final class DurationEdgeWeightCalculator implements EdgeWeightCalculator {

  private static final HaversineDistanceCalculator distanceCalculator =
      new HaversineDistanceCalculator();

  @Override
  public double calculateWeight(Edge edge) {
    return distanceCalculator.calculateDistance(
            edge.getFrom().getCoordinates(), edge.getTo().getCoordinates())
        / edge.getMaxSpeed()
        * 60;
  }

  @Override
  public double estimateWeight(Vertex start, Vertex end) {
    return calculateWeight(new Edge(start, end, 140));
  }
}
