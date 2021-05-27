package com.navigation.pathfinder.pathfinding

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.*

class BidirectionalBFSPathfinderTest extends Specification {

  @Subject
  @Shared
  def pathfinder = new BidirectionalBFSPathfinder()

  def "findPath() should return the shortest path"(TestGraphSummary testGraph) {
    when:
    def result = pathfinder.findPath(testGraph.start, testGraph.end, testGraph.graph)
    then:
    result.simplePath() == testGraph.shortestPath
    where:
    testGraph                          || _
    vertexCountTestGraphConnected()    || _
    vertexCountTestGraphDisconnected() || _
  }
}
