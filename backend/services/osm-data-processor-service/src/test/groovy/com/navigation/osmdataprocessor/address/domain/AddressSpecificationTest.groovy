package com.navigation.osmdataprocessor.address.domain

import com.navigation.parser.elements.Bounds
import com.navigation.parser.elements.Metadata
import com.navigation.parser.elements.Node
import com.navigation.parser.elements.Relation
import com.navigation.parser.elements.Way
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class AddressSpecificationTest extends Specification {

  @Subject
  @Shared
  def addressSpecification = new AddressSpecification()

  def "accept(Node) should return whether #providedNode has the required tags associated with address"(
      Node providedNode, boolean expectedResult) {
    when:
    def actualResult = addressSpecification.accept(providedNode)
    then:
    actualResult == expectedResult
    where:
    providedNode                                                           || expectedResult
    new Node(1, ["addr:housenumber": "10", "addr:street": "street"], 1, 1) || true
    new Node(1, ["addr:street": "street"], 1, 1)                           || false
    new Node(1, ["addr:housenumber": "10",], 1, 1)                         || false
  }

  def "accept(Way) should always return false"() {
    expect:
    !addressSpecification.accept(new Way(11, [:], []))
  }

  def "accept(Bounds) should always return false"() {
    expect:
    !addressSpecification.accept(new Bounds(1, 1, 1, 1))
  }

  def "accept(Metadata) should always return false"() {
    expect:
    !addressSpecification.accept(new Metadata("", ""))
  }

  def "accept(Relation) should always return false"() {
    expect:
    !addressSpecification.accept(new Relation(1, [:], []))
  }
}
