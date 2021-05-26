package com.navigation.reversegeocodingapi.ui

import com.navigation.reversegeocodingapi.domain.Address
import com.navigation.reversegeocodingapi.domain.Location
import spock.lang.Specification
import spock.lang.Subject

class ReverseGeocodingApiMapperTest extends Specification {

  @Subject
  def mapper = new ReverseGeocodingApiMapper()

  def "toDto() should map Address domain object to AddressDto"() {
    given:
    def address = new Address(
        1,
        new Location(20.2, 21.2),
        "city",
        "country",
        "houseNumber",
        "street",
        "postCode"
    )
    when:
    def result = mapper.toDto(address)
    then:
    verifyAll(result) {
      id == 1
      city == "city"
      country == "country"
      houseNumber == "houseNumber"
      street == "street"
      postCode == "postCode"
      location.latitude == 20.2d
      location.longitude == 21.2d
    }
  }
}
