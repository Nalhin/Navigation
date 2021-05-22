package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.path.PathSummaryCreator;
import com.navigation.pathfinder.weight.EdgeWeightCalculator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class BidirectionalGreedyBestFirstSearchPathfindingStrategy implements PathfindingStrategy {
  private static final PathSummaryCreator pathSummaryCreator = new PathSummaryCreator();
  private static final BidirectionalCenterVertexFinder centerVertexFinder =
      new BidirectionalCenterVertexFinder();

  private final EdgeWeightCalculator calculator;

  public BidirectionalGreedyBestFirstSearchPathfindingStrategy(EdgeWeightCalculator calculator) {
    this.calculator = calculator;
  }

  @Override
  public PathSummary findPath(Vertex start, Vertex end, Graph graph) {
    var predecessorTreeBackward = new HashMap<Vertex, Edge>();
    var predecessorTreeForward = new HashMap<Vertex, Edge>();
    predecessorTreeForward.put(start, null);
    predecessorTreeBackward.put(end, null);

    var pqForward = new PriorityQueue<ScoredGraphVertex>();
    var pqBackward = new PriorityQueue<ScoredGraphVertex>();
    pqForward.add(new ScoredGraphVertex(start, heuristic(start, end)));
    pqBackward.add(new ScoredGraphVertex(end, heuristic(end, start)));

    var reversedGraph = graph.reversed();

    while (!pqForward.isEmpty() && !pqBackward.isEmpty()) {
      var currForward = pqForward.poll();
      if (predecessorTreeBackward.containsKey(currForward.vertex())) {
        var center =
            centerVertexFinder.findCenterVertex(
                currForward.vertex(),
                heuristic(currForward.vertex(), start) + heuristic(currForward.vertex(), end),
                pqForward,
                pqBackward);
        return pathSummaryCreator.createBidirectionalPath(
            start, center, end, predecessorTreeForward, predecessorTreeBackward);
      }
      visitVertex(currForward.vertex(), graph, pqForward, predecessorTreeForward, end);

      var currBackward = pqBackward.poll();
      if (predecessorTreeForward.containsKey(currBackward.vertex())) {
        var center =
            centerVertexFinder.findCenterVertex(
                currBackward.vertex(),
                heuristic(currBackward.vertex(), start) + heuristic(currBackward.vertex(), end),
                pqForward,
                pqBackward);
        return pathSummaryCreator.createBidirectionalPath(
            start, center, end, predecessorTreeForward, predecessorTreeBackward);
      }
      visitVertex(currBackward.vertex(), reversedGraph, pqBackward, predecessorTreeBackward, start);
    }

    return pathSummaryCreator.createBidirectionalPath(
        start, start, end, predecessorTreeForward, predecessorTreeBackward);
  }

  private void visitVertex(
      Vertex currVertex,
      Graph graph,
      Queue<ScoredGraphVertex> pq,
      Map<Vertex, Edge> predecessorTree,
      Vertex end) {
    for (var edge : graph.getVertexEdges(currVertex)) {
      var neighbour = edge.getTo();

      if (!predecessorTree.containsKey(neighbour)) {
        predecessorTree.put(neighbour, edge);
        pq.add(new ScoredGraphVertex(neighbour, heuristic(neighbour, end)));
      }
    }
  }

  private double heuristic(Vertex from, Vertex to) {
    return calculator.estimateWeight(from, to);
  }
}
