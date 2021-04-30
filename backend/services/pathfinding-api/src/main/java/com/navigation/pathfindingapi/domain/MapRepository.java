package com.navigation.pathfindingapi.domain;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;

import java.util.List;
import java.util.Optional;

public interface MapRepository {

  Optional<MapNode> closestNode(Coordinates location);

  Graph prepareGraph();

  Optional<MapNode> closestNodeWithinBounds(Coordinates location, BoundsQuery boundsQuery);

  Graph prepareGraphWithinBounds(BoundsQuery boundsQuery);
}
