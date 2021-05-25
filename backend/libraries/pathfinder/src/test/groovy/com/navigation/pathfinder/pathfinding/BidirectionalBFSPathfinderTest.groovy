package com.navigation.pathfinder.pathfinding

import spock.lang.Specification

import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.*

class BidirectionalBFSPathfinderTest extends Specification {

  def "findPath() should return the shortest path"(TestGraphSummary testGraph) {
    given:
    def strategy = new BidirectionalBFSPathfinder()
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
