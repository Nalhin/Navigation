package com.navigation.pathfinder.weight;

import com.navigation.pathfinder.graph.GraphEdge;

public class DistanceEdgeWeightCalculator implements EdgeWeightCalculator {

  private final HaversineDistanceCalculator distanceCalculator = new HaversineDistanceCalculator();

  @Override
  public double calculateWeight(GraphEdge edge) {
    return distanceCalculator.calculateDistance(edge.getFrom().getCoordinates(), edge.getTo().getCoordinates());
  }
}
