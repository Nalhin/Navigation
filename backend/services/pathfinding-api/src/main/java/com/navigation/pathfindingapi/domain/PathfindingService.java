package com.navigation.pathfindingapi.domain;

import java.util.List;
import java.util.Set;

public interface PathfindingService {

  PathWithExecutionSummary calculatePathBetween(PathBetweenCoordinatesQuery query);

  PathWithExecutionSummary calculateBoundedPathBetween(PathBetweenCoordinatesQuery query, Bounds bounds);

  Set<PathfindingOptimizations> availableOptimisations(PathfindingAlgorithms algorithm);
}
