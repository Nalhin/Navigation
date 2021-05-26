package com.navigation.osmdataprocessor.shared.domain

import spock.lang.Specification

class GeoJsonPointTest extends Specification {

  def "getType() should return 'Point'"() {
    given:
    def geoJsonPoint = new GeoJsonPoint(1d, 2d)
    expect:
    geoJsonPoint.type == "Point"
  }

  def "getCoordinates() should return coordinates as [long, lat]"() {
    given:
    def geoJsonPoint = new GeoJsonPoint(1d, 2d)
    expect:
    geoJsonPoint.coordinates == [2d, 1d]
  }
}
