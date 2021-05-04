package com.navigation.pathfinder.path

import com.navigation.pathfinder.graph.Coordinates
import com.navigation.pathfinder.graph.Edge
import com.navigation.pathfinder.graph.Vertex
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class PathSummaryCreatorTest extends Specification {

  @Subject
  @Shared
  def pathSummaryCreator = new PathSummaryCreator()

  def "createUnidirectionalPath() should return path between start and end vertex present in predecessor tree"() {
    given:
    def start = new Vertex(1, new Coordinates(1, 1))
    def mid = new Vertex(2, new Coordinates(1, 1))
    def end = new Vertex(3, new Coordinates(1, 1))
    def predecessorTree = [(mid): new Edge(start, mid, 50), (end): new Edge(mid, end, 50)]
    when:
    def result = pathSummaryCreator.createUnidirectionalPath(start, end, predecessorTree)
    then:
    result.simplePath() == [start, mid, end]
  }

  def "createUnidirectionalPath() should return an empty path when start vertex and end vertex are not connected in predecessor tree"() {
    given:
    def start = new Vertex(1, new Coordinates(1, 1))
    def randomVertex = new Vertex(4, new Coordinates(1, 1))
    def mid = new Vertex(2, new Coordinates(1, 1))
    def end = new Vertex(3, new Coordinates(1, 1))
    def predecessorTree = [(mid): new Edge(randomVertex, mid, 50),
                           (end): new Edge(mid, end, 50)]
    when:
    def result = pathSummaryCreator.createUnidirectionalPath(start, end, predecessorTree)
    then:
    verifyAll(result) {
      simplePath() == []
      !isFound()
    }
  }

  def "createBidirectionalPath() should return path between start and end vertex present in predecessor tree"() {
    given:
    def start = new Vertex(1, new Coordinates(1, 1))
    def mid = new Vertex(2, new Coordinates(1, 1))
    def end = new Vertex(3, new Coordinates(1, 1))
    def predecessorTreeStart = [(mid): new Edge(start, mid, 50)]
    def predecessorTreeEnd = [(mid): new Edge(end, mid, 50)]
    when:
    def result = pathSummaryCreator.
        createBidirectionalPath(start, mid, end, predecessorTreeStart, predecessorTreeEnd)
    then:
    result.simplePath() == [start, mid, end]
  }

  def "createBidirectionalPath() should return an empty path when start vertex and mid vertex are not connected in predecessor tree"() {
    given:
    def start = new Vertex(1, new Coordinates(1, 1))
    def randomVertex = new Vertex(4, new Coordinates(1, 1))
    def mid = new Vertex(2, new Coordinates(1, 1))
    def end = new Vertex(3, new Coordinates(1, 1))
    def predecessorTreeStart = [(mid): new Edge(randomVertex, mid, 50)]
    def predecessorTreeEnd = [(mid): new Edge(end, mid, 50)]
    when:
    def result = pathSummaryCreator.
        createBidirectionalPath(start, mid, end, predecessorTreeStart, predecessorTreeEnd)
    then:
    verifyAll(result) {
      simplePath() == []
      !isFound()
    }
  }


  def "createBidirectionalPath() should return an empty path when mid vertex and end vertex are not connected in predecessor tree"() {
    given:
    def start = new Vertex(1, new Coordinates(1, 1))
    def randomVertex = new Vertex(4, new Coordinates(1, 1))
    def mid = new Vertex(2, new Coordinates(1, 1))
    def end = new Vertex(3, new Coordinates(1, 1))
    def predecessorTreeStart = [(mid): new Edge(start, mid, 50)]
    def predecessorTreeEnd = [(mid): new Edge(randomVertex, mid, 50)]
    when:
    def result = pathSummaryCreator.
        createBidirectionalPath(start, mid, end, predecessorTreeStart, predecessorTreeEnd)
    then:
    verifyAll(result) {
      simplePath() == []
      !isFound()
    }
  }
}
