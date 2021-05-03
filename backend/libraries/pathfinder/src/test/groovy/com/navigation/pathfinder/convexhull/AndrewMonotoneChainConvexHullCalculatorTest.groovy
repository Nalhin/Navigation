package com.navigation.pathfinder.convexhull

import com.navigation.pathfinder.graph.Coordinates
import com.navigation.pathfinder.graph.Vertex
import spock.lang.Specification
import spock.lang.Subject

class AndrewMonotoneChainConvexHullCalculatorTest extends Specification {

  @Subject
  def convexHulkCalculator = new AndrewMonotoneChainConvexHullCalculator()

  def "calculateConvexHull() should return all vertices when 2 or less vertices were given"() {
    given:
    def vertices = [
        new Vertex(1, new Coordinates(1, 1)),
        new Vertex(2, new Coordinates(2, 1)),
    ]
    when:
    def result = convexHulkCalculator.calculateConvexHull(vertices)
    then:
    result == vertices
  }

  def "calculateConvexHull() should remove excess vertices and determine a valid convex hull when given 7 points"() {
    given:
    def vertices = [
        new Vertex(1, new Coordinates(0, 3)),
        new Vertex(2, new Coordinates(2, 4)),
        new Vertex(3, new Coordinates(1, 1)),
        new Vertex(4, new Coordinates(2, 1)),
        new Vertex(5, new Coordinates(3, 0)),
        new Vertex(6, new Coordinates(0, 0)),
        new Vertex(7, new Coordinates(3, 3)),
    ]
    when:
    def result = convexHulkCalculator.calculateConvexHull(vertices)
    then:
    result.collect { it.id } == [7L, 5L, 6L, 1L, 2L]
  }

  def "calculateConvexHull() should remove excess vertices and determine a valid convex hull when given an additional point"() {
    given:
    def vertices = [
        new Vertex(1, new Coordinates(0, 3)),
        new Vertex(2, new Coordinates(1, 1)),
        new Vertex(3, new Coordinates(2, 2)),
        new Vertex(4, new Coordinates(4, 4)),
        new Vertex(5, new Coordinates(0, 0)),
        new Vertex(6, new Coordinates(1, 5)),
        new Vertex(7, new Coordinates(3, 1)),
        new Vertex(8, new Coordinates(3, 3)),
    ]
    when:
    def result = convexHulkCalculator.calculateConvexHull(vertices)
    then:
    result.collect { it.id } == [4L, 7L, 5L, 1L, 6L]
  }
}
