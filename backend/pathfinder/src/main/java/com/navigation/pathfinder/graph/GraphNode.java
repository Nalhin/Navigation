package com.navigation.pathfinder.graph;


import java.util.Objects;

public class GraphNode {

  private final int id;
  private final double latitude;
  private final double longitude;

  public GraphNode(int id, double latitude, double longitude) {
    this.id = id;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GraphNode graphNode = (GraphNode) o;
    return id == graphNode.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
