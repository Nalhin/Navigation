package com.navigation.pathfinder.weight

import com.navigation.pathfinder.graph.Coordinates
import com.navigation.pathfinder.graph.Edge
import com.navigation.pathfinder.graph.Vertex
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class UnweightedDistanceCalculatorTest extends Specification {

  @Subject
  @Shared
  def edgeWeightCalculator = new UnweightedDistanceCalculator()

  def "calculateWeight() should return 0"() {
    given:
    def edge = new Edge(
        new Vertex(1, new Coordinates(1, 1)),
        new Vertex(2, new Coordinates(2, 2)),
        50)
    when:
    def distance = edgeWeightCalculator.calculateWeight(edge)
    then:
    distance == 0
  }

  def "estimateWeight() should return 0"() {
    given:
    def from = new Vertex(1, new Coordinates(1, 1))
    def to = new Vertex(2, new Coordinates(2, 2))
    when:
    def distance = edgeWeightCalculator.estimateWeight(from, to)
    then:
    distance == 0
  }
}
