package com.navigation.pathfindingapi.api.dto;

public class DetailedNodeResponseDto {
  private double cumulativeDistance;
  private double distanceFromPrevious;
  private double cumulativeTime;
  private double timeFromPrevious;
  private int maxSpeedFromPrevious;
  private NodeResponseDto node;

  public double getCumulativeDistance() {
    return cumulativeDistance;
  }

  public void setCumulativeDistance(double cumulativeDistance) {
    this.cumulativeDistance = cumulativeDistance;
  }

  public double getDistanceFromPrevious() {
    return distanceFromPrevious;
  }

  public void setDistanceFromPrevious(double distanceFromPrevious) {
    this.distanceFromPrevious = distanceFromPrevious;
  }

  public double getCumulativeTime() {
    return cumulativeTime;
  }

  public void setCumulativeTime(double cumulativeTime) {
    this.cumulativeTime = cumulativeTime;
  }

  public double getTimeFromPrevious() {
    return timeFromPrevious;
  }

  public void setTimeFromPrevious(double timeFromPrevious) {
    this.timeFromPrevious = timeFromPrevious;
  }

  public int getMaxSpeedFromPrevious() {
    return maxSpeedFromPrevious;
  }

  public void setMaxSpeedFromPrevious(int maxSpeedFromPrevious) {
    this.maxSpeedFromPrevious = maxSpeedFromPrevious;
  }

  public NodeResponseDto getNode() {
    return node;
  }

  public void setNode(NodeResponseDto node) {
    this.node = node;
  }
}
