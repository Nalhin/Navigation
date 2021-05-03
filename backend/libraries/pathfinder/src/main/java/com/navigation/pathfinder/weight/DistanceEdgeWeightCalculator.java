package com.navigation.pathfinder.weight;

import com.navigation.pathfinder.graph.Edge;

public final class DistanceEdgeWeightCalculator implements EdgeWeightCalculator {

  private final static HaversineDistanceCalculator distanceCalculator = new HaversineDistanceCalculator();

  @Override
  public double calculateWeight(Edge edge) {
    return distanceCalculator.calculateDistance(
        edge.getFrom().getCoordinates(), edge.getTo().getCoordinates());
  }
}
