package com.navigation.osmdataprocessor.street.application

import com.navigation.osmdataprocessor.street.domain.StreetConnection
import com.navigation.osmdataprocessor.street.domain.StreetConnectionExtractor
import com.navigation.osmdataprocessor.street.domain.StreetNode
import com.navigation.osmdataprocessor.street.domain.StreetNodeExtractor
import com.navigation.parser.provider.OSMProviderInMemory
import spock.lang.Specification

class StreetProcessorTest extends Specification {

  def STREET_OSM_XML = """<?xml version='1.0' encoding='UTF-8'?>
<osm version='0.6' generator='JOSM'>
  <node id='453966480' timestamp='2009-08-02T03:35:44Z' user='Apo42' visible='true' version='1' lat='52.229676' lon='21.012227' />
  <node id='453966490' timestamp='2009-08-02T03:36:02Z' user='Apo42' visible='true' version='1' lat='52.229677' lon='21.012228' />
  <node id='453966131' timestamp='2009-08-02T03:35:44Z' user='Apo42' visible='true' version='1' lat='52.229678' lon='21.012229' />
  <way id='38407529' timestamp='2009-08-02T03:37:41Z' user='Apo42' visible='true' version='1'>
    <nd ref='453966480' />
    <nd ref='453966490' />
    <tag k='highway' v='road' />
    <tag k='oneway' v='yes' />
    <tag k='maxspeed' v='60' />
  </way>
</osm>"""

  def "processAndSend() should send street connections and nodes contained in connections"() {
    given:
    def processedStreetSender = Mock(ProcessedStreetPublisher)
    def processor = new StreetProcessor(
        new OSMProviderInMemory(STREET_OSM_XML),
        new StreetExporter(
            processedStreetSender,
            new StreetConnectionExtractor(),
            new StreetNodeExtractor()
        )
    )
    when:
    def actualResult = processor.processAndPublish().get()
    then:
    verifyAll(actualResult) {
      totalParsed() == 4
      totalExported() == 3
      totalAccepted() == 3
    }
    with(processedStreetSender) {
      1 * publishProcessedStreetNode('453966480', new StreetNode(453966480, 52.229676, 21.012227))
      1 * publishProcessedStreetNode('453966490', new StreetNode(453966490, 52.229677, 21.012228))
      0 * publishProcessedStreetNode(_, _)

      1 * publishProcessedStreetConnection(
          "453966480#453966490",
          new StreetConnection("453966480#453966490", 453966480, 453966490, 60))
      0 * publishProcessedStreetConnection(_, _)
    }
  }
}
