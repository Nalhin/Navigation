package com.navigation.pathfindingapi.application;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinder.graph.Path;
import com.navigation.pathfindingapi.application.dto.PathResponseDto;
import com.navigation.pathfindingapi.application.dto.NodeResponseDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PathfindingMapper {

  public PathResponseDto toResponse(Path path) {
    var points = path.getVertices().stream()
        .map(vert -> toResponse(vert.getCoordinates()))
        .collect(Collectors.toList());
    return new PathResponseDto(points);
  }

  public NodeResponseDto toResponse(Coordinates coordinates) {
    return new NodeResponseDto(coordinates.getLatitude(), coordinates.getLongitude());
  }
}
