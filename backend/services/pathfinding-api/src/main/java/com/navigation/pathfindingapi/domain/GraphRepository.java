package com.navigation.pathfindingapi.domain;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;

import java.util.Optional;

public interface GraphRepository {

  Optional<Vertex> closestNode(Coordinates location);

  Graph prepareGraph();

  Graph prepareGraphWithinBounds(Bounds bounds);
}
