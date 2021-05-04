package com.navigation.pathfinder.weight

import com.navigation.pathfinder.graph.Coordinates
import com.navigation.pathfinder.graph.Edge
import com.navigation.pathfinder.graph.Vertex
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class NodeCountEdgeWeightCalculatorTest extends Specification {

  @Subject
  @Shared
  def edgeWeightCalculator = new NodeCountEdgeWeightCalculator()

  def "calculateWeight() should return edge node count"() {
    given:
    def edge = new Edge(
        new Vertex(1, new Coordinates(1, 1)),
        new Vertex(2, new Coordinates(2, 2)),
        50)
    when:
    def distance = edgeWeightCalculator.calculateWeight(edge)
    then:
    distance == 1
  }
}
