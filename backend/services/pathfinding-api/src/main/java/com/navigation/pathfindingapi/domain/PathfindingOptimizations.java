package com.navigation.pathfindingapi.domain;

import com.navigation.pathfinder.weight.DistanceEdgeWeightCalculator;
import com.navigation.pathfinder.weight.DurationEdgeWeightCalculator;
import com.navigation.pathfinder.weight.EdgeWeightCalculator;
import com.navigation.pathfinder.weight.VertexCountEdgeWeightCalculator;

import java.util.function.Supplier;

public enum PathfindingOptimizations {
  DISTANCE(DistanceEdgeWeightCalculator::new),
  NUMBER_OF_NODES(VertexCountEdgeWeightCalculator::new),
  TIME(DurationEdgeWeightCalculator::new);

  public final Supplier<? extends EdgeWeightCalculator> supplier;

  <T extends EdgeWeightCalculator> PathfindingOptimizations(Supplier<T> supplier) {
    this.supplier = supplier;
  }
}
