package com.navigation.pathfinding.domain;

import com.navigation.pathfinder.pathfinding.*;

class PathfindingStrategyFactory {

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
      case BIDIRECTIONAL_DIJKSTRA -> new BidirectionalDijkstraPathfindingStrategy(calculator);
      case BFS -> new BFSPathfindingStrategy();
      case BIDIRECTIONAL_BFS -> new BidirectionalBFSPathfindingStrategy();
      case DFS -> new DFSPathfindingStrategy();
      case BIDIRECTIONAL_A_STAR -> new BidirectionalAStarPathfindingStrategy(calculator);
      case GREEDY_BEST_FIRST_SEARCH -> new GreedyBestFirstSearchPathfindingStrategy(calculator);
      case BIDIRECTIONAL_GREEDY_BEST_FIRST_SEARCH -> new BidirectionalGreedyBestFirstSearchPathfindingStrategy(calculator);
    };
  }
}
