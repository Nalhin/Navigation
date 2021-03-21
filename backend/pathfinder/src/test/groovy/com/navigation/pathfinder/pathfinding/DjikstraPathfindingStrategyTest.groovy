package com.navigation.pathfinder.pathfinding

import com.navigation.pathfinder.graph.Graph
import com.navigation.pathfinder.graph.GraphBuilder
import com.navigation.pathfinder.graph.GraphNode
import spock.lang.Specification

class DjikstraPathfindingStrategyTest extends Specification {

  static Graph buildTestGraph(GraphNode start, GraphNode end) {
    def builder = new GraphBuilder()

    def nodes = [new GraphNode(3333, 40.71494, -73.9636),
                 new GraphNode(4444, 40.74095, -73.8920),
                 new GraphNode(5555, 40.74803, -73.78496),
                 new GraphNode(6666, 40.74023, -73.7415),
                 new GraphNode(7777, 40.6646, -73.6676)]
    def connections = [
        [start, nodes[0]],
        [nodes[2], nodes[3]],
        [nodes[0], nodes[1]],
        [nodes[0], nodes[3]],
        [nodes[1], nodes[2]],
        [nodes[3], nodes[4]],
        [nodes[4], end]] as List<List<GraphNode>>

    builder.addNode(start)
    builder.addNode(end)
    nodes.forEach(builder::addNode)

    connections.forEach({ it -> builder.connect(it.get(0), it.get(1)) })

    return builder.asGraph()
  }


  def "findShortestPath() should return the shortest path"() {
    given:
    def start = new GraphNode(1, 40.6227, -73.9393)
    def end = new GraphNode(2, 40.6531, -73.7769)
    def graph = buildTestGraph(start, end)
    def strategy = new DjikstraPathfindingStrategy()
    when:
    def path = strategy.findShortestPath(start, end, graph)
    then:
    path.nodes[0] == start
    path.nodes[4] == end
  }
}
