package com.navigation.pathfinding

import com.navigation.pathfinding.annotations.IntegrationTest
import com.navigation.pathfinding.specifications.WebMongoDBSpecification
import org.springframework.http.HttpStatus

import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.that
import static spock.util.matcher.HamcrestMatchers.closeTo

@IntegrationTest
class PathfindingIntegrationTest extends WebMongoDBSpecification {

  def static final STREET_CONNECTION_COLLECTION = "streetConnections"
  def static final STREET_NODES_COLLECTION = "streetNodes"

  def static streetConnections() {
    return [[_id: "1#2", fromId: 1, toId: 2, maxSpeed: 50],
            [_id: "2#3", fromId: 2, toId: 3, maxSpeed: 50],
            [_id: "3#4", fromId: 3, toId: 4, maxSpeed: 50]
    ]
  }

  def static streetNodes() {
    return [[_id: 1, location: [coordinates: [19.9d, 50.1d], type: "Point"]],
            [_id: 2, location: [coordinates: [19.8d, 50.2d], type: "Point"]],
            [_id: 3, location: [coordinates: [19.7d, 50.3d], type: "Point"]],
            [_id: 4, location: [coordinates: [19.6d, 50.4d], type: "Point"]],
    ]
  }

  void cleanup() {
    clearCollection(STREET_CONNECTION_COLLECTION)
    clearCollection(STREET_NODES_COLLECTION)
  }

  def 'GET /pathfinding/path-between should return 200 (OK) status code and path details'() {
    given:
    saveAllInCollection(streetConnections(), STREET_CONNECTION_COLLECTION)
    saveAllInCollection(streetNodes(), STREET_NODES_COLLECTION)
    def params = [algorithm     : "DIJKSTRA",
                  optimization  : "DISTANCE",
                  startLatitude : 50.1d,
                  startLongitude: 19.9d,
                  endLatitude   : 50.4d,
                  endLongitude  : 19.6d]
    when:
    def response = apiClient()
        .params(params)
        .get("/pathfinding/path-between")
    def responseBody = parseJSON(response.body.asString())
    then:
    verifyAll(responseBody) {
      response.statusCode == HttpStatus.OK.value()
      totalNodes == 4
      totalVisitedNodes == 4
      simplePath == [
          [id: 1, longitude: 19.9, latitude: 50.1],
          [id: 2, longitude: 19.8, latitude: 50.2],
          [id: 3, longitude: 19.7, latitude: 50.3],
          [id: 4, longitude: 19.6, latitude: 50.4]
      ]
      that searchBoundaries.first() as List<Map>, containsInAnyOrder(
          [id: 1, longitude: 19.9, latitude: 50.1],
          [id: 2, longitude: 19.8, latitude: 50.2],
          [id: 3, longitude: 19.7, latitude: 50.3],
          [id: 4, longitude: 19.6, latitude: 50.4]
      )
      that totalDistance as double, closeTo(39.59528586, 0.00001)
      that totalDuration as double, closeTo(47.51434303, 0.00001)
      optimization == "DISTANCE"
      algorithm == "DIJKSTRA"
    }
  }

  def 'GET /pathfinding/path-between/bounded should return 200 (OK) status code and bounded path between points'() {
    given:
    saveAllInCollection(streetConnections(), STREET_CONNECTION_COLLECTION)
    saveAllInCollection(streetNodes(), STREET_NODES_COLLECTION)
    def params = [algorithm     : "BELLMAN_FORD",
                  optimization  : "TIME",
                  startLatitude : 50.1d,
                  startLongitude: 19.9d,
                  endLatitude   : 50.4d,
                  endLongitude  : 19.6d,
                  minLatitude   : 50d,
                  maxLatitude   : 51d,
                  minLongitude  : 19d,
                  maxLongitude  : 21d]
    when:
    def response = apiClient()
        .params(params)
        .get("/pathfinding/path-between/bounded")
    def responseBody = parseJSON(response.body.asString())
    then:
    verifyAll(responseBody) {
      response.statusCode == HttpStatus.OK.value()
      totalNodes == 4
      totalVisitedNodes == 4
      simplePath == [
          [id: 1, longitude: 19.9, latitude: 50.1],
          [id: 2, longitude: 19.8, latitude: 50.2],
          [id: 3, longitude: 19.7, latitude: 50.3],
          [id: 4, longitude: 19.6, latitude: 50.4]
      ]
      that searchBoundaries.first() as List<Map>, containsInAnyOrder(
          [id: 1, longitude: 19.9, latitude: 50.1],
          [id: 2, longitude: 19.8, latitude: 50.2],
          [id: 3, longitude: 19.7, latitude: 50.3],
          [id: 4, longitude: 19.6, latitude: 50.4]
      )
      that totalDistance as double, closeTo(39.59528586, 0.00001)
      that totalDuration as double, closeTo(47.51434303, 0.00001)
      optimization == "TIME"
      algorithm == "BELLMAN_FORD"
    }
  }

  def "GET /pathfinding/algorithms/DIJKSTRA/available-optimization should return 200 (OK) status code and optimizations offered by the algorithm"() {
    when:
    def response = apiClient()
        .get("/pathfinding/algorithms/DIJKSTRA/available-optimizations")
    then:
    response.statusCode() == HttpStatus.OK.value()
    that parseJSON(response.body.asString()),
        containsInAnyOrder('DISTANCE', 'NUMBER_OF_NODES', 'TIME')
  }
}