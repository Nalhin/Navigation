package com.navigation.osmdataprocessor

import com.navigation.osmdataprocessor.annotations.IntegrationTest
import com.navigation.osmdataprocessor.specifications.KafkaSpecification
import com.navigation.parser.provider.OSMProvider
import com.navigation.parser.provider.OSMProviderInMemory
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.core.metrics.ApplicationStartup
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@IntegrationTest
@Import([OsmTestConfig])
class OsmDataProcessorIntegrationTest extends KafkaSpecification {

  @SpringBean
  ApplicationStartup startup = Mock()
  @Autowired
  RootExporter rootExporter

  @Autowired
  KafkaAddressTestListener addressListener
  @Autowired
  KafkaStreetTestListener streetListener

  def "Should export osm file to kafka topics"() {
    when:
    rootExporter.onApplicationEvent(Stub(ApplicationReadyEvent))
    then:
    verifyAll {
      addressListener.addressPublished().await(10, TimeUnit.SECONDS)
      streetListener.streetNodesPublished().await(10, TimeUnit.SECONDS)
      streetListener.streetConnectionsPublished().await(10, TimeUnit.SECONDS)
    }
  }

  @TestConfiguration
  static class OsmTestConfig {

    private static ADDRESS_OSM_XML = """<?xml version='1.0' encoding='UTF-8'?>
<osm version='0.6' generator='JOSM'>
  <node id='358802885' timestamp='2009-03-11T06:30:08Z' user='random' visible='true' version='1' lat='52.229676' lon='21.012229'>
    <tag k='addr:city' v='Warsaw' />
    <tag k='addr:housenumber' v='1' />
    <tag k='addr:street' v='Towarowa' />
    <tag k='addr:postcode' v='00-844' />
  </node>
  <node id='453966480' timestamp='2009-08-02T03:35:44Z' user='random' visible='true' version='1' lat='52.229676' lon='21.012227' />
  <node id='453966490' timestamp='2009-08-02T03:36:02Z' user='random' visible='true' version='1' lat='52.229677' lon='21.012228' />
  <node id='453966131' timestamp='2009-08-02T03:35:44Z' user='random' visible='true' version='1' lat='52.229678' lon='21.012229' />
  <way id='38407529' timestamp='2009-08-02T03:37:41Z' user='random' visible='true' version='1'>
    <nd ref='453966480' />
    <nd ref='453966490' />
    <tag k='highway' v='road' />
    <tag k='maxspeed' v='60' />
  </way>
</osm>"""

    @Bean
    @Primary
    OSMProvider osmProviderTest() {
      return new OSMProviderInMemory(ADDRESS_OSM_XML)
    }
  }

  @Component
  static class KafkaStreetTestListener {

    private CountDownLatch streetNodeLatch = new CountDownLatch(2)
    private CountDownLatch streetConnectionsLatch = new CountDownLatch(2)

    @KafkaListener(topics = '${infrastructure.topics.street-nodes}', groupId = 'street-nodes')
    void receiveNode() {
      streetNodeLatch.countDown()
    }

    @KafkaListener(topics = '${infrastructure.topics.street-connections}', groupId = 'street-connections')
    void receiveConnection() {
      streetConnectionsLatch.countDown()
    }

    CountDownLatch streetConnectionsPublished() {
      return streetConnectionsLatch
    }

    CountDownLatch streetNodesPublished() {
      return streetNodeLatch
    }
  }

  @Component
  static class KafkaAddressTestListener {

    private CountDownLatch addressLatch = new CountDownLatch(1)

    @KafkaListener(topics = '${infrastructure.topics.address}', groupId = 'address')
    void receive() {
      addressLatch.countDown()
    }

    CountDownLatch addressPublished() {
      return addressLatch
    }
  }
}