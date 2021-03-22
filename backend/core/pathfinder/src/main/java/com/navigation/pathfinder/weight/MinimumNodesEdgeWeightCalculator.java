package com.navigation.pathfinder.weight;

import com.navigation.pathfinder.graph.GraphEdge;

public class MinimumNodesEdgeWeightCalculator implements EdgeWeightCalculator {
  @Override
  public double calculateWeight(GraphEdge edge) {
    return 1;
  }
}
