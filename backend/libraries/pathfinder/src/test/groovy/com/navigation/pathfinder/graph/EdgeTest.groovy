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
    verifyAll(actualResult) {
      to == edge.from
      from == edge.to
      maxSpeed == edge.maxSpeed
    }
  }

  def "equals() should compare by from and to vertices and speed"() {
    given:
    def firstVertex =
        new Vertex(1, new Coordinates(1, 1))
    def secondVertex = new Vertex(2, new Coordinates(1, 1))
    def firstEdge = new Edge(firstVertex, secondVertex, 50)
    def secondEdge = new Edge(firstVertex, secondVertex, 50)
    expect:
    firstEdge == secondEdge
  }

  def "hashCode() should compare by from and to vertices and speed"() {
    given:
    def firstVertex =
        new Vertex(1, new Coordinates(1, 1))
    def secondVertex = new Vertex(2, new Coordinates(1, 1))
    def firstEdge = new Edge(firstVertex, secondVertex, 50)
    def secondEdge = new Edge(firstVertex, secondVertex, 50)
    expect:
    firstEdge.hashCode() == secondEdge.hashCode()
  }
}
