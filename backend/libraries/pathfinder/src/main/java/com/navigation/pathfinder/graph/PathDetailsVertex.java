package com.navigation.pathfinder.graph;

public class PathDetailsVertex {
  private final double cumulativeDistance;
  private final double distanceFromPrevious;
  private final double cumulativeTime;
  private final double timeFromPrevious;
  private final int maxSpeedFromPrevious;
  private final double totalPathPercentage;
  private final Vertex vertex;

  public PathDetailsVertex(
      double cumulativeDistance,
      double distanceFromPrevious,
      double cumulativeTime,
      double timeFromPrevious,
      int maxSpeedFromPrevious,
      double totalPathPercentage,
      Vertex vertex) {
    this.cumulativeDistance = cumulativeDistance;
    this.distanceFromPrevious = distanceFromPrevious;
    this.cumulativeTime = cumulativeTime;
    this.timeFromPrevious = timeFromPrevious;
    this.maxSpeedFromPrevious = maxSpeedFromPrevious;
    this.totalPathPercentage = totalPathPercentage;
    this.vertex = vertex;
  }

  public double getCumulativeDistance() {
    return cumulativeDistance;
  }

  public double getDistanceFromPrevious() {
    return distanceFromPrevious;
  }

  public double getCumulativeTime() {
    return cumulativeTime;
  }

  public double getTimeFromPrevious() {
    return timeFromPrevious;
  }

  public int getMaxSpeedFromPrevious() {
    return maxSpeedFromPrevious;
  }

  public double getTotalPathPercentage() {
    return totalPathPercentage;
  }

  public Vertex getVertex() {
    return vertex;
  }
}
