package com.navigation.pathfinding.domain;

import com.navigation.pathfinder.weight.DistanceEdgeWeightCalculator;
import com.navigation.pathfinder.weight.DurationEdgeWeightCalculator;
import com.navigation.pathfinder.weight.EdgeWeightCalculator;
import com.navigation.pathfinder.weight.UnweightedDistanceCalculator;
import com.navigation.pathfinder.weight.VertexCountEdgeWeightCalculator;

import java.util.Set;
import java.util.function.Supplier;

public enum PathfindingOptimizations {
  DISTANCE(DistanceEdgeWeightCalculator::new),
  NUMBER_OF_NODES(VertexCountEdgeWeightCalculator::new),
  TIME(DurationEdgeWeightCalculator::new),
  NONE(UnweightedDistanceCalculator::new);

  public final static Set<PathfindingOptimizations> allOptimizing = Set.of(DISTANCE, NUMBER_OF_NODES, TIME);

  public final Supplier<? extends EdgeWeightCalculator> supplier;

  <T extends EdgeWeightCalculator> PathfindingOptimizations(Supplier<T> supplier) {
    this.supplier = supplier;
  }
}
