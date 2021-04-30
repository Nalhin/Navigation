package com.navigation.pathfindingapi.domain;

import com.navigation.pathfinder.pathfinding.*;

public class PathfindingStrategyFactory {

  public PathfindingStrategy pathfindingStrategy(
          PathfindingAlgorithms pathfindingAlgorithms, PathfindingOptimizations pathfindingOptimizations) {
    if(!pathfindingAlgorithms.isOptimizationAllowed(pathfindingOptimizations)){
      throw new IllegalArgumentException("This algorithm does not support selected optimization");
    }

    var calculator = pathfindingOptimizations.supplier.get();

    return switch(pathfindingAlgorithms){
      case A_STAR -> new AStarPathfindingStrategy(calculator);
      case DIJKSTRA -> new DijkstraPathfindingStrategy(calculator);
      case BELLMAN_FORD -> new BellmanFordPathfindingStrategy(calculator);
      case BFS -> new BFSPathfindingStrategy();
      case BIDIRECTIONAL_BFS -> new BidirectionalBFSPathfindingStrategy();
    };
  }
}
