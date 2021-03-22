package com.navigation.pathfinder.weight

import com.navigation.pathfinder.graph.Coordinates
import com.navigation.pathfinder.graph.GraphEdge
import com.navigation.pathfinder.graph.GraphNode
import spock.lang.Specification

class MinimumNodesEdgeWeightCalculatorTest extends Specification {
  def "calculateWeight() should always return 1"() {
    given:
    def calculator = new MinimumNodesEdgeWeightCalculator()
    def edge = new GraphEdge(new GraphNode(1, new Coordinates(1, 1)), new GraphNode(2, new Coordinates(2, 2)))
    when:
    def distance = calculator.calculateWeight(edge)
    then:
    distance == 1
  }
}
