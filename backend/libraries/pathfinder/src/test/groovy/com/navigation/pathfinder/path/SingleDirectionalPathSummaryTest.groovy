package com.navigation.pathfinder.path

import com.navigation.pathfinder.graph.Coordinates
import com.navigation.pathfinder.graph.Edge
import com.navigation.pathfinder.graph.Vertex
import spock.lang.Specification

import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestMatchers.closeTo
import static spock.util.matcher.HamcrestSupport.that

class SingleDirectionalPathSummaryTest extends Specification {

  def "getSimplePath() should return path consisting of vertices"() {
    given:
    def first = new Vertex(1, new Coordinates(1, 2))
    def second = new Vertex(2, new Coordinates(2, 2))
    def path = [new Edge(first, second, 50)]
    def pathSummary = new SingleDirectionalPathSummary(path, [] as Set)
    when:
    def result = pathSummary.simplePath()
    then:
    result == [first, second]
  }


  def "getSimplePath() should return empty list when path is empty"() {
    given:
    def path = []
    def pathSummary = new SingleDirectionalPathSummary(path, [] as Set)
    when:
    def result = pathSummary.simplePath()
    then:
    result == []
  }

  def "numberOfVertices() should return the number of vertices in path"() {
    given:
    def path = [new Edge(
        new Vertex(1, new Coordinates(1, 2)),
        new Vertex(2, new Coordinates(2, 2)),
        50
    )]
    def pathSummary = new SingleDirectionalPathSummary(path, [] as Set)
    when:
    def result = pathSummary.numberOfVertices()
    then:
    result == 2
  }

  def "numberOfVertices() should return 0 when path is empty"() {
    given:
    def path = []
    def pathSummary = new SingleDirectionalPathSummary(path, [] as Set)
    when:
    def result = pathSummary.numberOfVertices()
    then:
    result == 0
  }


  def "totalVisitedVertices() should return the number of visited vertices"() {
    given:
    def visitedVertices = [
        new Vertex(1, new Coordinates(1, 1)),
        new Vertex(2, new Coordinates(2, 2))
    ] as Set
    def pathSummary = new SingleDirectionalPathSummary([], visitedVertices)
    when:
    def result = pathSummary.totalVisitedVertices()
    then:
    result == 2
  }


  def "totalDistance() should return path distance"() {
    given:
    def path = [new Edge(
        new Vertex(1, new Coordinates(14.552797, 121.058805)),
        new Vertex(2, new Coordinates(14.593999, 120.994260)),
        50
    )]
    def pathSummary = new SingleDirectionalPathSummary(path, [] as Set)
    when:
    def result = pathSummary.totalDistance()
    then:
    result closeTo(8.3209, 0.0001)
  }

  def "totalDuration() should return path duration"() {
    given:
    def path = [new Edge(
        new Vertex(1, new Coordinates(14.552797, 121.058805)),
        new Vertex(2, new Coordinates(14.593999, 120.994260)),
        50
    )]
    def pathSummary = new SingleDirectionalPathSummary(path, [] as Set)
    when:
    def result = pathSummary.totalDuration()
    then:
    result closeTo(9.98508, 0.001)
  }

  def "searchBoundaries() should return path search boundaries"() {
    given:
    def firstVertex =
        new Vertex(1, new Coordinates(1, 1))
    def secondVertex =
        new Vertex(2, new Coordinates(3, 3))
    def thirdVertex =
        new Vertex(3, new Coordinates(1, 5))
    def pathSummary = new SingleDirectionalPathSummary([],
        [firstVertex, secondVertex, thirdVertex] as Set)
    when:
    def result = pathSummary.searchBoundaries()
    then:
    verifyAll(result) {
      size() == 1
      that getAt(0), containsInAnyOrder(firstVertex, secondVertex, thirdVertex)
    }
  }


  def "isFound() should return whether the path is empty"(List<Edge> path, boolean expectedResult) {
    given:
    def pathSummary = new SingleDirectionalPathSummary(path, [] as Set)
    expect:
    pathSummary.isFound() == expectedResult
    where:
    path || expectedResult
    []   || false
    [new Edge(
        new Vertex(1, new Coordinates(14.552797, 121.058805)),
        new Vertex(2, new Coordinates(14.593999, 120.994260)),
        50
    )]   || true
  }
}
