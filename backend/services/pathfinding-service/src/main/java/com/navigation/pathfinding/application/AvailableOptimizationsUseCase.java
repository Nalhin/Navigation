package com.navigation.pathfinding.application;

import com.navigation.pathfinding.domain.PathfindingAlgorithms;
import com.navigation.pathfinding.domain.PathfindingOptimizations;
import java.util.Set;

public interface AvailableOptimizationsUseCase {
  Set<PathfindingOptimizations> availableOptimizations(PathfindingAlgorithms algorithm);
}
