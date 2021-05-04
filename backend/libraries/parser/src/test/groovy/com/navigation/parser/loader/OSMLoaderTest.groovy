package com.navigation.parser.loader

import com.navigation.parser.elements.Bounds
import com.navigation.parser.elements.Member
import com.navigation.parser.elements.Metadata
import com.navigation.parser.elements.Node
import com.navigation.parser.elements.Relation
import com.navigation.parser.elements.Way
import com.navigation.parser.exporter.OSMExporterInMemory
import com.navigation.parser.provider.OSMProviderInMemory
import com.navigation.parser.specification.OSMLoaderSpecification
import spock.lang.Specification

class OSMLoaderTest extends Specification {

  private final static OSM_XML = """<?xml version='1.0' encoding='UTF-8'?>
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
  <relation id="56688" user="kmvar" uid="56190" visible="true" version="28" changeset="6947637" timestamp="2011-01-12T14:23:49Z">
  <member type="node" ref="358802885" role="inner"/>
  <member type="node" ref="453966480" role="inner"/>
  <member type="way" ref="38407529" role="inner"/>
  <member type="node" ref="453966490" role="inner"/>
  <tag k="name" v="Kstenbus Linie 123"/>
  <tag k="network" v="VVW"/>
  <tag k="operator" v="Regionalverkehr Kste"/>
  <tag k="ref" v="123"/>
  <tag k="route" v="bus"/>
  <tag k="type" v="route"/>
  </relation>
</osm>"""

  def "Should load nodes with matching ids"() {
    given:
    def exporter = new OSMExporterInMemory()
    when:
    new OSMLoader(new OSMProviderInMemory(OSM_XML), exporter).export()
    then:
    def nodes = exporter.exportedData.nodes
    nodes[358802885L].id == 358802885
    nodes[453966480L].id == 453966480
    nodes[453966482L].id == 453966482
    nodes[453966143L].id == 453966143
    nodes[453966130L].id == 453966130
    nodes[453966490L].id == 453966490
    nodes.size() == 6
  }

  def "Should load ways with matching ids"() {
    given:
    def exporter = new OSMExporterInMemory()
    when:
    new OSMLoader(new OSMProviderInMemory(OSM_XML), exporter).export()
    then:
    def ways = exporter.exportedData.ways

    ways[38407529L].id == 38407529
    ways.size() == 1
  }

  def "Should load node tag data"() {
    given:
    def exporter = new OSMExporterInMemory()
    when:
    new OSMLoader(new OSMProviderInMemory(OSM_XML), exporter).export()
    then:
    def tags = exporter.exportedData.nodes[358802885L].tags
    tags == ["gnis:created"   : "06/14/2000",
             "gnis:county_id" : "037",
             "name"           : "Santa Monica Mountains National Recreation Area",
             "leisure"        : "park",
             "gnis:feature_id": "277263",
             "gnis:state_id"  : "06",
             "ele"            : "243"]
  }

  def "Should load way tag data"() {
    given:
    def exporter = new OSMExporterInMemory()
    when:
    new OSMLoader(new OSMProviderInMemory(OSM_XML), exporter).export()
    then:
    def tags = exporter.exportedData.ways[38407529L].tags

    tags == ["park:type"   : "state_park",
             "csp:unitcode": "537",
             "admin_level" : "4",
             "name"        : "Malibu Creek State Park",
             "csp:globalid": "{4A422954-089E-407F-A5B3-1E808F830EAA}",
             "leisure"     : "park",
             "attribution" : "CASIL CSP_Opbdys072008",
             "note"        : "simplified with josm to reduce node #",
             "boundary"    : "national_park"]
  }

  def "Should load node ref elements"() {
    given:
    def exporter = new OSMExporterInMemory()
    when:
    new OSMLoader(new OSMProviderInMemory(OSM_XML), exporter).export()
    then:
    def refs = exporter.exportedData.ways[38407529L].nodeReferences
    refs == [453966480L, 453966490L, 453966482L, 453966130L, 453966143L, 453966480L]
  }

  def "Should load map bounds"() {
    given:
    def exporter = new OSMExporterInMemory()
    when:
    new OSMLoader(new OSMProviderInMemory(OSM_XML), exporter).export()
    then:
    exporter.exportedData.bounds ==
        new Bounds(34.0662408634219, 34.0731374116421, -118.736715316772, -118.73122215271)
  }

  def "Should load map metadata"() {
    given:
    def exporter = new OSMExporterInMemory()
    when:
    new OSMLoader(new OSMProviderInMemory(OSM_XML), exporter).export()
    then:
    exporter.exportedData.metadata == new Metadata("0.6", "JOSM")
  }

  def "Should load relation data"() {
    given:
    def exporter = new OSMExporterInMemory()
    when:
    new OSMLoader(new OSMProviderInMemory(OSM_XML), exporter).export()
    then:
    def relation = exporter.exportedData.relations[56688L]
    relation.id == 56688
  }

  def "Should load relation tag data"() {
    given:
    def exporter = new OSMExporterInMemory()
    when:
    new OSMLoader(new OSMProviderInMemory(OSM_XML), exporter).export()
    then:
    def tags = exporter.exportedData.relations[56688L].tags

    tags == ["name"    : "Kstenbus Linie 123",
             "network" : "VVW",
             "operator": "Regionalverkehr Kste",
             "ref"     : "123",
             "route"   : "bus",
             "type"    : "route"]
  }

  def "Should load relation member data"() {
    given:
    def exporter = new OSMExporterInMemory()
    when:
    new OSMLoader(new OSMProviderInMemory(OSM_XML), exporter).export()
    then:
    def members = exporter.exportedData.relations[56688L].members

    members == [new Member("node", 358802885L, "inner"),
                new Member("node", 453966480L, "inner"),
                new Member("way", 38407529L, "inner"),
                new Member("node", 453966490L, "inner")]

  }

  def "Should summarize extracted document"() {
    given:
    def exporter = new OSMExporterInMemory()
    when:
    def summary = new OSMLoader(new OSMProviderInMemory(OSM_XML), exporter).export()
    then:
    verifyAll(summary) {
      totalParsed() == 10
      totalAccepted() == 10
      totalExported() == 10
    }
  }

  def "Should apply specification filters to elements"() {
    given:
    def exporter = new OSMExporterInMemory()
    when:
    def summary = new OSMLoader(new OSMProviderInMemory(OSM_XML), exporter,
        new TestSpecification()).export()
    then:
    def exportedNodes = exporter.exportedData.nodes
    def exportedWays = exporter.exportedData.ways
    def exportedRelations = exporter.exportedData.relations
    verifyAll(summary) {
      totalParsed() == 10
      totalAccepted() == 3
      totalExported() == 3
      exportedNodes.size() == 1
      exportedNodes.containsKey(358802885L)
      exportedWays.size() == 1
      exportedWays.containsKey(38407529L)
      exportedRelations.size() == 1
      exportedRelations.containsKey(56688L)
    }
  }

  class TestSpecification implements OSMLoaderSpecification {

    @Override
    boolean accept(Bounds bounds) {
      return false
    }

    @Override
    boolean accept(Metadata metadata) {
      return false
    }

    @Override
    boolean accept(Node node) {
      return node.id == 358802885L
    }

    @Override
    boolean accept(Relation relation) {
      return relation.id == 56688L
    }

    @Override
    boolean accept(Way way) {
      return way.id == 38407529L
    }
  }
}