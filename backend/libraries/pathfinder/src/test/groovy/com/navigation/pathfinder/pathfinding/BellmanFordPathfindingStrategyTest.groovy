package com.navigation.pathfinder.pathfinding

import spock.lang.Specification

import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.euclideanDistanceTestGraphConnected
import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.euclideanDistanceTestGraphDisconnected
import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.vertexCountTestGraphConnected
import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.vertexCountTestGraphDisconnected

class BellmanFordPathfindingStrategyTest extends Specification {

  def "findShortestPath() should return the shortest path"(
      ShortestPathPathfindingTestGraphs.TestGraphSummary testGraph) {
    given:
    def strategy = new BellmanFordPathfindingStrategy(testGraph.calculator)
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
