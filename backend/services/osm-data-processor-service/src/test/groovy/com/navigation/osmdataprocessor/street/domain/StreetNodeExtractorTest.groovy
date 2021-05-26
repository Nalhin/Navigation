package com.navigation.osmdataprocessor.street.domain

import com.navigation.parser.elements.Node
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class StreetNodeExtractorTest extends Specification {

  @Subject
  @Shared
  def extractor = new StreetNodeExtractor()

  def "extractFromNode() should extract street node from osm node"() {
    given:
    def node = new Node(1, [:], 2, 3)
    when:
    def actualResult = extractor.extractFromNode(node)
    then:
    actualResult == new StreetNode(1, 2, 3)
  }
}
