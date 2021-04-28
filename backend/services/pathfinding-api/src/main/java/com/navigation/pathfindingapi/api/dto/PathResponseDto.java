package com.navigation.pathfindingapi.api.dto;

import java.util.List;

public class PathResponseDto {

  private List<NodeResponseDto> simplePath;
  private List<DetailedNodeResponseDto> detailedPath;
  private double totalDistance;
  private double totalDuration;
  private double totalNodes;

  public PathResponseDto() {
  }

  public List<NodeResponseDto> getSimplePath() {
    return simplePath;
  }

  public void setSimplePath(List<NodeResponseDto> simplePath) {
    this.simplePath = simplePath;
  }

  public List<DetailedNodeResponseDto> getDetailedPath() {
    return detailedPath;
  }

  public void setDetailedPath(List<DetailedNodeResponseDto> detailedPath) {
    this.detailedPath = detailedPath;
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
}
