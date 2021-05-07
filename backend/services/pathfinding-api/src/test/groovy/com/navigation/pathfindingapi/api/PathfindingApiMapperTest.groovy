package com.navigation.pathfindingapi.api

import com.navigation.pathfinder.graph.Coordinates
import com.navigation.pathfinder.graph.Vertex
import com.navigation.pathfinder.pathfinding.PathSummary
import com.navigation.pathfindingapi.api.dto.params.BoundsRequestDtoParams
import com.navigation.pathfindingapi.api.dto.params.PathRequestDtoParams
import com.navigation.pathfindingapi.api.dto.response.NodeResponseDto
import com.navigation.pathfindingapi.api.dto.shared.PathfindingAlgorithmsDto
import com.navigation.pathfindingapi.api.dto.shared.PathfindingOptimizationsDto
import com.navigation.pathfindingapi.domain.PathWithExecutionSummary
import com.navigation.pathfindingapi.domain.PathfindingAlgorithms
import com.navigation.pathfindingapi.domain.PathfindingOptimizations
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import java.time.Instant

class PathfindingApiMapperTest extends Specification {

  @Shared
  @Subject
  def pathfindingApiMapper = new PathfindingApiMapper()

  def "toResponse(PathWithExecutionSummary) should convert path with summary to PathResponseDto"() {
    given:
    def pathSummary = Stub(PathSummary) {
      simplePath() >> [new Vertex(1, new Coordinates(2, 3)), new Vertex(2, new Coordinates(4, 5))]
      totalDistance() >> 1
      numberOfVertices() >> 2
      totalDuration() >> 3
      totalVisitedVertices() >> 4
      isFound() >> true
      searchBoundaries() >> [[new Vertex(3, new Coordinates(5, 6))]]
    }
    def pathWithExecutionSummary = new PathWithExecutionSummary(
        pathSummary,
        Instant.EPOCH,
        Instant.ofEpochMilli(500),
        PathfindingAlgorithms.DIJKSTRA,
        PathfindingOptimizations.DISTANCE)
    when:
    def result = pathfindingApiMapper.toResponse(pathWithExecutionSummary)
    then:
    verifyAll(result) {
      simplePath ==
          [new NodeResponseDto(id: 1, latitude: 2, longitude: 3),
           new NodeResponseDto(id: 2, latitude: 4, longitude: 5)]
      totalDistance == 1
      totalNodes == 2
      totalVisitedNodes == 4
      totalDuration == 3
      executionDuration == 0.5d
      searchBoundaries == [[new NodeResponseDto(id: 3, latitude: 5, longitude: 6)]]
      algorithm == PathfindingAlgorithmsDto.DIJKSTRA
      optimization == PathfindingOptimizationsDto.DISTANCE
      found
    }
  }

  def "toQuery(PathRequestDtoParams) should map PathRequestDtoParams to PathBetweenCoordinatesQuery"() {
    given:
    def params = new PathRequestDtoParams(
        startLatitude: 1,
        startLongitude: 2,
        endLatitude: 3,
        endLongitude: 4,
        algorithm: PathfindingAlgorithmsDto.BIDIRECTIONAL_BFS,
        optimization: PathfindingOptimizationsDto.DISTANCE)
    when:
    def result = pathfindingApiMapper.toQuery(params)
    then:
    verifyAll(result) {
      start == new Coordinates(1, 2)
      end == new Coordinates(3, 4)
      pathfindingAlgorithm == PathfindingAlgorithms.BIDIRECTIONAL_BFS
      pathfindingOptimization == PathfindingOptimizations.DISTANCE
    }
  }

  def "toBounds() should map BoundsRequestDtoParams to Bounds"() {
    given:
    def params = new BoundsRequestDtoParams(
        minLatitude: 1,
        maxLatitude: 2,
        minLongitude: 3,
        maxLongitude: 4)
    when:
    def result = pathfindingApiMapper.toBounds(params)
    then:
    verifyAll(result) {
      leftBottom == new Coordinates(1, 3)
      topRight == new Coordinates(2, 4)
    }
  }
}
