package com.navigation.pathfindingapi.api.dto;

import java.time.Duration;
import java.util.List;

public class PathResponseDto {

  private List<NodeResponseDto> simplePath;
  private List<NodeResponseDto> searchBoundaries;
  private double totalDistance;
  private double totalDuration;
  private double totalNodes;
  private int totalVisitedNodes;
  private Duration executionDuration;

  public PathResponseDto() {
  }

  public List<NodeResponseDto> getSimplePath() {
    return simplePath;
  }

  public void setSimplePath(List<NodeResponseDto> simplePath) {
    this.simplePath = simplePath;
  }

  public double getTotalDistance() {
    return totalDistance;
  }

  public void setTotalDistance(double totalDistance) {
    this.totalDistance = totalDistance;
  }

  public double getTotalDuration() {
    return totalDuration;
  }

  public void setTotalDuration(double totalDuration) {
    this.totalDuration = totalDuration;
  }

  public double getTotalNodes() {
    return totalNodes;
  }

  public void setTotalNodes(double totalNodes) {
    this.totalNodes = totalNodes;
  }

  public void setExecutionDuration(Duration executionDuration) {
    this.executionDuration = executionDuration;
  }

  public double getExecutionDuration() {
    return executionDuration.getNano() / 1_000_000_000.0;
  }

  public int getTotalVisitedNodes() {
    return totalVisitedNodes;
  }

  public void setTotalVisitedNodes(int totalVisitedNodes) {
    this.totalVisitedNodes = totalVisitedNodes;
  }

  public List<NodeResponseDto> getSearchBoundaries() {
    return searchBoundaries;
  }

  public void setSearchBoundaries(List<NodeResponseDto> searchBoundaries) {
    this.searchBoundaries = searchBoundaries;
  }
}
