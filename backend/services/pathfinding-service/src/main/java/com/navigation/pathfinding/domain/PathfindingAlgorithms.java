package com.navigation.pathfinding.domain;

import java.util.Set;

public enum PathfindingAlgorithms {
  A_STAR(PathfindingOptimizations.ALL_OPTIMIZING),
  BIDIRECTIONAL_A_STAR(PathfindingOptimizations.ALL_OPTIMIZING),
  BELLMAN_FORD(PathfindingOptimizations.ALL_OPTIMIZING),
  BFS(Set.of(PathfindingOptimizations.NUMBER_OF_NODES)),
  BIDIRECTIONAL_BFS(Set.of(PathfindingOptimizations.NUMBER_OF_NODES)),
  DIJKSTRA(PathfindingOptimizations.ALL_OPTIMIZING),
  BIDIRECTIONAL_DIJKSTRA(PathfindingOptimizations.ALL_OPTIMIZING),
  DFS(Set.of(PathfindingOptimizations.NONE)),
  GREEDY_BEST_FIRST_SEARCH(PathfindingOptimizations.COORDINATES_BASED),
  BIDIRECTIONAL_GREEDY_BEST_FIRST_SEARCH(PathfindingOptimizations.COORDINATES_BASED);

  public final Set<PathfindingOptimizations> SUPPORTED_OPTIMIZATIONS;

  PathfindingAlgorithms(Set<PathfindingOptimizations> SUPPORTED_OPTIMIZATIONS) {
    this.SUPPORTED_OPTIMIZATIONS = SUPPORTED_OPTIMIZATIONS;
  }

  public boolean isOptimizationAllowed(PathfindingOptimizations optimization) {
    return SUPPORTED_OPTIMIZATIONS.contains(optimization);
  }
}
