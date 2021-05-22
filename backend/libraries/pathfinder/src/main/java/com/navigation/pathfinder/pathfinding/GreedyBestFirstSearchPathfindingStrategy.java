package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.path.PathSummaryCreator;
import com.navigation.pathfinder.weight.EdgeWeightCalculator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class GreedyBestFirstSearchPathfindingStrategy implements PathfindingStrategy {

  private static final PathSummaryCreator pathSummaryCreator = new PathSummaryCreator();
  private final EdgeWeightCalculator calculator;

  public GreedyBestFirstSearchPathfindingStrategy(EdgeWeightCalculator calculator) {
    this.calculator = calculator;
  }

  @Override
  public PathSummary findPath(Vertex start, Vertex end, Graph graph) {
    var predecessorTree = new HashMap<Vertex, Edge>();
    predecessorTree.put(start, null);

    var pq = new PriorityQueue<ScoredGraphVertex>();
    pq.add(new ScoredGraphVertex(start, heuristic(start, end)));

    while (!pq.isEmpty()) {
      var curr = pq.poll().vertex();

      if (curr.equals(end)) {
        return pathSummaryCreator.createUnidirectionalPath(start, end, predecessorTree);
      }

      for (var edge : graph.getVertexEdges(curr)) {
        var neighbour = edge.getTo();

        if (!predecessorTree.containsKey(neighbour)) {
          predecessorTree.put(neighbour, edge);
          pq.add(new ScoredGraphVertex(neighbour, heuristic(neighbour, end)));
        }
      }
    }

    return pathSummaryCreator.createUnidirectionalPath(start, end, predecessorTree);
  }

  private double heuristic(Vertex from, Vertex to) {
    return calculator.estimateWeight(from, to);
  }
}
