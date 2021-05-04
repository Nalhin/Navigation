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

  def "calculateWeight() should return edge distance"(Coordinates fromCoordinates,
      Coordinates toCoordinates, double expectedDistance) {
    when:
    def actualDistance = edgeWeightCalculator.
        calculateWeight(new Edge(new Vertex(1, fromCoordinates), new Vertex(2, toCoordinates), 50))
    then:
    actualDistance closeTo(expectedDistance, 0.0001)
    where:
    fromCoordinates                        | toCoordinates                           ||
        expectedDistance
    new Coordinates(14.552797, 121.058805) | new Coordinates(14.593999, 120.994260)  || 8.3209
    new Coordinates(77.870317, 96.591876)  | new Coordinates(21.719527, -4.815018)   || 7910.8280
    new Coordinates(-17.727830, 23.704799) | new Coordinates(58.585396, -130.279576) || 15001.64302

  }
}
