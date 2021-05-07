package com.navigation.pathfinder.graph

import com.navigation.pathfinder.exceptions.VertexNotPresentException
import spock.lang.Specification

import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.that

class GraphBuilderTest extends Specification {

  def "addVertex(Vertex) should add an vertex to graph"() {
    given:
    def graphBuilder = new GraphBuilder()
    def insertedVertex = new Vertex(1, new Coordinates(22.22, 33.33))
    when:
    graphBuilder.addVertex(insertedVertex)
    then:
    that graphBuilder.asGraph().vertices(), containsInAnyOrder(insertedVertex)
  }

  def "connect() should connect two vertices in graph"() {
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
    verifyAll(edges) {
      size() == 1
      it.collect { e -> e.to } == [to]
    }
  }

  def "connect() should throw an exception when from vertex is not present in graph"() {
    given:
    def graphBuilder = new GraphBuilder()
    def from = new Vertex(1, new Coordinates(22.22, 33.33))
    def to = new Vertex(2, new Coordinates(33.22, 44.33))
    graphBuilder.addVertex(to)
    when:
    graphBuilder.connect(from, to, 50)
    then:
    thrown VertexNotPresentException
  }

  def "connect() should throw an exception when to vertex is not present in graph"() {
    given:
    def graphBuilder = new GraphBuilder()
    def from = new Vertex(1, new Coordinates(22.22, 33.33))
    def to = new Vertex(2, new Coordinates(33.22, 44.33))
    graphBuilder.addVertex(from)
    when:
    graphBuilder.connect(from, to, 50)
    then:
    thrown VertexNotPresentException
  }


  def "connectByIds() should connect two vertices in graph"() {
    given:
    def graphBuilder = new GraphBuilder()
    def from = new Vertex(1, new Coordinates(22.22, 33.33))
    def to = new Vertex(2, new Coordinates(33.22, 44.33))
    graphBuilder.addVertex(from)
    graphBuilder.addVertex(to)
    when:
    graphBuilder.connectByIds(from.id, to.id, 50)
    then:
    def edges = graphBuilder.asGraph().getVertexEdges(from)
    verifyAll(edges) {
      size() == 1
      it.collect { e -> e.to } == [to]
    }
  }

  def "connectByIds() should throw an exception when from vertex is not present"() {
    given:
    def graphBuilder = new GraphBuilder()
    def from = new Vertex(1, new Coordinates(22.22, 33.33))
    def to = new Vertex(2, new Coordinates(33.22, 44.33))
    graphBuilder.addVertex(to)
    when:
    graphBuilder.connectByIds(from.id, to.id, 50)
    then:
    thrown VertexNotPresentException
  }


  def "connectByIds() should throw an exception when to vertex is not present"() {
    given:
    def graphBuilder = new GraphBuilder()
    def from = new Vertex(1, new Coordinates(22.22, 33.33))
    def to = new Vertex(2, new Coordinates(33.22, 44.33))
    graphBuilder.addVertex(from)
    when:
    graphBuilder.connectByIds(from.id, to.id, 50)
    then:
    thrown VertexNotPresentException
  }
}
