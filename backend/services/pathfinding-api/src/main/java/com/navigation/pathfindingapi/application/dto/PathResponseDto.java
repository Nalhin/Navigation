package com.navigation.pathfindingapi.application.dto;

import java.util.List;

public class PathResponseDto {

  private List<NodeResponseDto> points;

  public PathResponseDto() {
  }

  public PathResponseDto(List<NodeResponseDto> points) {
    this.points = points;
  }

  public List<NodeResponseDto> getPoints() {
    return points;
  }

  public void setPoints(List<NodeResponseDto> points) {
    this.points = points;
  }
}
