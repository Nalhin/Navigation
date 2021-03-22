package com.navigation.pathfinder.graph

import spock.lang.Specification

class GraphBuilderTest extends Specification {
  def "addNode() should add node to graph"() {
    given:
    def graphBuilder = new GraphBuilder()
    def insertedNode = new GraphNode(1, new Coordinates(22.22, 33.33))
    when:
    graphBuilder.addNode(insertedNode)
    then:
    graphBuilder.asGraph().getNodeEdges(insertedNode) == []
  }

  def "connect() should connect two nodes in graph"() {
    given:
    def graphBuilder = new GraphBuilder()
    def from = new GraphNode(1, new Coordinates(22.22, 33.33))
    def to = new GraphNode(2, new Coordinates(33.22, 44.33))
    graphBuilder.addNode(from)
    graphBuilder.addNode(to)
    when:
    graphBuilder.connect(from, to)
    then:
    def edges = graphBuilder.asGraph().getNodeEdges(from)
    edges.size() == 1
    edges[0].to == to
  }
}
