package com.navigation.parser.types

import com.navigation.parser.exceptions.InvalidOsmTagException
import spock.lang.Specification

class ElementTypesTest extends Specification {

  def "fromTag() should convert tag string representation (#tagString) to element (#expectedResult)"(
      String tagString, ElementTypes expectedResult) {
    expect:
    ElementTypes.fromTag(tagString) == expectedResult
    where:
    tagString  || expectedResult
    "way"      || ElementTypes.WAY
    "node"     || ElementTypes.NODE
    "bounds"   || ElementTypes.BOUNDS
    "osm"      || ElementTypes.METADATA
    "relation" || ElementTypes.RELATION
  }


  def "fromTag() should throw InvalidOsmTagException when tag string representation does not match any element"() {
    given:
    def invalidTagRepresentation = "invalid"
    when:
    ElementTypes.fromTag(invalidTagRepresentation)
    then:
    thrown InvalidOsmTagException
  }

  def "isElement() returns whether tag string representation matches element"(
      ElementTypes element, String tagString, boolean expectedResult) {
    expect:
    element.isElement(tagString) == expectedResult
    where:
    element               | tagString  || expectedResult
    ElementTypes.WAY      | "way"      || true
    ElementTypes.NODE     | "node"     || true
    ElementTypes.BOUNDS   | "bounds"   || true
    ElementTypes.METADATA | "osm"      || true
    ElementTypes.RELATION | "relation" || true
    ElementTypes.NODE     | "way"      || false
    ElementTypes.NODE     | "fake"     || false
  }
}
