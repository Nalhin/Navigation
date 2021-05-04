package com.navigation.pathfinder.weight

import com.navigation.pathfinder.graph.Coordinates
import com.navigation.pathfinder.graph.Edge
import com.navigation.pathfinder.graph.Vertex
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import static spock.util.matcher.HamcrestMatchers.closeTo

class DistanceEdgeWeightCalculatorTest extends Specification {

  @Subject
  @Shared
  def edgeWeightCalculator = new DistanceEdgeWeightCalculator()

  def "calculateWeight() should return edge distance"(Edge edge) {
    when:
    def actualDistance = edgeWeightCalculator.calculateWeight(edge)
    then:
    actualDistance closeTo(expectedDistance, 0.0001)
    where:
    edge                                                            || expectedDistance
    new Edge(new Vertex(1, new Coordinates(14.552797, 121.058805)),
        new Vertex(2, new Coordinates(14.593999, 120.994260)), 50)  || 8.3209
    new Edge(new Vertex(1, new Coordinates(77.870317, 96.591876)),
        new Vertex(2, new Coordinates(21.719527, -4.815018)), 50)   || 7910.8280
    new Edge(new Vertex(1, new Coordinates(-17.727830, 23.704799)),
        new Vertex(2, new Coordinates(58.585396, -130.279576)), 50) || 15001.64302
  }
}
