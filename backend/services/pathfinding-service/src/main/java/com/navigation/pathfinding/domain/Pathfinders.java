package com.navigation.pathfinding.domain;

import com.navigation.pathfinder.pathfinding.PathfindingStrategy;
import io.vavr.control.Option;

public interface Pathfinders {

  Option<PathfindingStrategy> selectPathfinder(
      PathfindingAlgorithms algorithms, PathfindingOptimizations optimizations);
}
