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

  public static final Set<PathfindingOptimizations> COORDINATES_BASED = Set.of(DISTANCE, TIME);
  public static final Set<PathfindingOptimizations> ALL_OPTIMIZING =
      Set.of(DISTANCE, NUMBER_OF_NODES, TIME);

  public final Supplier<? extends EdgeWeightCalculator> calculator;

  <T extends EdgeWeightCalculator> PathfindingOptimizations(Supplier<T> calculator) {
    this.calculator = calculator;
  }
}
