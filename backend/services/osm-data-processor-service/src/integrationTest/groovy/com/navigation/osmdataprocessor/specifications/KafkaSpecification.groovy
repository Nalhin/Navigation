package com.navigation.osmdataprocessor.specifications

import com.navigation.osmdataprocessor.annotations.IntegrationTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
@IntegrationTest
@SpringBootTest
@ContextConfiguration(initializers = Initializer)
class KafkaSpecification extends Specification {

  @Shared
  static KafkaContainer kafka = new KafkaContainer(
      DockerImageName.parse("confluentinc/cp-kafka:5.5.4"))

  static class Initializer implements
      ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      kafka.start()
      def values = TestPropertyValues.of(
          "spring.kafka.bootstrap-servers=${kafka.getBootstrapServers()}",
      )
      values.applyTo(configurableApplicationContext)
    }
  }
}