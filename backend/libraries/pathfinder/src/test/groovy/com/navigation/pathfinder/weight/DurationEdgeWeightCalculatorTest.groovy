package com.navigation.pathfinder.weight

import com.navigation.pathfinder.graph.Coordinates
import com.navigation.pathfinder.graph.Edge
import com.navigation.pathfinder.graph.Vertex
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import static spock.util.matcher.HamcrestMatchers.closeTo

class DurationEdgeWeightCalculatorTest extends Specification {

  @Subject
  @Shared
  def edgeWeightCalculator = new DurationEdgeWeightCalculator()

  def "calculateWeight() should return edge duration in minutes"(Edge edge) {
    when:
    def actualDistance = edgeWeightCalculator.calculateWeight(edge)
    then:
    actualDistance closeTo(expectedDistance, 0.001)
    where:
    edge                                                             || expectedDistance
    new Edge(new Vertex(1, new Coordinates(14.552797, 121.058805)),
        new Vertex(2, new Coordinates(14.593999, 120.994260)), 50)   || 9.98508
    new Edge(new Vertex(1, new Coordinates(77.870317, 96.591876)),
        new Vertex(2, new Coordinates(21.719527, -4.815018)), 80)    || 5933.121
    new Edge(new Vertex(1, new Coordinates(-17.727830, 23.704799)),
        new Vertex(2, new Coordinates(58.585396, -130.279576)), 100) || 9000.985812
  }
}
