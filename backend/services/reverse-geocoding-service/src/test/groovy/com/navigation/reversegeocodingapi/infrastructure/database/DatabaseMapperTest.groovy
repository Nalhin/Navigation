package com.navigation.reversegeocodingapi.infrastructure.database

import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import spock.lang.Specification
import spock.lang.Subject

class DatabaseMapperTest extends Specification {

  @Subject
  def mapper = new DatabaseMapper()

  def "toDomain should map AddressEntity to Address domain object"() {
    given:
    def entity = new AddressEntity(
        id: 1,
        location: new GeoJsonPoint(20.1d, 22.1d),
        city: "city",
        country: "country",
        houseNumber: "9B",
        street: "street",
        postCode: "30-330"
    )
    when:
    def actualResult = mapper.toDomain(entity)
    then:
    verifyAll(actualResult) {
      id == 1
      city == "city"
      country == "country"
      houseNumber == "9B"
      street == "street"
      postCode == "30-330"
      location.longitude == 20.1d
      location.latitude == 22.1d
    }
  }
}
