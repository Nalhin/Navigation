package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.graph.Path;
import com.navigation.pathfinder.weight.EdgeWeightCalculator;

public class AStarPathfindingStrategy implements PathfindingStrategy {

  private final EdgeWeightCalculator calculator;
  private final PathBuilder pathBuilder = new PathBuilder();

  public AStarPathfindingStrategy(EdgeWeightCalculator calculator) {
    this.calculator = calculator;
  }

  @Override
  public Path findShortestPath(Vertex start, Vertex target, Graph graph) {
    return null;
  }
}
