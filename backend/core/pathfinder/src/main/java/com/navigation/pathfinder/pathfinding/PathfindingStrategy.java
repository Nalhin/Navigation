package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.GraphNode;
import com.navigation.pathfinder.graph.Path;

public interface PathfindingStrategy {
  Path findShortestPath(GraphNode start, GraphNode target, Graph graph);
}
