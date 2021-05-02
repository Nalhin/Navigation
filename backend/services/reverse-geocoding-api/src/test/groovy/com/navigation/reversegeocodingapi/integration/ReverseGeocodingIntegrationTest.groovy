package com.navigation.reversegeocodingapi.integration

import com.navigation.reversegeocodingapi.test.IntegrationTest
import com.navigation.reversegeocodingapi.test.WebMongoDBSpecification
import org.springframework.http.HttpStatus

@IntegrationTest
class ReverseGeocodingIntegrationTest extends WebMongoDBSpecification {

  def static final ADDRESS_COLLECTION = "addresses"

  def static anAddress() {
    [
        _id        : 31005854,
        city       : "Warszawa",
        country    : "Poland",
        houseNumber: "110",
        location   : [coordinates: [20.9459496d, 52.1769081d], type: "Point"],
        postCode   : "30-340",
        street     : "Aleja Krakowska",
    ]
  }

  void cleanup() {
    clearCollection(ADDRESS_COLLECTION)
  }

  def "GET /reverse-geocode should return 200 (OK) status code and closest address"() {
    given:
    saveInCollection(anAddress(), ADDRESS_COLLECTION)
    def params = [latitude: 52.1769081d, longitude: 20.9459496d]
    when:
    def response = apiClient()
        .queryParams(params)
        .get("/reverse-geocode")
    then:
    response.statusCode == HttpStatus.OK.value()
    slurper.parseText(response.body.asString()).with {
      id == 31005854
      city == "Warszawa"
      country == "Poland"
      houseNumber == "110"
      location.latitude == 52.1769081d
      location.longitude == 20.9459496d
      postCode == "30-340"
      street == "Aleja Krakowska"
    }
  }

  def "GET /reverse-geocode should return 404 (NOT FOUND) status code when address is not found"() {
    given:
    def params = [latitude: 52.1769081d, longitude: 20.9459496d]
    when:
    def response = apiClient()
        .queryParams(params)
        .get("/reverse-geocode")
    then:
    response.statusCode == HttpStatus.NOT_FOUND.value()
  }


  def "GET /reverse-geocode should return 404 (NOT FOUND) status code when address is too far"() {
    given:
    saveInCollection(anAddress(), ADDRESS_COLLECTION)
    // 0.3 km diff
    def params = [latitude: 52.174d, longitude: 20.9459496d]
    when:
    def response = apiClient()
        .queryParams(params)
        .get("reverse-geocode")
    then:
    response.statusCode == HttpStatus.NOT_FOUND.value()
  }


  def "GET /reverse-geocode should return 400 (BAD REQUEST) with incomplete params (#params)"(
      Map<String, Double> params) {
    when:
    def response = apiClient()
        .queryParams(params)
        .get("/reverse-geocode")
    then:
    response.statusCode == HttpStatus.BAD_REQUEST.value()
    where:
    params          | _
    [latitude: 20]  | _
    [longitude: 20] | _
  }
}