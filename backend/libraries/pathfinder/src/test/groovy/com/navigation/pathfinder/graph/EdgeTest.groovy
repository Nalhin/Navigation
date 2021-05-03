package com.navigation.pathfinder.graph

import spock.lang.Specification

class EdgeTest extends Specification {

  def "reversed() should reverse an edge"() {
    given:
    def edge = new Edge(
        new Vertex(1, new Coordinates(1, 1)),
        new Vertex(2, new Coordinates(1, 1)), 50)
    when:
    def actualResult = edge.reversed()
    then:
    actualResult.to == edge.from
    actualResult.from == edge.to
    actualResult.maxSpeed == edge.maxSpeed
  }
}
