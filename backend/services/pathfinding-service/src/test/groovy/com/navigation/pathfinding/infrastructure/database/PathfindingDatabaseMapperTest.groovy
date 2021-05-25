package com.navigation.pathfinding.infrastructure.database

import com.navigation.pathfinder.graph.Coordinates
import com.navigation.pathfinding.application.PathBetweenCoordinatesUseCase
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class PathfindingDatabaseMapperTest extends Specification {

  @Shared
  @Subject
  def databaseMapper = new PathfindingDatabaseMapper()

  def "toCoordinates() should map GeoJsonPoint to Coordinates"() {
    given:
    def point = new GeoJsonPoint(1, 2)
    when:
    def result = databaseMapper.toCoordinates(point)
    then:
    verifyAll(result) {
      latitude == 2
      longitude == 1
    }
  }

  def "toVertex() should map StreetNodeEntity to Vertex"() {
    given:
    def streetNode = new StreetNodeEntity(1, new GeoJsonPoint(1, 2))
    when:
    def result = databaseMapper.toVertex(streetNode)
    then:
    verifyAll(result) {
      id == 1
      coordinates == new Coordinates(2, 1)
    }
  }

  def "toGeoJsonPoint() should map Coordinates to GeoJsonPoint"() {
    given:
    def coordinates = new Coordinates(2, 1)
    when:
    def result = databaseMapper.toGeoJsonPoint(coordinates)
    then:
    verifyAll(result) {
      x == 1
      y == 2
    }
  }

  def "toBox() should map BoundsQuery to Box"() {
    given:
    def bounds = new PathBetweenCoordinatesUseCase.BoundsQuery(new Coordinates(1, 2), new Coordinates(3, 4))
    when:
    def result = databaseMapper.toBox(bounds)
    then:
    verifyAll(result) {
      first.x == 2
      first.y == 1
      second.x == 4
      second.y == 3
    }
  }
}
