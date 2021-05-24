package com.navigation.osmdataprocessor.domain.street

import com.navigation.osmdataprocessor.domain.ExportNotSupportedException
import com.navigation.parser.elements.Bounds
import com.navigation.parser.elements.Metadata
import com.navigation.parser.elements.Node
import com.navigation.parser.elements.Relation
import com.navigation.parser.elements.Way
import spock.lang.Specification

class StreetExporterTest extends Specification {

  def "accept(Node) should export address transformed from node"() {
    given:
    def processedExporter = Mock(ProcessedStreetExporter)
    def streetExporter = new StreetExporter(processedExporter)
    when:
    streetExporter.accept(new Node(1, [:], 2, 3))
    then:
    1 * processedExporter.exportProcessedStreetNode("1", new StreetNode(1, 2, 3))
  }

  def "accept(Way) should export one way connections"() {
    given:
    def processedExporter = Mock(ProcessedStreetExporter)
    def streetExporter = new StreetExporter(processedExporter)
    when:
    streetExporter.accept(new Way(11, [oneway: "yes", maxspeed: "60"], [1L, 2L]))
    then:
    1 * processedExporter.exportProcessedStreetConnection("1#2", new StreetConnection(1, 2, 60))
  }

  def "accept(Way) should provide default max speed value when max speed has illegal value"() {
    given:
    def processedExporter = Mock(ProcessedStreetExporter)
    def streetExporter = new StreetExporter(processedExporter)
    when:
    streetExporter.accept(new Way(11, [oneway: "yes", maxspeed: "dd"], [1L, 2L]))
    then:
    1 * processedExporter.exportProcessedStreetConnection("1#2", new StreetConnection(1, 2, 50))
  }

  def "accept(Way) should provide default max speed value is not present"() {
    given:
    def processedExporter = Mock(ProcessedStreetExporter)
    def streetExporter = new StreetExporter(processedExporter)
    when:
    streetExporter.accept(new Way(11, [oneway: "yes"], [1L, 2L]))
    then:
    1 * processedExporter.exportProcessedStreetConnection("1#2", new StreetConnection(1, 2, 50))
  }

  def "accept(Way) should export two way connections"() {
    given:
    def processedExporter = Mock(ProcessedStreetExporter)
    def streetExporter = new StreetExporter(processedExporter)
    when:
    streetExporter.accept(new Way(11, [:], [1L, 2L, 3L]))
    then:
    with(processedExporter) {
      1 * exportProcessedStreetConnection("1#2", new StreetConnection(1, 2, 50))
      1 * exportProcessedStreetConnection("2#3", new StreetConnection(2, 3, 50))
      1 * exportProcessedStreetConnection("3#2", new StreetConnection(3, 2, 50))
      1 * exportProcessedStreetConnection("2#1", new StreetConnection(2, 1, 50))
      0 * _
    }
  }

  def "accept(Bounds) should throw ExportNotSupportedException"() {
    given:
    def streetExporter = new StreetExporter(null)
    when:
    streetExporter.accept(new Bounds(1, 1, 1, 1))
    then:
    thrown ExportNotSupportedException
  }

  def "accept(Metadata) should throw ExportNotSupportedException"() {
    given:
    def streetExporter = new StreetExporter(null)
    when:
    streetExporter.accept(new Metadata("", ""))
    then:
    thrown ExportNotSupportedException
  }

  def "accept(Relation) should throw ExportNotSupportedException"() {
    given:
    def streetExporter = new StreetExporter(null)
    when:
    streetExporter.accept(new Relation(1, [:], []))
    then:
    thrown ExportNotSupportedException
  }
}