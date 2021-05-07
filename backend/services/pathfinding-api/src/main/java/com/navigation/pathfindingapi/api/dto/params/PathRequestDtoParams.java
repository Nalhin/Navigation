package com.navigation.pathfindingapi.api.dto.params;

import com.navigation.pathfindingapi.api.dto.shared.PathfindingOptimizationsDto;
import com.navigation.pathfindingapi.api.dto.shared.PathfindingAlgorithmsDto;
import java.util.Objects;
import javax.validation.constraints.NotNull;

public class PathRequestDtoParams {
  @NotNull private Double startLatitude;
  @NotNull private Double startLongitude;
  @NotNull private Double endLatitude;
  @NotNull private Double endLongitude;
  @NotNull private PathfindingAlgorithmsDto algorithm;
  @NotNull private PathfindingOptimizationsDto optimization;

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

  public PathfindingOptimizationsDto getOptimization() {
    return optimization;
  }

  public void setOptimization(PathfindingOptimizationsDto optimization) {
    this.optimization = optimization;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PathRequestDtoParams that = (PathRequestDtoParams) o;
    return Objects.equals(startLatitude, that.startLatitude)
        && Objects.equals(startLongitude, that.startLongitude)
        && Objects.equals(endLatitude, that.endLatitude)
        && Objects.equals(endLongitude, that.endLongitude)
        && algorithm == that.algorithm
        && optimization == that.optimization;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        startLatitude, startLongitude, endLatitude, endLongitude, algorithm, optimization);
  }
}
