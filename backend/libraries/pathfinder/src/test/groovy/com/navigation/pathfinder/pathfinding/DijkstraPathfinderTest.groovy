package com.navigation.pathfinder.pathfinding

import com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.TestGraphSummary
import spock.lang.Specification

import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.euclideanDistanceTestGraphConnected
import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.euclideanDistanceTestGraphDisconnected
import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.vertexCountTestGraphConnected
import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.vertexCountTestGraphDisconnected

class DijkstraPathfinderTest extends Specification {

  def "findPath() should return the shortest path"(TestGraphSummary testGraph) {
    given:
    def strategy = new DijkstraPathfinder(testGraph.calculator)
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
