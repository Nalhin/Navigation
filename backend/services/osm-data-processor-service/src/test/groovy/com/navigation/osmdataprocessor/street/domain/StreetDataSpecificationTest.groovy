package com.navigation.osmdataprocessor.street.domain

import com.navigation.parser.elements.Node
import com.navigation.parser.elements.Bounds
import com.navigation.parser.elements.Metadata
import com.navigation.parser.elements.Relation
import com.navigation.parser.elements.Way
import spock.lang.Specification

class StreetDataSpecificationTest extends Specification {

  def "accept(Way) should return whether the way has the required highway tags"(
      Map<String, String> tags, boolean expectedResult) {
    given:
    def specification = new StreetDataSpecification()
    when:
    def actualResult = specification.accept(new Way(1, tags, [1L]))
    then:
    actualResult == expectedResult
    where:
    tags                || expectedResult
    [highway: "road"]   || true
    [random: "road"]    || false
    [highway: "random"] || false
  }


  def "accept(Node) should return true when node is part of way"() {
    given:
    def specification = new StreetDataSpecification()
    when:
    specification.accept(new Way(1, [highway: "road"], [2L]))
    then:
    specification.accept(new Node(2, [:], 2, 3))
  }


  def "accept(Node) should return false when node is not part of a way"() {
    given:
    def specification = new StreetDataSpecification()
    when:
    specification.accept(new Way(1, [highway: "road"], [3L]))
    then:
    !specification.accept(new Node(2, [:], 2, 3))
  }

  def "accept(Bounds) should always return false"() {
    given:
    def specification = new StreetDataSpecification()
    expect:
    !specification.accept(new Bounds(1, 1, 1, 1))
  }

  def "accept(Metadata) should always return false"() {
    given:
    def specification = new StreetDataSpecification()
    expect:
    !specification.accept(new Metadata("", ""))
  }

  def "accept(Relation) should always return false"() {
    given:
    def specification = new StreetDataSpecification()
    expect:
    !specification.accept(new Relation(1, [:], []))
  }
}
