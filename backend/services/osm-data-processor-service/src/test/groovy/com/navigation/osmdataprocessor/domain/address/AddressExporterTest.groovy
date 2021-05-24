package com.navigation.osmdataprocessor.domain.address

import com.navigation.osmdataprocessor.domain.ExportNotSupportedException
import com.navigation.parser.elements.Bounds
import com.navigation.parser.elements.Metadata
import com.navigation.parser.elements.Node
import com.navigation.parser.elements.Relation
import com.navigation.parser.elements.Way
import spock.lang.Specification

class AddressExporterTest extends Specification {

  def "accept(Node) should export address transformed from node"() {
    given:
    def processedExporter = Mock(ProcessedAddressExporter)
    def addressExporter = new AddressExporter(processedExporter)
    when:
    addressExporter.accept(new Node(1, [:], 2, 3))
    then:
    1 * processedExporter.exportProcessedAddress("1", _ as Address)
  }


  def "accept(Way) should throw ExportNotSupportedException"() {
    given:
    def addressExporter = new AddressExporter(null)
    when:
    addressExporter.accept(new Way(11, [:], []))
    then:
    thrown ExportNotSupportedException
  }


  def "accept(Bounds) should throw ExportNotSupportedException"() {
    given:
    def addressExporter = new AddressExporter(null)
    when:
    addressExporter.accept(new Bounds(1, 1, 1, 1))
    then:
    thrown ExportNotSupportedException
  }

  def "accept(Metadata) should throw ExportNotSupportedException"() {
    given:
    def addressExporter = new AddressExporter(null)
    when:
    addressExporter.accept(new Metadata("", ""))
    then:
    thrown ExportNotSupportedException
  }

  def "accept(Relation) should throw ExportNotSupportedException"() {
    given:
    def addressExporter = new AddressExporter(null)
    when:
    addressExporter.accept(new Relation(1, [:], []))
    then:
    thrown ExportNotSupportedException
  }
}
