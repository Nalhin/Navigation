package com.navigation.pathfinder.weight;

import com.navigation.pathfinder.graph.Edge;

public final class DurationEdgeWeightCalculator implements EdgeWeightCalculator {

  private final static HaversineDistanceCalculator distanceCalculator = new HaversineDistanceCalculator();

  @Override
  public double calculateWeight(Edge edge) {
    return distanceCalculator.calculateDistance(
            edge.getFrom().getCoordinates(), edge.getTo().getCoordinates())
        / edge.getMaxSpeed()
        * 60;
  }
}
