package com.navigation.pathfinder.graph

import spock.lang.Specification

class CoordinatesTest extends Specification {

  def "Coordinates() should throw Illegal argument exception when longitude is invalid (#longitude)"(double longitude) {
    when:
    new Coordinates(0, longitude)
    then:
    thrown(IllegalArgumentException)
    where:
    longitude << [-181, -360]
  }

  def "Coordinates() should throw Illegal argument exception when latitude is invalid (#latitude)"(double latitude) {
    when:
    new Coordinates(latitude, 0)
    then:
    thrown(IllegalArgumentException)
    where:
    latitude << [-181, -360]
  }

  def "Coordinates() should return coordinates object when latitude nad longitude is valid (#latitude, #longitude)"(double latitude, double longitude) {
    when:
    def coords = new Coordinates(latitude, longitude)
    then:
    coords.latitude == latitude
    coords.longitude == longitude
    where:
    latitude | longitude
    90       | 180
    0        | 0
    -90      | -180
  }
}
