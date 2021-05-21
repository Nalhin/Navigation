package com.navigation.pathfinder.pathfinding

import com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.TestGraphSummary
import spock.lang.Specification

import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.*

class BidirectionalDijkstraPathfindingStrategyTest extends Specification {

  def "findShortestPath() should return the shortest path"(TestGraphSummary testGraph) {
    given:
    def strategy = new BidirectionalDijkstraPathfindingStrategy(testGraph.calculator)
    when:
    def path = strategy.findShortestPath(testGraph.start, testGraph.end, testGraph.graph)
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
