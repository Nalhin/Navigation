package com.navigation.pathfinder.weight;

import com.navigation.pathfinder.graph.Edge;

public class DistanceEdgeWeightCalculator implements EdgeWeightCalculator {

  private final HaversineDistanceCalculator distanceCalculator = new HaversineDistanceCalculator();

  @Override
  public double calculateWeight(Edge edge) {
    return distanceCalculator.calculateDistance(edge.getFrom().getCoordinates(), edge.getTo().getCoordinates());
  }
}
