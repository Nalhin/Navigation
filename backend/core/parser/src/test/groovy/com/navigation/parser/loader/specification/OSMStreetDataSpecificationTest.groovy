package com.navigation.parser.loader.specification

import com.navigation.parser.elements.Way
import com.navigation.parser.elements.Node
import spock.lang.Specification

class OSMStreetDataSpecificationTest extends Specification {
  def "isSatisfiedBy(Way) should return #expectedResult when called with #way"(Way way, boolean expectedResult) {
    given:
    def specification = new OSMStreetDataSpecification()
    expect:
    specification.isSatisfiedBy(way) == expectedResult
    where:
    way                                  || expectedResult
    new Way(1, [invalid: "primary"], []) || false
    new Way(1, [highway: "invalid"], []) || false
    new Way(1, [highway: "primary"], []) || true
  }

  def "isSatisfiedBy(Way) should add node to allowList when when way satisfied the condition"(Way way, Node node, boolean expectedResult) {
    given:
    def specification = new OSMStreetDataSpecification()
    when:
    specification.isSatisfiedBy(way)
    then:
    specification.isSatisfiedBy(node) == expectedResult
    where:
    way                                    | node                   || expectedResult
    new Way(2, [invalid: "primary"], [1L]) | new Node(1, [:], 4, 4) || false
    new Way(2, [highway: "invalid"], [1L]) | new Node(1, [:], 4, 4) || false
    new Way(2, [highway: "primary"], [1L]) | new Node(1, [:], 4, 4) || true
  }

  def "isSatisfiedBy(Node) should return false by default"() {
    given:
    def specification = new OSMStreetDataSpecification()
    expect:
    !specification.isSatisfiedBy(new Node(2, [:], 3, 4))
  }

  def "isSatisfiedBy(Node) should return true after node was added to allowList"() {
    given:
    def specification = new OSMStreetDataSpecification()
    when:
    specification.isSatisfiedBy(new Way(1, [highway: "primary"], [2L, 3L]))
    then:
    specification.isSatisfiedBy(new Node(2, [:], 3, 4))
  }
}
