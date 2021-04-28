package com.navigation.pathfindingapi.domain;

import com.navigation.pathfinder.pathfinding.AStarPathfindingStrategy;
import com.navigation.pathfinder.pathfinding.BellmanFordPathfindingStrategy;
import com.navigation.pathfinder.pathfinding.DijkstraPathfindingStrategy;
import com.navigation.pathfinder.pathfinding.PathfindingStrategy;
import com.navigation.pathfinder.weight.DistanceEdgeWeightCalculator;
import com.navigation.pathfinder.weight.DurationEdgeWeightCalculator;
import com.navigation.pathfinder.weight.EdgeWeightCalculator;
import com.navigation.pathfinder.weight.MinimumNodesEdgeWeightCalculator;

import java.util.function.Supplier;

public class PathfindingStrategyFactory {

  public enum Algorithms {
    DIJKSTRA,
    A_STAR,
    BELLMAN_FORD
  }

  public enum Optimizations {
    TIME(DurationEdgeWeightCalculator::new),
    DISTANCE(DistanceEdgeWeightCalculator::new),
    NUMBER_OF_NODES(MinimumNodesEdgeWeightCalculator::new);

    private final Supplier<? extends EdgeWeightCalculator> supplier;

    <T extends EdgeWeightCalculator> Optimizations(Supplier<T> supplier) {
      this.supplier = supplier;
    }
  }

  public PathfindingStrategy pathfindingStrategy(
      Algorithms algorithms, Optimizations optimizations) {
    var calculator = optimizations.supplier.get();

    return switch(algorithms){
      case A_STAR -> new AStarPathfindingStrategy(calculator);
      case DIJKSTRA -> new DijkstraPathfindingStrategy(calculator);
      case BELLMAN_FORD -> new BellmanFordPathfindingStrategy(calculator);
    };
  }
}
