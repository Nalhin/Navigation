package com.navigation.pathfinder.pathfinding

import spock.lang.Specification

import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.euclideanDistanceTestGraphConnected
import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.euclideanDistanceTestGraphDisconnected

class BidirectionalGreedyBestFirstSearchPathfinderTest extends Specification {

  def "findPath() should find path between start and end using greedy strategy"() {
    given:
    def testGraph = euclideanDistanceTestGraphConnected()
    def pathfinder = new BidirectionalGreedyBestFirstSearchPathfinder(testGraph.calculator)
    when:
    def result = pathfinder.findPath(testGraph.start, testGraph.end, testGraph.graph)
    then:
    verifyAll(result) {
      simplePath().first() == testGraph.start
      simplePath().last() == testGraph.end
      isFound()
    }
  }

  def "findPath() shouldn't find path in disconnected graph"() {
    given:
    def testGraph = euclideanDistanceTestGraphDisconnected()
    def pathfinder = new BidirectionalGreedyBestFirstSearchPathfinder(testGraph.calculator)
    when:
    def result = pathfinder.findPath(testGraph.start, testGraph.end, testGraph.graph)
    then:
    !result.isFound()
  }
}
