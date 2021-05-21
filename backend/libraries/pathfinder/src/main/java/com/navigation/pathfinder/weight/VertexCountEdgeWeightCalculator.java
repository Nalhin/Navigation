package com.navigation.pathfinder.weight;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Vertex;

public final class VertexCountEdgeWeightCalculator implements EdgeWeightCalculator {
  @Override
  public double calculateWeight(Edge edge) {
    return 1;
  }

  @Override
  public double estimateWeight(Vertex start, Vertex end) {
    return 0;
  }
}
