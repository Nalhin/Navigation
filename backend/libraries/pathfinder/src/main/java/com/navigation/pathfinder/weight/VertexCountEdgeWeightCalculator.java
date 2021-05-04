package com.navigation.pathfinder.weight;

import com.navigation.pathfinder.graph.Edge;

public final class VertexCountEdgeWeightCalculator implements EdgeWeightCalculator {
  @Override
  public double calculateWeight(Edge edge) {
    return 1;
  }
}
