package com.navigation.pathfinder.graph

import spock.lang.Specification

class VertexTest extends Specification {

  def "equals() should use ids for comparison"() {
    given:
    def first = new Vertex(1, new Coordinates(1, 1))
    def second = new Vertex(1, new Coordinates(2, 2))
    expect:
    first == second
  }

  def "hashCode() should use ids for hash generation"() {
    given:
    def first = new Vertex(1, new Coordinates(1, 1))
    def second = new Vertex(1, new Coordinates(2, 2))
    expect:
    first.hashCode() == second.hashCode()
  }
}
