package com.navigation.pathfindingapi.domain;

import com.navigation.pathfinder.graph.Coordinates;

public class MapNode {
  private final long id;
  private final Coordinates location;

  public MapNode(long id, Coordinates location) {
    this.location = location;
    this.id = id;
  }

  public Coordinates getLocation() {
    return location;
  }

  public long getId() {
    return id;
  }
}
