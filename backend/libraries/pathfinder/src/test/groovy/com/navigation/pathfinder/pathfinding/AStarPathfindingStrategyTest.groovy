package com.navigation.pathfinder.pathfinding

import spock.lang.Specification

import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.euclideanDistanceTestGraphConnected
import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.euclideanDistanceTestGraphDisconnected

class AStarPathfindingStrategyTest extends Specification {

  def "findShortestPath() should return the shortest path"(
      ShortestPathPathfindingTestGraphs.TestGraphSummary testGraph) {
    given:
    def strategy = new AStarPathfindingStrategy(testGraph.calculator)
    when:
    def path = strategy.findPath(testGraph.start, testGraph.end, testGraph.graph)
    then:
    path.simplePath() == testGraph.shortestPath
    where:
    testGraph                                || _
    euclideanDistanceTestGraphConnected()    || _
    euclideanDistanceTestGraphDisconnected() || _
  }
}
