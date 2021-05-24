package com.navigation.pathfinding.api.dto.response;

import com.navigation.pathfinding.api.dto.shared.PathfindingOptimizationsDto;
import com.navigation.pathfinding.api.dto.shared.PathfindingAlgorithmsDto;
import java.util.List;
import java.util.Objects;

public class PathResponseDto {

  private List<NodeResponseDto> simplePath;
  private List<List<NodeResponseDto>> searchBoundaries;
  private double totalDistance;
  private double totalDuration;
  private double totalNodes;
  private int totalVisitedNodes;
  private double executionDuration;
  private PathfindingOptimizationsDto optimization;
  private PathfindingAlgorithmsDto algorithm;
  private boolean isFound;

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

  public PathfindingOptimizationsDto getOptimization() {
    return optimization;
  }

  public void setOptimization(PathfindingOptimizationsDto optimization) {
    this.optimization = optimization;
  }

  public PathfindingAlgorithmsDto getAlgorithm() {
    return algorithm;
  }

  public void setAlgorithm(PathfindingAlgorithmsDto algorithm) {
    this.algorithm = algorithm;
  }

  public boolean isFound() {
    return isFound;
  }

  public void setFound(boolean found) {
    isFound = found;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PathResponseDto that = (PathResponseDto) o;
    return Double.compare(that.totalDistance, totalDistance) == 0 && Double.compare(that.totalDuration, totalDuration) == 0 && Double.compare(that.totalNodes, totalNodes) == 0 && totalVisitedNodes == that.totalVisitedNodes && Double.compare(that.executionDuration, executionDuration) == 0 && isFound == that.isFound && Objects.equals(simplePath, that.simplePath) && Objects.equals(searchBoundaries, that.searchBoundaries) && optimization == that.optimization && algorithm == that.algorithm;
  }

  @Override
  public int hashCode() {
    return Objects.hash(simplePath, searchBoundaries, totalDistance, totalDuration, totalNodes, totalVisitedNodes, executionDuration, optimization, algorithm, isFound);
  }
}
