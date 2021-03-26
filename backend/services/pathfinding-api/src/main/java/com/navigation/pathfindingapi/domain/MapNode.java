package com.navigation.pathfindingapi.domain;

public class MapNode {
  private final MapLocation location;
  private final long id;

  public MapNode(MapLocation location, long id) {
    this.location = location;
    this.id = id;
  }

  public MapLocation getLocation() {
    return location;
  }

  public long getId() {
    return id;
  }
}
