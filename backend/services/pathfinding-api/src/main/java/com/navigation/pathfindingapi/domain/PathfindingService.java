package com.navigation.pathfindingapi.domain;

public interface PathfindingService {

  PathWithExecutionSummary calculatePathBetween(PathBetweenCoordinatesQuery query);

  PathWithExecutionSummary calculateBoundedPathBetween(PathBetweenCoordinatesQuery query, Bounds bounds);
}
