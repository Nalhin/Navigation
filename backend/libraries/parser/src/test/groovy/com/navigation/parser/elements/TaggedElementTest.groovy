package com.navigation.parser.elements

import spock.lang.Specification

class TaggedElementTest extends Specification {

  def "containsTag() should return whether the element contains the given tag"(Map<String, String> elementTags, String targetTag, boolean expectedResult) {
    given:
    def taggedElement = new TaggedElement(1L, elementTags) {}
    expect:
    taggedElement.containsTag(targetTag) == expectedResult
    where:
    elementTags        | targetTag || expectedResult
    [:]                | "example" || false
    ["example": "any"] | "example" || true
    ["fake": "any"]    | "example" || false
  }

  def "containsAnyTagIn() should return whether the element contains any tag in the given collection"(Map<String, String> elementTags, List<String> targetTags, boolean expectedResult) {
    given:
    def taggedElement = new TaggedElement(1L, elementTags) {}
    expect:
    taggedElement.containsAnyTagIn(targetTags) == expectedResult
    where:
    elementTags        | targetTags              || expectedResult
    [:]                | ["example"]             || false
    ["example": "any"] | ["example", "example1"] || true
    ["fake": "any"]    | ["example", "any"]      || false
  }

  def "containsAllTagsIn() should return whether the element contains every tag in the given collection"(Map<String, String> elementTags, List<String> targetTags, boolean expectedResult) {
    given:
    def taggedElement = new TaggedElement(1L, elementTags) {}
    expect:
    taggedElement.containsAllTagsIn(targetTags) == expectedResult
    where:
    elementTags                  | targetTags         || expectedResult
    [:]                          | ["example"]        || false
    ["one": "any", "two": "any"] | ["one", "two"]     || true
    ["one": "any"]               | ["example", "one"] || false

  }

  def "containsTagWithValue() should return whether the element contains tag with given value"(Map<String, String> elementTags, String targetTag, String expectedValue, boolean expectedResult) {
    given:
    def taggedElement = new TaggedElement(1L, elementTags) {}
    expect:
    taggedElement.containsTagWithValue(targetTag, expectedValue) == expectedResult
    where:
    elementTags                    | targetTag | expectedValue || expectedResult
    [:]                            | "example" | "value"       || false
    ["one": "value", "two": "any"] | "one"     | "value"       || true
    ["one": "any"]                 | "one"     | "value"       || false

  }

  def "containsTagWithAnyValueIn() should return whether the element contains tag with any value in given collection"(Map<String, String> elementTags, String targetTag, List<String> anyOfValue, boolean expectedResult) {
    given:
    def taggedElement = new TaggedElement(1L, elementTags) {}
    expect:
    taggedElement.containsTagWithAnyValueIn(targetTag, anyOfValue) == expectedResult
    where:
    elementTags                    | targetTag | anyOfValue       || expectedResult
    [:]                            | "example" | ["value"]        || false
    ["one": "value", "two": "any"] | "one"     | ["any", "value"] || true
    ["one": "any"]                 | "one"     | ["value"]        || false
  }
}
