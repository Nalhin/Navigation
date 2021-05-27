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

  def "calculateWeight() should return edge duration in minutes"(Coordinates fromCoordinates,
      Coordinates toCoordinates, double expectedDuration, int maxSpeed) {
    when:
    def actualDistance = edgeWeightCalculator.
        calculateWeight(
            new Edge(new Vertex(1, fromCoordinates), new Vertex(2, toCoordinates), maxSpeed))
    then:
    actualDistance closeTo(expectedDuration, 0.001)
    where:
    fromCoordinates                        | toCoordinates                           | maxSpeed ||
        expectedDuration
    new Coordinates(14.552797, 121.058805) | new Coordinates(14.593999, 120.994260)  | 50       ||
        9.98508
    new Coordinates(77.870317, 96.591876)  | new Coordinates(21.719527, -4.815018)   | 80       ||
        5933.121
    new Coordinates(-17.727830, 23.704799) | new Coordinates(58.585396, -130.279576) | 100      ||
        9000.985812
  }


  def "estimateWeight() should return duration in minutes between vertices with max possible speed"(
      Coordinates fromCoordinates, Coordinates toCoordinates, double expected) {
    when:
    def actualDistance = edgeWeightCalculator.
        estimateWeight(new Vertex(1, fromCoordinates), new Vertex(2, toCoordinates))
    then:
    actualDistance closeTo(expected, 0.001)
    where:
    fromCoordinates                        | toCoordinates                           || expected
    new Coordinates(14.552797, 121.058805) | new Coordinates(14.593999, 120.994260)  || 3.5661
    new Coordinates(77.870317, 96.591876)  | new Coordinates(21.719527, -4.815018)   || 3390.3548571
    new Coordinates(-17.727830, 23.704799) | new Coordinates(58.585396, -130.279576) || 6429.27558
  }
}
