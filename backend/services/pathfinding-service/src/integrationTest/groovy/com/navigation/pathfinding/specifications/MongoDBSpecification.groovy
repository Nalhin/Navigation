package com.navigation.pathfinding.specifications

import com.mongodb.BasicDBObject
import com.navigation.pathfinding.annotations.IntegrationTest
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.data.mongodb.core.MongoTemplate
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

  @Shared
  static MongoDBContainer mongoDBContainer = new MongoDBContainer(
      DockerImageName.parse("mongo:4.4.5")).withExposedPorts(27017)

  def saveInCollection(Map<String, Object> entity, String collection) {
    template.save(new BasicDBObject(entity), collection)
  }

  def saveAllInCollection(List<Map<String,Object>> entities, String collection){
    entities.each {saveInCollection(it, collection)}
  }

  def clearCollection(String collectionName) {
    template.getCollection(collectionName).deleteMany(new Document())
  }

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