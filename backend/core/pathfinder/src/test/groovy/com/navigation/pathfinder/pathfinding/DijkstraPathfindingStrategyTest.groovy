package com.navigation.pathfinder.pathfinding

import com.navigation.pathfinder.graph.Coordinates
import com.navigation.pathfinder.graph.Graph
import com.navigation.pathfinder.graph.GraphBuilder
import com.navigation.pathfinder.graph.Vertex
import spock.lang.Specification

class DijkstraPathfindingStrategyTest extends Specification {


  private static TestGraphResult generateTestGraph() {
    /*
     Test graph structure

          H(1,4) ---- I(3,4)  --- M(3,6)
            |      /     |        |
         G(1,3)  /      |         |
           |   /      J(2,4) --- K(2,6)
         B(1,1)      /            |
         |         /              |
     A(0,0) --- D (0,2)           |
         \    /                   |
       C(-1,1) ---- E(-1,2) --- F(-1,6)

      legend:
      * start - A
      * end - M
    */
    def A = new Vertex(1, new Coordinates(0, 0))
    def B = new Vertex(2, new Coordinates(1, 1))
    def C = new Vertex(3, new Coordinates(1, 3))
    def D = new Vertex(4, new Coordinates(0, 2))
    def E = new Vertex(5, new Coordinates(-1, 2))
    def F = new Vertex(6, new Coordinates(-1, 6))
    def G = new Vertex(7, new Coordinates(1, 3))
    def H = new Vertex(8, new Coordinates(1, 4))
    def I = new Vertex(9, new Coordinates(3, 4))
    def J = new Vertex(10, new Coordinates(2, 4))
    def K = new Vertex(11, new Coordinates(2, 6))
    def M = new Vertex(12, new Coordinates(3, 6))

    def gb = new GraphBuilder()
    gb.addNode(A)
        .addNode(B)
        .addNode(C)
        .addNode(D)
        .addNode(E)
        .addNode(F)
        .addNode(G)
        .addNode(H)
        .addNode(I)
        .addNode(J)
        .addNode(K)
        .addNode(M)
        .connect(A, D)
        .connect(A, C)
        .connect(A, B)
        .connect(B, G)
        .connect(B, I)
        .connect(G, H)
        .connect(H, I)
        .connect(I, M)
        .connect(D, J)
        .connect(C, D)
        .connect(C, E)
        .connect(C, F)
        .connect(F, K)
        .connect(K, M)
        .connect(J, K)
        .connect(J, I)

    return new TestGraphResult(gb.asGraph(), A, M, [A, B, I, M])
  }


  def "findShortestPath() should return the shortest path"() {
    given:
    def testGraph = generateTestGraph()
    def strategy = new DijkstraPathfindingStrategy()
    when:
    def path = strategy.findShortestPath(testGraph.start, testGraph.end, testGraph.graph)
    then:
    path.vertices == testGraph.shortestPath
  }


  static class TestGraphResult {
    final Graph graph
    final Vertex start
    final Vertex end
    final List<Vertex> shortestPath

    TestGraphResult(Graph graph, Vertex start, Vertex end, List<Vertex> shortestPath) {
      this.graph = graph
      this.start = start
      this.end = end
      this.shortestPath = shortestPath
    }
  }
}
