package com.navigation.pathfinder.pathfinding

import com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.TestGraphSummary
import spock.lang.Specification

import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.*

class BidirectionalDijkstraPathfinderTest extends Specification {

  def "findPath() should return the shortest path"(TestGraphSummary testGraph) {
    given:
    def pathfinder = new BidirectionalDijkstraPathfinder(testGraph.calculator)
    when:
    def path = pathfinder.findPath(testGraph.start, testGraph.end, testGraph.graph)
    then:
    path.simplePath() == testGraph.shortestPath
    where:
    testGraph                                || _
    euclideanDistanceTestGraphConnected()    || _
    euclideanDistanceTestGraphDisconnected() || _
    vertexCountTestGraphConnected()          || _
    vertexCountTestGraphDisconnected()       || _
  }
}
