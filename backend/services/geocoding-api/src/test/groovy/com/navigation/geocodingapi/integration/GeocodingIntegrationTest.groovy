package com.navigation.geocodingapi.integration

import com.navigation.geocodingapi.test.IntegrationTest
import com.navigation.geocodingapi.test.WebElasticSearchSpecification
import org.springframework.http.HttpStatus

@IntegrationTest
class GeocodingIntegrationTest extends WebElasticSearchSpecification {

  private static final ADDRESS_INDEX = "address"

  def static anAddress() {
    [
        id         : 31005854,
        city       : "Warszawa",
        country    : "Poland",
        houseNumber: "110",
        location   : [coordinates: [20.9459496d, 52.1769081d], type: "Point"],
        postCode   : "30-340",
        street     : "Aleja Krakowska",
    ]
  }

  void cleanup() {
    clearIndex(ADDRESS_INDEX)
    clearCache()
  }

  def "GET /geocode should return 200 (OK) status code and empty list when match is not found"() {
    given:
    saveInIndex(anAddress(), ADDRESS_INDEX)
    def search = "no match"
    when:
    def response = apiClient()
        .queryParams("address", search)
        .get("/geocode")
    def body = parseJSON(response.body.asString()) as List
    then:
    verifyAll {
      response.statusCode == HttpStatus.OK.value()
      body == []
    }
  }


  def "GET /geocode should return 200 (OK) status code and similar address"() {
    given:
    saveInIndex(anAddress(), ADDRESS_INDEX)
    def search = "Warszawa"
    when:
    def response = apiClient()
        .queryParams("address", search)
        .get("/geocode")
    def body = parseJSON(response.body.asString()) as List
    then:
    verifyAll {
      response.statusCode == HttpStatus.OK.value()
      body.size() == 1
      verifyAll(body[0]) {
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
  }


  def "GET /geocode should return 200 (OK) status code and similar address when read from cache"() {
    given:
    saveInIndex(anAddress(), ADDRESS_INDEX)
    def search = "Warszawa"
    when:
    apiClient()
        .queryParams("address", search)
        .get("/geocode")
    and:
    def response = apiClient()
        .queryParams("address", search)
        .get("/geocode")
    def body = parseJSON(response.body.asString()) as List
    then:
    verifyAll {
      response.statusCode == HttpStatus.OK.value()
      body.size() == 1
      verifyAll(body[0]) {
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
  }
}