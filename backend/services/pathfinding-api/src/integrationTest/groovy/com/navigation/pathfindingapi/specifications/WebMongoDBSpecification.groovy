package com.navigation.pathfindingapi.specifications

import com.navigation.pathfindingapi.annotations.IntegrationTest
import groovy.json.JsonSlurper
import io.restassured.RestAssured
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType
import spock.lang.Shared

@IntegrationTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class WebMongoDBSpecification extends MongoDBSpecification {

  @Shared
  private slurper = new JsonSlurper()

  @LocalServerPort
  private int serverPort

  def parseJSON(String jsonString){
    return slurper.parseText(jsonString)
  }

  def apiClient() {
    return RestAssured.given()
        .basePath("/api/v1")
        .port(serverPort)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
  }
}