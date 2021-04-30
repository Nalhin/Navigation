package com.navigation.pathfindingapi.api.dto;

public class PathRequestDtoParams {
  private Double startLatitude;
  private Double startLongitude;
  private Double endLatitude;
  private Double endLongitude;
  private PathfindingAlgorithmsDto algorithm;
  private OptimizationDto optimization;

  public Double getStartLatitude() {
    return startLatitude;
  }

  public void setStartLatitude(Double startLatitude) {
    this.startLatitude = startLatitude;
  }

  public Double getStartLongitude() {
    return startLongitude;
  }

  public void setStartLongitude(Double startLongitude) {
    this.startLongitude = startLongitude;
  }

  public Double getEndLatitude() {
    return endLatitude;
  }

  public void setEndLatitude(Double endLatitude) {
    this.endLatitude = endLatitude;
  }

  public Double getEndLongitude() {
    return endLongitude;
  }

  public void setEndLongitude(Double endLongitude) {
    this.endLongitude = endLongitude;
  }

  public PathfindingAlgorithmsDto getAlgorithm() {
    return algorithm;
  }

  public void setAlgorithm(PathfindingAlgorithmsDto algorithm) {
    this.algorithm = algorithm;
  }

  public OptimizationDto getOptimization() {
    return optimization;
  }

  public void setOptimization(OptimizationDto optimization) {
    this.optimization = optimization;
  }
}
