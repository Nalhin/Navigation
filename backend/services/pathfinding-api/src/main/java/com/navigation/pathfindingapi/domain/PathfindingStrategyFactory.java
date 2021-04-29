package com.navigation.pathfindingapi.domain;

import com.navigation.pathfinder.pathfinding.*;
import com.navigation.pathfinder.weight.DistanceEdgeWeightCalculator;
import com.navigation.pathfinder.weight.DurationEdgeWeightCalculator;
import com.navigation.pathfinder.weight.EdgeWeightCalculator;
import com.navigation.pathfinder.weight.MinimumNodesEdgeWeightCalculator;

import java.util.Set;
import java.util.function.Supplier;

public class PathfindingStrategyFactory {

  public enum Algorithms {
    DIJKSTRA(Set.of(Optimizations.values())),
    A_STAR((Set.of(Optimizations.values()))),
    BELLMAN_FORD(Set.of(Optimizations.values())),
    BFS(Set.of(Optimizations.NUMBER_OF_NODES));

    private final Set<Optimizations> allowedOptimizations;

    Algorithms(Set<Optimizations> allowedOptimizations) {
      this.allowedOptimizations = allowedOptimizations;
    }

    public boolean isOptimizationAllowed(Optimizations optimization){
      return allowedOptimizations.contains(optimization);
    }
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
    if(!algorithms.isOptimizationAllowed(optimizations)){
      throw new IllegalArgumentException("This algorithm does not support selected optimization");
    }

    var calculator = optimizations.supplier.get();

    return switch(algorithms){
      case A_STAR -> new AStarPathfindingStrategy(calculator);
      case DIJKSTRA -> new DijkstraPathfindingStrategy(calculator);
      case BELLMAN_FORD -> new BellmanFordPathfindingStrategy(calculator);
      case BFS -> new BFSPathfindingStrategy();
    };
  }
}
