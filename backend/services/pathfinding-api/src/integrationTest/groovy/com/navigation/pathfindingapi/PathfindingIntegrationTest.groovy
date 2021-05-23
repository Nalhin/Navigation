package com.navigation.pathfindingapi

import com.navigation.pathfindingapi.annotations.IntegrationTest
import com.navigation.pathfindingapi.specifications.WebMongoDBSpecification
import org.springframework.http.HttpStatus

@IntegrationTest
class PathfindingIntegrationTest extends WebMongoDBSpecification {

  def "GET /reverse-geocode should return 200 (OK) status code and closest address"() {
    when:
    def response = apiClient()
        .get("/pathfinding/algorithms/DIJKSTRA/available-optimizations")
    then:
    response.statusCode() == HttpStatus.OK.value()
    parseJSON(response.body.asString()) as Set == ['DISTANCE', 'NUMBER_OF_NODES', 'TIME'] as Set
  }
}