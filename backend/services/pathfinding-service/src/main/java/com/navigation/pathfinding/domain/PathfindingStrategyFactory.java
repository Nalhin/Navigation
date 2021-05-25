package com.navigation.pathfinding.domain;

import com.navigation.pathfinder.pathfinding.*;
import io.vavr.control.Option;
import org.springframework.stereotype.Component;

@Component
public class PathfindingStrategyFactory implements Pathfinders {

  public Option<PathfindingStrategy> selectPathfinder(
          PathfindingAlgorithms pathfindingAlgorithms, PathfindingOptimizations pathfindingOptimizations) {
    if(!pathfindingAlgorithms.isOptimizationAllowed(pathfindingOptimizations)){
      return Option.none();
    }

    var calculator = pathfindingOptimizations.calculator.get();

    return Option.of(switch(pathfindingAlgorithms){
      case A_STAR -> new AStarPathfinder(calculator);
      case DIJKSTRA -> new DijkstraPathfinder(calculator);
      case BELLMAN_FORD -> new BellmanFordPathfinder(calculator);
      case BIDIRECTIONAL_DIJKSTRA -> new BidirectionalDijkstraPathfinder(calculator);
      case BFS -> new BFSPathfinder();
      case BIDIRECTIONAL_BFS -> new BidirectionalBFSPathfinder();
      case DFS -> new DFSPathfinder();
      case BIDIRECTIONAL_A_STAR -> new BidirectionalAStarPathfinder(calculator);
      case GREEDY_BEST_FIRST_SEARCH -> new GreedyBestFirstSearchPathfinder(calculator);
      case BIDIRECTIONAL_GREEDY_BEST_FIRST_SEARCH -> new BidirectionalGreedyBestFirstSearchPathfinder(calculator);
    });
  }
}
