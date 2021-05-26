package com.navigation.pathfinding.application;

import com.navigation.pathfinding.domain.PathfindingAlgorithms;
import com.navigation.pathfinding.domain.PathfindingOptimizations;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class AvailableOptimizationsService implements AvailableOptimizationsUseCase {
  @Override
  public Set<PathfindingOptimizations> availableOptimizations(PathfindingAlgorithms algorithm) {
    return algorithm.SUPPORTED_OPTIMIZATIONS;
  }
}
