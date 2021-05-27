package com.navigation.osmdataprocessor.street.application

import com.navigation.osmdataprocessor.shared.exceptions.ExportNotSupportedException
import com.navigation.osmdataprocessor.street.domain.StreetConnection
import com.navigation.osmdataprocessor.street.domain.StreetConnectionExtractor
import com.navigation.osmdataprocessor.street.domain.StreetNode
import com.navigation.osmdataprocessor.street.domain.StreetNodeExtractor
import com.navigation.parser.elements.Bounds
import com.navigation.parser.elements.Metadata
import com.navigation.parser.elements.Node
import com.navigation.parser.elements.Relation
import com.navigation.parser.elements.Way
import spock.lang.Specification

class StreetExporterTest extends Specification {

  def "accept(Node) should export address transformed from node"() {
    given:
    def processedPublisher = Mock(ProcessedStreetPublisher)
    def streetExporter = new StreetExporter(
        processedPublisher,
        new StreetConnectionExtractor(),
        new StreetNodeExtractor()
    )
    when:
    streetExporter.accept(new Node(1, [:], 2, 3))
    then:
    1 * processedPublisher.publishProcessedStreetNode("1", new StreetNode(1, 2, 3))
  }

  def "accept(Way) should export one way street connection"() {
    given:
    def processedPublisher = Mock(ProcessedStreetPublisher)
    def streetExporter = new StreetExporter(
        processedPublisher,
        new StreetConnectionExtractor(),
        new StreetNodeExtractor()
    )
    when:
    streetExporter.accept(new Way(11, [oneway: "yes", maxspeed: "60"], [1L, 2L]))
    then:
    1 * processedPublisher.
        publishProcessedStreetConnection("1#2", new StreetConnection("1#2", 1, 2, 60))
  }


  def "accept(Bounds) should throw ExportNotSupportedException"() {
    given:
    def streetExporter = new StreetExporter(
        Stub(ProcessedStreetPublisher),
        new StreetConnectionExtractor(),
        new StreetNodeExtractor()
    )
    when:
    streetExporter.accept(new Bounds(1, 1, 1, 1))
    then:
    thrown ExportNotSupportedException
  }

  def "accept(Metadata) should throw ExportNotSupportedException"() {
    given:
    def streetExporter = new StreetExporter(
        Stub(ProcessedStreetPublisher),
        new StreetConnectionExtractor(),
        new StreetNodeExtractor()
    )
    when:
    streetExporter.accept(new Metadata("", ""))
    then:
    thrown ExportNotSupportedException
  }

  def "accept(Relation) should throw ExportNotSupportedException"() {
    given:
    def streetExporter = new StreetExporter(
        Stub(ProcessedStreetPublisher),
        new StreetConnectionExtractor(),
        new StreetNodeExtractor()
    )
    when:
    streetExporter.accept(new Relation(1, [:], []))
    then:
    thrown ExportNotSupportedException
  }
}
