package com.navigation.pathfindingapi.domain;

public class MapConnection {
  private final MapNode from;
  private final MapNode to;

  public MapConnection(MapNode from, MapNode to) {
    this.from = from;
    this.to = to;
  }

  public MapNode getFrom() {
    return from;
  }

  public MapNode getTo() {
    return to;
  }
}
