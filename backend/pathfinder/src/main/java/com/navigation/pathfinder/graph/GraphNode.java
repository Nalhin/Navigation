package com.navigation.pathfinder.graph;


public class GraphNode {

  private final int id;
  private final double latitude;
  private final double longitude;

  public GraphNode(int id, double latitude, double longitude) {
    this.id = id;
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
