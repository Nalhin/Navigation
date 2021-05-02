package com.navigation.reversegeocodingapi.test

import com.mongodb.BasicDBObject
import io.restassured.RestAssured
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
@IntegrationTest
@SpringBootTest
@ContextConfiguration(initializers = Initializer)
abstract class MongoDBSpecification extends Specification {

  @Autowired
  private MongoTemplate template

  def saveInCollection(Map<String, Object> entity, String collection) {
    template.save(new BasicDBObject(entity), collection)
  }

  def clearCollection(String collectionName) {
    template.getCollection(collectionName).deleteMany(new Document())
  }

  @Shared
  static MongoDBContainer mongoDBContainer = new MongoDBContainer(
      DockerImageName.parse("mongo:4.4.2")).withExposedPorts(27017)


  static class Initializer implements
      ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      mongoDBContainer.start()
      def values = TestPropertyValues.of(
          "spring.data.mongodb.host=${mongoDBContainer.getContainerIpAddress()}",
          "spring.data.mongodb.port=${mongoDBContainer.getMappedPort(27017)}"
      )
      values.applyTo(configurableApplicationContext)
    }
  }
}