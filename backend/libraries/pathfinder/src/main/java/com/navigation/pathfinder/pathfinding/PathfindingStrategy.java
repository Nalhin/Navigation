package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;

public interface PathfindingStrategy {
  Path findShortestPath(Vertex start, Vertex target, Graph graph);
}
