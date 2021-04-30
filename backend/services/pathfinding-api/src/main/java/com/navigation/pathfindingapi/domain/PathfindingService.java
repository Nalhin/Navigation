package com.navigation.pathfindingapi.domain;

public interface PathfindingService {

  PathWithExecutionSummary calculatePathBetween(CalculatePathBetweenQuery query);

  PathWithExecutionSummary calculateBoundedPathBetween(CalculatePathBetweenQuery query, BoundsQuery bounds);
}
