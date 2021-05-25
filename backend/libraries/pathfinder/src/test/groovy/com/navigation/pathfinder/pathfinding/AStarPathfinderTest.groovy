package com.navigation.pathfinder.pathfinding

import spock.lang.Specification

import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.*

class AStarPathfinderTest extends Specification {

  def "findShortestPath() should return the shortest path"(TestGraphSummary testGraph) {
    given:
    def strategy = new AStarPathfinder(testGraph.calculator)
    when:
    def path = strategy.findPath(testGraph.start, testGraph.end, testGraph.graph)
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
