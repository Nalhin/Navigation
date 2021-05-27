package com.navigation.pathfinder.pathfinding

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.vertexCountTestGraphConnected
import static com.navigation.pathfinder.pathfinding.ShortestPathPathfindingTestGraphs.vertexCountTestGraphDisconnected

class DFSPathfinderTest extends Specification {

  @Subject
  @Shared
  def pathfinder = new DFSPathfinder()

  def "findPath() should find path between start and end"() {
    given:
    def testGraph = vertexCountTestGraphConnected()
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
    def testGraph = vertexCountTestGraphDisconnected()
    when:
    def result = pathfinder.findPath(testGraph.start, testGraph.end, testGraph.graph)
    then:
    verifyAll(result) {
      !isFound()
    }
  }
}
