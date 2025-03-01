package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Vertex;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.stream.Collectors;

class BidirectionalCenterVertexFinder {

  Vertex findCenterVertex(
      Vertex candidate,
      double candidateBidirectionalScore,
      Queue<ScoredGraphVertex> pqForward,
      Queue<ScoredGraphVertex> pqBackward) {

    Map<Vertex, Double> scoresForward = buildMinVertexScoreMap(pqForward);
    Map<Vertex, Double> scoresBackward = buildMinVertexScoreMap(pqBackward);

    var minVertex = candidate;
    var minScore = candidateBidirectionalScore;

    for (Entry<Vertex, Double> forwardEntry : scoresForward.entrySet()) {

      if (!scoresBackward.containsKey(forwardEntry.getKey())) {
        continue;
      }

      var currScore = scoresBackward.get(forwardEntry.getKey()) + forwardEntry.getValue();
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
            Collectors.toMap(ScoredGraphVertex::vertex, ScoredGraphVertex::score, Math::min));
  }
}
