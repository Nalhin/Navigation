package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinder.path.PathSummary;

public interface PathfindingStrategy {
  PathSummary findShortestPath(Vertex start, Vertex target, Graph graph);
}
