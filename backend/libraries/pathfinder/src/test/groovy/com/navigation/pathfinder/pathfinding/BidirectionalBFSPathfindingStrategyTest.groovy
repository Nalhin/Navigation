package com.navigation.pathfinder.pathfinding

import spock.lang.Specification

import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.vertexCountTestGraphConnected
import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.vertexCountTestGraphDisconnected

class BidirectionalBFSPathfindingStrategyTest extends Specification {

  def "findShortestPath() should return the shortest path"(
      ShortestPathPathfindingTestGraphs.TestGraphSummary testGraph) {
    given:
    def strategy = new BidirectionalBFSPathfindingStrategy()
    when:
    def path = strategy.findPath(testGraph.start, testGraph.end, testGraph.graph)
    then:
    path.simplePath() == testGraph.shortestPath
    where:
    testGraph                          || _
    vertexCountTestGraphConnected()    || _
    vertexCountTestGraphDisconnected() || _
  }
}
