package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.path.PathSummaryCreator;
import com.navigation.pathfinder.weight.EdgeWeightCalculator;

import java.util.*;

public class DijkstraPathfindingStrategy implements PathfindingStrategy {

  private final EdgeWeightCalculator calculator;
  private static final PathSummaryCreator pathSummaryCreator = new PathSummaryCreator();

  public DijkstraPathfindingStrategy(EdgeWeightCalculator calculator) {
    this.calculator = calculator;
  }

  @Override
  public PathSummary findPath(Vertex start, Vertex end, Graph graph) {
    var minDistances = new HashMap<Vertex, Double>();
    minDistances.put(start, 0.0);

    var predecessorTree = new HashMap<Vertex, Edge>();
    predecessorTree.put(start, null);

    var pq = new PriorityQueue<ScoredGraphVertex>();
    pq.add(new ScoredGraphVertex(start, 0.0));

    while (!pq.isEmpty()) {
      var curr = pq.poll();
      var currVertex = curr.vertex();
      var distanceSoFar = curr.getScore();

      if (distanceSoFar > minDistances.getOrDefault(currVertex, Double.MAX_VALUE)) {
        continue;
      }

      if (currVertex.equals(end)) {
        return pathSummaryCreator.createUnidirectionalPath(start, end, predecessorTree);
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

    return pathSummaryCreator.createUnidirectionalPath(start, end, predecessorTree);
  }
}
