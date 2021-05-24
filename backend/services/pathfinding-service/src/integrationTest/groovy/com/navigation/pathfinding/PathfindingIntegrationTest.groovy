package com.navigation.pathfinding

import com.navigation.pathfinding.annotations.IntegrationTest
import com.navigation.pathfinding.specifications.WebMongoDBSpecification
import org.springframework.http.HttpStatus

import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.that

@IntegrationTest
class PathfindingIntegrationTest extends WebMongoDBSpecification {

  def "GET /reverse-geocode should return 200 (OK) status code and closest address"() {
    when:
    def response = apiClient()
        .get("/pathfinding/algorithms/DIJKSTRA/available-optimizations")
    then:
    response.statusCode() == HttpStatus.OK.value()
    that parseJSON(response.body.asString()), containsInAnyOrder('DISTANCE', 'NUMBER_OF_NODES', 'TIME')
  }
}