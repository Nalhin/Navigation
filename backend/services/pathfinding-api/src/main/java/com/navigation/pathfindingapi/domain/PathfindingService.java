package com.navigation.pathfindingapi.domain;

public interface PathfindingService {

  PathWithExecutionDuration calculatePathBetween(CalculatePathBetweenQuery query);

  PathWithExecutionDuration calculateBoundedPathBetween(CalculatePathBetweenQuery query, BoundsQuery bounds);
}
