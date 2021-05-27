package com.navigation.parser.types

import com.navigation.parser.exceptions.InvalidOsmTagException
import spock.lang.Specification

class NestedElementTypesTest extends Specification {

  def "fromTag() should convert tag string representation (#tagString) to nested element (#expectedResult)"(
      String tagString, NestedElementTypes expectedResult) {
    expect:
    NestedElementTypes.fromTag(tagString) == expectedResult
    where:
    tagString || expectedResult
    "nd"      || NestedElementTypes.REF
    "tag"     || NestedElementTypes.TAG
    "member"  || NestedElementTypes.MEMBER
  }


  def "fromTag() should throw InvalidOsmTagException when tag string representation does not match any nested element"() {
    given:
    def invalidTagRepresentation = "invalid"
    when:
    NestedElementTypes.fromTag(invalidTagRepresentation)
    then:
    thrown InvalidOsmTagException
  }
}
