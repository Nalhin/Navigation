package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.path.PathSummaryCreator;
import com.navigation.pathfinder.weight.EdgeWeightCalculator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class BidirectionalDijkstraPathfindingStrategy implements PathfindingStrategy {

  private final EdgeWeightCalculator calculator;
  private static final PathSummaryCreator pathSummaryCreator = new PathSummaryCreator();

  public BidirectionalDijkstraPathfindingStrategy(EdgeWeightCalculator calculator) {
    this.calculator = calculator;
  }

  @Override
  public PathSummary findPath(Vertex start, Vertex end, Graph graph) {
    var minDistancesForward = new HashMap<Vertex, Double>();
    var minDistancesBackward = new HashMap<Vertex, Double>();
    minDistancesForward.put(start, 0.0);
    minDistancesBackward.put(end, 0.0);

    var predecessorTreeBackward = new HashMap<Vertex, Edge>();
    var predecessorTreeForward = new HashMap<Vertex, Edge>();
    predecessorTreeForward.put(start, null);
    predecessorTreeBackward.put(end, null);

    var pqForward = new PriorityQueue<ScoredGraphVertex>();
    var pqBackward = new PriorityQueue<ScoredGraphVertex>();
    pqForward.add(new ScoredGraphVertex(start, 0.0));
    pqBackward.add(new ScoredGraphVertex(end, 0.0));

    var reversedGraph = graph.reversed();

    while (!pqForward.isEmpty() && !pqBackward.isEmpty()) {
      var currForward = pqForward.poll();
      if (predecessorTreeBackward.containsKey(currForward.vertex())) {
        var center =
            findCenterVertex(
                currForward.vertex(),
                minDistancesForward.get(currForward.vertex()),
                minDistancesBackward.get(currForward.vertex()),
                pqForward,
                pqBackward);
        return pathSummaryCreator.createBidirectionalPath(
            start, center, end, predecessorTreeForward, predecessorTreeBackward);
      }
      visitVertex(currForward, graph, pqForward, predecessorTreeForward, minDistancesForward);

      var currBackward = pqBackward.poll();
      if (predecessorTreeForward.containsKey(currBackward.vertex())) {
        var center =
            findCenterVertex(
                currBackward.vertex(),
                minDistancesForward.get(currBackward.vertex()),
                minDistancesBackward.get(currBackward.vertex()),
                pqForward,
                pqBackward);

        return pathSummaryCreator.createBidirectionalPath(
            start, center, end, predecessorTreeForward, predecessorTreeBackward);
      }
      visitVertex(
          currBackward, reversedGraph, pqBackward, predecessorTreeBackward, minDistancesBackward);
    }

    return pathSummaryCreator.createBidirectionalPath(
        start, start, end, predecessorTreeForward, predecessorTreeBackward);
  }

  private void visitVertex(
      ScoredGraphVertex curr,
      Graph graph,
      Queue<ScoredGraphVertex> pq,
      Map<Vertex, Edge> predecessorTree,
      Map<Vertex, Double> minDistances) {
    var currVertex = curr.vertex();
    var distanceSoFar = curr.getScore();

    if (distanceSoFar > minDistances.getOrDefault(currVertex, Double.MAX_VALUE)) {
      return;
    }

    for (var edge : graph.getVertexEdges(currVertex)) {
      double distance = distanceSoFar + calculator.calculateWeight(edge);

      if (distance < minDistances.getOrDefault(edge.getTo(), Double.MAX_VALUE)) {
        minDistances.put(edge.getTo(), distance);
        predecessorTree.put(edge.getTo(), edge);
        pq.add(new ScoredGraphVertex(edge.getTo(), distance));
      }
    }
  }

  private Vertex findCenterVertex(
      Vertex candidate,
      double leftScore,
      double rightScore,
      Queue<ScoredGraphVertex> pqForward,
      Queue<ScoredGraphVertex> pqBackward) {

    Map<Vertex, Double> scoresForward = buildMinVertexScoreMap(pqForward);
    Map<Vertex, Double> scoresBackward = buildMinVertexScoreMap(pqBackward);

    var minVertex = candidate;
    var minScore = leftScore + rightScore;

    for (Entry<Vertex, Double> forwardEntry : scoresForward.entrySet()) {

      if (!scoresBackward.containsKey(forwardEntry.getKey())) {
        continue;
      }

      double currScore = scoresBackward.get(forwardEntry.getKey()) + forwardEntry.getValue();
      if (minScore > currScore) {
        minScore = currScore;
        minVertex = forwardEntry.getKey();
      }
    }

    return minVertex;
  }

  private Map<Vertex, Double> buildMinVertexScoreMap(Queue<ScoredGraphVertex> scoredVertices) {
    return scoredVertices.stream()
        .collect(
            Collectors.toMap(ScoredGraphVertex::vertex, ScoredGraphVertex::getScore, Math::min));
  }
}
