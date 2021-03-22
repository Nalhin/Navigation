package com.navigation.pathfinder.distance

import static spock.util.matcher.HamcrestMatchers.closeTo
import spock.lang.Specification

class HaversineDistanceCalculatorTest extends Specification {
  def "calculateDistance() should calculate approximate distance in km between (#lat1, #lon1) and (#lat2, #lon2) that is close to #expectedDistance"(
      double lat1, double lon1, double lat2, double lon2, double expectedDistance) {
    given:
    def distanceCalculator = new HaversineDistanceCalculator()
    when:
    def actualDistance = distanceCalculator.calculateDistance(lat1, lon1, lat2, lon2)
    then:
    actualDistance closeTo(expectedDistance, 0.0001)
    where:
    lat1       | lon1       | lat2      | lon2        || expectedDistance
    14.552797  | 121.058805 | 14.593999 | 120.994260  || 8.3209
    77.870317  | 96.591876  | 21.719527 | -4.815018   || 7910.8280
    -17.727830 | 23.704799  | 58.585396 | -130.279576 || 15001.64302
  }
}
