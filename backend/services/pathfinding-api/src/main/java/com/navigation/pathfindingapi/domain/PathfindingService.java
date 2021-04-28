package com.navigation.pathfindingapi.domain;

import com.navigation.pathfinder.graph.Path;

public interface PathfindingService {


     Path calculatePathBetween(CalculatePathBetweenQuery query);
}
