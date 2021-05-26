package com.navigation.osmdataprocessor.address.application

import com.navigation.osmdataprocessor.address.domain.Address
import com.navigation.osmdataprocessor.address.domain.AddressExtractor
import com.navigation.parser.provider.OSMProviderInMemory
import spock.lang.Specification

class AddressProcessorTest extends Specification {

  def ADDRESS_OSM_XML = """<?xml version='1.0' encoding='UTF-8'?>
<osm version='0.6' generator='JOSM'>
  <node id='358802885' timestamp='2009-03-11T06:30:08Z' user='random' visible='true' version='1' lat='52.229676' lon='21.012229'>
    <tag k='addr:city' v='Warsaw' />
    <tag k='addr:housenumber' v='1' />
    <tag k='addr:street' v='Towarowa' />
    <tag k='addr:postcode' v='00-844' />
  </node>
</osm>"""

  def "processAndSend() should send addresses exporter from osm file"() {
    given:
    def processedAddressSender = Mock(ProcessedAddressSender)
    def processor = new AddressProcessor(
        new OSMProviderInMemory(ADDRESS_OSM_XML),
        new AddressExporter(processedAddressSender, new AddressExtractor())
    )
    def expectedAddress = new Address.AddressBuilder()
        .setCity("Warsaw")
        .setCountry("Poland")
        .setId(358802885)
        .setHouseNumber("1")
        .setStreet("Towarowa")
        .setPostCode("00-844")
        .setLatitude(52.229676D)
        .setLongitude(21.012229D)
        .createAddress()
    when:
    def actualResult = processor.processAndSend().get()
    then:
    verifyAll(actualResult) {
      totalExported() == 1
      totalAccepted() == 1
      totalParsed() == 1
    }
    1 * processedAddressSender.sendProcessedAddress("358802885", expectedAddress)
  }
}
