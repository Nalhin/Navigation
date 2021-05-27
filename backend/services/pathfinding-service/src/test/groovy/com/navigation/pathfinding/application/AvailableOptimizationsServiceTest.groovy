package com.navigation.pathfinding.application

import com.navigation.pathfinding.domain.PathfindingAlgorithms
import com.navigation.pathfinding.domain.PathfindingOptimizations
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.that

class AvailableOptimizationsServiceTest extends Specification {

  @Subject
  @Shared
  def service = new AvailableOptimizationsService()

  def "availableOptimizations() should return available optimalizations for algorithm"() {
    given:
    def algorithm = PathfindingAlgorithms.DIJKSTRA
    when:
    def actualResult = service.availableOptimizations(algorithm)
    then:
    that actualResult, containsInAnyOrder(PathfindingOptimizations.DISTANCE,
        PathfindingOptimizations.NUMBER_OF_NODES, PathfindingOptimizations.TIME)
  }
}
