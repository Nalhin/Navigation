package com.navigation.pathfindingapi.domain;

import java.util.List;
import java.util.Optional;

public interface MapRepository {

  Optional<MapNode> closestNode(MapLocation location);

  List<MapNode> getNodes();

  List<MapConnection> getConnections();
}
