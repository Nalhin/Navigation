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

public class BidirectionalAStarPathfinder implements PathfindingStrategy {

  private static final PathSummaryCreator pathSummaryCreator = new PathSummaryCreator();
  private static final BidirectionalCenterVertexFinder centerVertexFinder =
      new BidirectionalCenterVertexFinder();

  private final EdgeWeightCalculator calculator;

  public BidirectionalAStarPathfinder(EdgeWeightCalculator calculator) {
    this.calculator = calculator;
  }

  @Override
  public PathSummary findPath(Vertex start, Vertex end, Graph graph) {
    var gScoresForward = new HashMap<Vertex, Double>();
    var gScoresBackward = new HashMap<Vertex, Double>();
    gScoresForward.put(start, 0.0);
    gScoresBackward.put(end, 0.0);

    var predecessorTreeBackward = new HashMap<Vertex, Edge>();
    var predecessorTreeForward = new HashMap<Vertex, Edge>();
    predecessorTreeForward.put(start, null);
    predecessorTreeBackward.put(end, null);

    var openForward = new PriorityQueue<ScoredGraphVertex>();
    var openBackward = new PriorityQueue<ScoredGraphVertex>();
    openForward.add(new ScoredGraphVertex(start, heuristic(start, end)));
    openBackward.add(new ScoredGraphVertex(end, heuristic(end, start)));

    var reversedGraph = graph.reversed();

    while (!openForward.isEmpty() && !openBackward.isEmpty()) {
      var currForward = openForward.poll();
      if (predecessorTreeBackward.containsKey(currForward.vertex())) {
        var center =
            centerVertexFinder.findCenterVertex(
                currForward.vertex(),
                gScoresForward.get(currForward.vertex())
                    + gScoresBackward.get(currForward.vertex()),
                openForward,
                openBackward);
        return pathSummaryCreator.createBidirectionalPath(
            start, center, end, predecessorTreeForward, predecessorTreeBackward);
      }
      visitVertex(
          currForward.vertex(), graph, openForward, predecessorTreeForward, gScoresForward, end);

      var currBackward = openBackward.poll();
      if (predecessorTreeForward.containsKey(currBackward.vertex())) {
        var center =
            centerVertexFinder.findCenterVertex(
                currBackward.vertex(),
                gScoresForward.get(currBackward.vertex())
                    + gScoresBackward.get(currBackward.vertex()),
                openForward,
                openBackward);

        return pathSummaryCreator.createBidirectionalPath(
            start, center, end, predecessorTreeForward, predecessorTreeBackward);
      }
      visitVertex(
          currBackward.vertex(),
          reversedGraph,
          openBackward,
          predecessorTreeBackward,
          gScoresBackward,
          start);
    }

    return pathSummaryCreator.createBidirectionalPath(
        start, start, end, predecessorTreeForward, predecessorTreeBackward);
  }

  private void visitVertex(
      Vertex currVertex,
      Graph graph,
      Queue<ScoredGraphVertex> open,
      Map<Vertex, Edge> predecessorTree,
      Map<Vertex, Double> gScores,
      Vertex end) {
    for (var edge : graph.getVertexEdges(currVertex)) {
      var neighbour = edge.getTo();
      var newScore = gScores.get(currVertex) + calculator.calculateWeight(edge);

      if (newScore < gScores.getOrDefault(neighbour, Double.MAX_VALUE)) {
        gScores.put(neighbour, newScore);
        predecessorTree.put(neighbour, edge);
        open.add(new ScoredGraphVertex(neighbour, newScore + heuristic(neighbour, end)));
      }
    }
  }

  private double heuristic(Vertex from, Vertex to) {
    return calculator.estimateWeight(from, to);
  }
}
