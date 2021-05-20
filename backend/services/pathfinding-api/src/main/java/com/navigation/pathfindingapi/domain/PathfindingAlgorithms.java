package com.navigation.pathfindingapi.domain;

import java.util.Set;

public enum PathfindingAlgorithms {
  A_STAR((Set.of(PathfindingOptimizations.values()))),
  BELLMAN_FORD(Set.of(PathfindingOptimizations.values())),
  BFS(Set.of(PathfindingOptimizations.NUMBER_OF_NODES)),
  BIDIRECTIONAL_BFS(Set.of(PathfindingOptimizations.NUMBER_OF_NODES)),
  DIJKSTRA(Set.of(PathfindingOptimizations.values()));

  private final Set<PathfindingOptimizations> allowedOptimizations;

  PathfindingAlgorithms(Set<PathfindingOptimizations> allowedOptimizations) {
    this.allowedOptimizations = allowedOptimizations;
  }

  public boolean isOptimizationAllowed(PathfindingOptimizations optimization) {
    return allowedOptimizations.contains(optimization);
  }
}
