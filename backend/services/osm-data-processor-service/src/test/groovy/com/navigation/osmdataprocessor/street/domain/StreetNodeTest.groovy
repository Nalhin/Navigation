package com.navigation.osmdataprocessor.street.domain

import com.navigation.osmdataprocessor.shared.domain.GeoJsonPoint
import spock.lang.Specification

class StreetNodeTest extends Specification {

  def "getLocation() should return location as GeoJsonPoint"() {
    given:
    def node = new StreetNode(1, 2d, 3d)
    expect:
    node.location == new GeoJsonPoint(2d, 3d)
  }
}
