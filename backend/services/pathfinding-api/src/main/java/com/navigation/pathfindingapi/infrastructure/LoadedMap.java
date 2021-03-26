package com.navigation.pathfindingapi.infrastructure;


import com.navigation.pathfindingapi.domain.MapConnection;
import com.navigation.pathfindingapi.domain.MapNode;

import java.util.List;

class LoadedMap {
  private final List<MapConnection> connections;
  private final List<MapNode> nodes;

  public LoadedMap(List<MapNode> nodes, List<MapConnection> connections) {
    this.connections = connections;
    this.nodes = nodes;
  }

  public List<MapConnection> getConnections() {
    return connections;
  }

  public List<MapNode> getNodes() {
    return nodes;
  }
}
