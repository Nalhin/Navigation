package com.navigation.pathfinder.weight

import com.navigation.pathfinder.graph.Coordinates
import com.navigation.pathfinder.graph.Edge
import com.navigation.pathfinder.graph.Vertex
import spock.lang.Specification

class MinimumNodesEdgeWeightCalculatorTest extends Specification {
  def "calculateWeight() should always return 1"() {
    given:
    def calculator = new MinimumNodesEdgeWeightCalculator()
    def edge = new Edge(new Vertex(1, new Coordinates(1, 1)), new Vertex(2, new Coordinates(2, 2)),50)
    when:
    def distance = calculator.calculateWeight(edge)
    then:
    distance == 1
  }
}
