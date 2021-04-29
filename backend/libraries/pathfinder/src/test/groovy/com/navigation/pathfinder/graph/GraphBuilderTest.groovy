package com.navigation.pathfinder.graph

import spock.lang.Specification

class GraphBuilderTest extends Specification {
  def "addNode() should add node to graph"() {
    given:
    def graphBuilder = new GraphBuilder()
    def insertedNode = new Vertex(1, new Coordinates(22.22, 33.33))
    when:
    graphBuilder.addVertex(insertedNode)
    then:
    graphBuilder.asGraph().getVertexEdges(insertedNode) == []
  }

  def "connect() should connect two nodes in graph"() {
    given:
    def graphBuilder = new GraphBuilder()
    def from = new Vertex(1, new Coordinates(22.22, 33.33))
    def to = new Vertex(2, new Coordinates(33.22, 44.33))
    graphBuilder.addVertex(from)
    graphBuilder.addVertex(to)
    when:
    graphBuilder.connect(from, to, 50)
    then:
    def edges = graphBuilder.asGraph().getVertexEdges(from)
    edges.size() == 1
    edges[0].to == to
  }
}
