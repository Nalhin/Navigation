package com.navigation.pathfindingapi.api.dto.response;

import com.navigation.pathfindingapi.api.dto.shared.OptimizationDto;
import com.navigation.pathfindingapi.api.dto.shared.PathfindingAlgorithmsDto;
import java.util.List;

public class PathResponseDto {

  private List<NodeResponseDto> simplePath;
  private List<List<NodeResponseDto>> searchBoundaries;
  private double totalDistance;
  private double totalDuration;
  private double totalNodes;
  private int totalVisitedNodes;
  private double executionDuration;
  private OptimizationDto optimization;
  private PathfindingAlgorithmsDto algorithm;

  public PathResponseDto() {}

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

  public void setExecutionDuration(double executionDuration) {
    this.executionDuration = executionDuration;
  }

  public double getExecutionDuration() {
    return executionDuration;
  }

  public int getTotalVisitedNodes() {
    return totalVisitedNodes;
  }

  public void setTotalVisitedNodes(int totalVisitedNodes) {
    this.totalVisitedNodes = totalVisitedNodes;
  }

  public List<List<NodeResponseDto>> getSearchBoundaries() {
    return searchBoundaries;
  }

  public void setSearchBoundaries(List<List<NodeResponseDto>> searchBoundaries) {
    this.searchBoundaries = searchBoundaries;
  }

  public OptimizationDto getOptimization() {
    return optimization;
  }

  public void setOptimization(OptimizationDto optimization) {
    this.optimization = optimization;
  }

  public PathfindingAlgorithmsDto getAlgorithm() {
    return algorithm;
  }

  public void setAlgorithm(PathfindingAlgorithmsDto algorithm) {
    this.algorithm = algorithm;
  }
}
