package com.navigation.pathfindingapi.domain;

import java.util.Set;

public enum PathfindingAlgorithms {
  A_STAR(PathfindingOptimizations.allOptimizing),
  BIDIRECTIONAL_A_STAR(PathfindingOptimizations.allOptimizing),
  BELLMAN_FORD(PathfindingOptimizations.allOptimizing),
  BFS(Set.of(PathfindingOptimizations.NUMBER_OF_NODES)),
  BIDIRECTIONAL_BFS(Set.of(PathfindingOptimizations.NUMBER_OF_NODES)),
  DIJKSTRA(PathfindingOptimizations.allOptimizing),
  BIDIRECTIONAL_DIJKSTRA(PathfindingOptimizations.allOptimizing),
  DFS(Set.of(PathfindingOptimizations.NONE));

  private final Set<PathfindingOptimizations> allowedOptimizations;

  PathfindingAlgorithms(Set<PathfindingOptimizations> allowedOptimizations) {
    this.allowedOptimizations = allowedOptimizations;
  }

  public boolean isOptimizationAllowed(PathfindingOptimizations optimization) {
    return allowedOptimizations.contains(optimization);
  }
}
