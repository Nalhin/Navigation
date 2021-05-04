package com.navigation.pathfinder.graph

import spock.lang.Specification

import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.that

class GraphTest extends Specification {

  def "getVertexEdges() should return the edges of an vertex"() {
    given:
    def vertex = new Vertex(1, new Coordinates(1, 1))
    def firstNeighbour = new Vertex(2, new Coordinates(1, 1))
    def secondNeighbour = new Vertex(3, new Coordinates(1, 1))
    def graph = new GraphBuilder()
        .addVertex(vertex)
        .addVertex(firstNeighbour)
        .addVertex(secondNeighbour)
        .connectByIds(1, 2, 50)
        .connectByIds(1, 3, 50)
        .asGraph()
    when:
    def result = graph.getVertexEdges(vertex)
    then:
    result containsInAnyOrder(new Edge(vertex, firstNeighbour, 50),
        new Edge(vertex, secondNeighbour, 50))
  }


  def "Graph shouldn't reflect updates made to the builder after it's creation"() {
    given:
    def graphBuilder = new GraphBuilder()
    def insertedVertex = new Vertex(1, new Coordinates(22.22, 33.33))
    when:
    graphBuilder.addVertex(insertedVertex)
    def result = graphBuilder.asGraph()
    graphBuilder.addVertex(new Vertex(2, new Coordinates(22.22, 33.33)))
    then:
    that result.vertices(), containsInAnyOrder(insertedVertex)
  }

  def "reversed() should return reversed graph"() {
    given:
    def vertex = new Vertex(1, new Coordinates(1, 1))
    def firstNeighbour = new Vertex(2, new Coordinates(1, 1))
    def secondNeighbour = new Vertex(3, new Coordinates(1, 1))
    def graph = new GraphBuilder()
        .addVertex(vertex)
        .addVertex(firstNeighbour)
        .addVertex(secondNeighbour)
        .connectByIds(1, 2, 50)
        .connectByIds(1, 3, 50)
        .asGraph()
    when:
    def result = graph.reversed()
    then:
    that result.getVertexEdges(firstNeighbour),
        containsInAnyOrder(new Edge(firstNeighbour, vertex, 50))
    that result.getVertexEdges(secondNeighbour),
        containsInAnyOrder(new Edge(secondNeighbour, vertex, 50))
  }

  def "vertices() should return graph vertices"() {
    given:
    def first = new Vertex(1, new Coordinates(1, 1))
    def second = new Vertex(2, new Coordinates(1, 1))
    def third = new Vertex(3, new Coordinates(1, 1))
    def graph = new GraphBuilder()
        .addVertex(first)
        .addVertex(second)
        .addVertex(third)
        .asGraph()
    when:
    def result = graph.vertices()
    then:
    result containsInAnyOrder(first, second, third)
  }

  def "edges() should return graph edges"() {
    given:
    def vertex = new Vertex(1, new Coordinates(1, 1))
    def firstNeighbour = new Vertex(2, new Coordinates(1, 1))
    def secondNeighbour = new Vertex(3, new Coordinates(1, 1))
    def graph = new GraphBuilder()
        .addVertex(vertex)
        .addVertex(firstNeighbour)
        .addVertex(secondNeighbour)
        .connectByIds(1, 2, 50)
        .connectByIds(1, 3, 50)
        .asGraph()
    when:
    def result = graph.edges()
    then:
    result containsInAnyOrder(new Edge(vertex, firstNeighbour, 50),
        new Edge(vertex, secondNeighbour, 50))
  }
}
