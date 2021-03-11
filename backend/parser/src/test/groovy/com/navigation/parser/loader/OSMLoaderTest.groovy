package com.navigation.parser.loader

import com.navigation.parser.elements.Tag
import com.navigation.parser.exporter.OSMExporterInMemory
import com.navigation.parser.provider.OSMProviderInMemory
import spock.lang.Specification

class OSMLoaderTest extends Specification {

  private final static EXAMPLE_XML = """<?xml version='1.0' encoding='UTF-8'?>
<osm version='0.6' generator='JOSM'>
  <bounds minlat='34.0662408634219' minlon='-118.736715316772' maxlat='34.0731374116421' maxlon='-118.73122215271' origin='OpenStreetMap server' />
  <node id='358802885' timestamp='2009-03-11T06:30:08Z' user='yellowbkpk' visible='true' version='1' lat='34.0666735' lon='-118.734254'>
    <tag k='gnis:created' v='06/14/2000' />
    <tag k='gnis:county_id' v='037' />
    <tag k='name' v='Santa Monica Mountains National Recreation Area' />
    <tag k='leisure' v='park' />
    <tag k='gnis:feature_id' v='277263' />
    <tag k='gnis:state_id' v='06' />
    <tag k='ele' v='243' />
  </node>
  <node id='453966480' timestamp='2009-08-02T03:36:00Z' user='Apo42' visible='true' version='1' lat='34.07234' lon='-118.7343501' />
  <node id='453966482' timestamp='2009-08-02T03:36:01Z' user='Apo42' visible='true' version='1' lat='34.0670965' lon='-118.7322253' />
  <node id='453966143' timestamp='2009-08-02T03:35:45Z' user='Apo42' visible='true' version='1' lat='34.0724577' lon='-118.7364799' />
  <node id='453966130' timestamp='2009-08-02T03:35:44Z' user='Apo42' visible='true' version='1' lat='34.0671122' lon='-118.7364725' />
  <node id='453966490' timestamp='2009-08-02T03:36:02Z' user='Apo42' visible='true' version='1' lat='34.0722227' lon='-118.7322321' />
  <way id='38407529' timestamp='2009-08-02T03:37:41Z' user='Apo42' visible='true' version='1'>
    <nd ref='453966480' />
    <nd ref='453966490' />
    <nd ref='453966482' />
    <nd ref='453966130' />
    <nd ref='453966143' />
    <nd ref='453966480' />
    <tag k='park:type' v='state_park' />
    <tag k='csp:unitcode' v='537' />
    <tag k='admin_level' v='4' />
    <tag k='name' v='Malibu Creek State Park' />
    <tag k='csp:globalid' v='{4A422954-089E-407F-A5B3-1E808F830EAA}' />
    <tag k='leisure' v='park' />
    <tag k='attribution' v='CASIL CSP_Opbdys072008' />
    <tag k='note' v='simplified with josm to reduce node #' />
    <tag k='boundary' v='national_park' />
  </way>
</osm>"""

  def "Should load nodes with matching ids"() {
    setup:
    def exporter = new OSMExporterInMemory()
    def loader = new OSMLoader(new OSMProviderInMemory(EXAMPLE_XML), exporter)
    when:
    loader.loadOSM()
    then:
    def nodes = exporter.getNodes()
    nodes.containsKey("358802885")
    nodes.containsKey("453966480")
    nodes.containsKey("453966482")
    nodes.containsKey("453966143")
    nodes.containsKey("453966130")
    nodes.containsKey("453966490")
    nodes.size() == 6
  }

  def "Should load ways with matching ids"() {
    setup:
    def exporter = new OSMExporterInMemory()
    def loader = new OSMLoader(new OSMProviderInMemory(EXAMPLE_XML), exporter)
    when:
    loader.loadOSM()
    then:
    def ways = exporter.getWays()

    ways.containsKey("38407529")
    ways.size() == 1
  }

  def "Should load node tag data correctly"() {
    setup:
    def exporter = new OSMExporterInMemory()
    def loader = new OSMLoader(new OSMProviderInMemory(EXAMPLE_XML), exporter)
    when:
    loader.loadOSM()
    then:
    def tags = exporter.getNodes()["358802885"].tags
    tags == [new Tag("gnis:created", "06/14/2000"),
             new Tag("gnis:county_id", "037"),
             new Tag("name", "Santa Monica Mountains National Recreation Area"),
             new Tag("leisure", "park"),
             new Tag("gnis:feature_id", "277263"),
             new Tag("gnis:state_id", "06"),
             new Tag("ele", "243")]
  }

  def "Should load way tag data correctly"() {
    setup:
    def exporter = new OSMExporterInMemory()
    def loader = new OSMLoader(new OSMProviderInMemory(EXAMPLE_XML), exporter)
    when:
    loader.loadOSM()
    then:
    def tags = exporter.getWays()["38407529"].tags

    tags == [new Tag("park:type", "state_park"),
             new Tag("csp:unitcode", "537"),
             new Tag("admin_level", "4"),
             new Tag("name", "Malibu Creek State Park"),
             new Tag("csp:globalid", "{4A422954-089E-407F-A5B3-1E808F830EAA}"),
             new Tag("leisure", "park"),
             new Tag("attribution", "CASIL CSP_Opbdys072008"),
             new Tag("note", "simplified with josm to reduce node #"),
             new Tag("boundary", "national_park")]
  }

  def "Should load node ref elements correctly"() {
    setup:
    def exporter = new OSMExporterInMemory()
    def loader = new OSMLoader(new OSMProviderInMemory(EXAMPLE_XML), exporter)
    when:
    loader.loadOSM()
    then:
    def refs = exporter.getWays()["38407529"].nodeReferences
    refs == ["453966480", "453966490", "453966482", "453966130", "453966143", "453966480"]
  }
}