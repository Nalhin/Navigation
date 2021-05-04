package com.navigation.pathfinder.weight

import com.navigation.pathfinder.graph.Coordinates
import spock.lang.Shared
import spock.lang.Subject

import static spock.util.matcher.HamcrestMatchers.closeTo
import spock.lang.Specification

class HaversineDistanceCalculatorTest extends Specification {

  @Subject
  @Shared
  def distanceCalculator = new HaversineDistanceCalculator()

  def "calculateDistance() should calculate approximate distance in km between #startCoords and #endCoords close to#expectedDistance"(
      Coordinates startCoords, Coordinates endCoords, double expectedDistance) {
    when:
    def actualDistance = distanceCalculator.calculateDistance(startCoords, endCoords)
    then:
    actualDistance closeTo(expectedDistance, 0.0001)
    where:
    startCoords                            | endCoords                               || expectedDistance
    new Coordinates(14.552797, 121.058805) | new Coordinates(14.593999, 120.994260)  || 8.3209
    new Coordinates(77.870317, 96.591876)  | new Coordinates(21.719527, -4.815018)   || 7910.8280
    new Coordinates(-17.727830, 23.704799) | new Coordinates(58.585396, -130.279576) || 15001.64302
  }
}
