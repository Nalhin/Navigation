package com.navigation.pathfinding.domain

import com.navigation.pathfinder.pathfinding.AStarPathfinder
import com.navigation.pathfinder.pathfinding.BFSPathfinder
import com.navigation.pathfinder.pathfinding.BellmanFordPathfinder
import com.navigation.pathfinder.pathfinding.BidirectionalAStarPathfinder
import com.navigation.pathfinder.pathfinding.BidirectionalBFSPathfinder
import com.navigation.pathfinder.pathfinding.BidirectionalGreedyBestFirstSearchPathfinder
import com.navigation.pathfinder.pathfinding.DFSPathfinder
import com.navigation.pathfinder.pathfinding.DijkstraPathfinder
import com.navigation.pathfinder.pathfinding.GreedyBestFirstSearchPathfinder
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class PathfindingStrategyFactoryTest extends Specification {

  @Subject
  @Shared
  def pathfinders = new PathfindingStrategyFactory()

  def "selectPathfinder() should return #algorithm pathfinder with #optimization optimization"(
      PathfindingAlgorithms algorithm, PathfindingOptimizations optimization,
      Class<?> resultClass) {
    when:
    def result = pathfinders.selectPathfinder(algorithm, optimization)
    then:
    result.get().getClass() == resultClass
    where:
    algorithm                                                    | optimization                  ||
        resultClass
    PathfindingAlgorithms.BFS                                    |
        PathfindingOptimizations.NUMBER_OF_NODES                                                 ||
        BFSPathfinder
    PathfindingAlgorithms.BIDIRECTIONAL_BFS                      |
        PathfindingOptimizations.NUMBER_OF_NODES                                                 ||
        BidirectionalBFSPathfinder
    PathfindingAlgorithms.A_STAR                                 |
        PathfindingOptimizations.DISTANCE                                                        ||
        AStarPathfinder
    PathfindingAlgorithms.BIDIRECTIONAL_A_STAR                   | PathfindingOptimizations.TIME ||
        BidirectionalAStarPathfinder
    PathfindingAlgorithms.DIJKSTRA                               | PathfindingOptimizations.TIME ||
        DijkstraPathfinder
    PathfindingAlgorithms.GREEDY_BEST_FIRST_SEARCH               |
        PathfindingOptimizations.DISTANCE                                                        ||
        GreedyBestFirstSearchPathfinder
    PathfindingAlgorithms.BIDIRECTIONAL_GREEDY_BEST_FIRST_SEARCH |
        PathfindingOptimizations.DISTANCE                                                        ||
        BidirectionalGreedyBestFirstSearchPathfinder
    PathfindingAlgorithms.DFS                                    | PathfindingOptimizations.NONE ||
        DFSPathfinder
    PathfindingAlgorithms.BELLMAN_FORD                           | PathfindingOptimizations.TIME ||
        BellmanFordPathfinder
  }


  def "selectPathfinder() should return an empty option when #algorithm does not support #optimization optimization"(
      PathfindingAlgorithms algorithm, PathfindingOptimizations optimization) {
    when:
    def result = pathfinders.selectPathfinder(algorithm, optimization)
    then:
    result.isEmpty()
    where:
    algorithm                                                    | optimization
    PathfindingAlgorithms.BFS                                    | PathfindingOptimizations.DISTANCE
    PathfindingAlgorithms.BIDIRECTIONAL_BFS                      | PathfindingOptimizations.TIME
    PathfindingAlgorithms.A_STAR                                 | PathfindingOptimizations.NONE
    PathfindingAlgorithms.BIDIRECTIONAL_A_STAR                   | PathfindingOptimizations.NONE
    PathfindingAlgorithms.DIJKSTRA                               | PathfindingOptimizations.NONE
    PathfindingAlgorithms.GREEDY_BEST_FIRST_SEARCH               |
        PathfindingOptimizations.NUMBER_OF_NODES
    PathfindingAlgorithms.BIDIRECTIONAL_GREEDY_BEST_FIRST_SEARCH |
        PathfindingOptimizations.NUMBER_OF_NODES
    PathfindingAlgorithms.DFS                                    | PathfindingOptimizations.DISTANCE
    PathfindingAlgorithms.BELLMAN_FORD                           | PathfindingOptimizations.NONE
  }
}
