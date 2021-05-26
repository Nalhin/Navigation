package com.navigation.osmdataprocessor.address.domain

import com.navigation.osmdataprocessor.shared.domain.GeoJsonPoint
import spock.lang.Specification

class AddressTest extends Specification {

  def "getLocation() should return location as GeoJsonPoint"() {
    given:
    def address = new Address.AddressBuilder()
        .setCity("Warsaw")
        .setCountry("Poland")
        .setId(358802885)
        .setHouseNumber("1")
        .setStreet("Towarowa")
        .setPostCode("00-844")
        .setLatitude(52d)
        .setLongitude(21d)
        .createAddress()
    expect:
    address.location == new GeoJsonPoint(52d, 21d)
  }
}
