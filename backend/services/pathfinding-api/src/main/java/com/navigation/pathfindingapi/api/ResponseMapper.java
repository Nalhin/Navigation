package com.navigation.pathfindingapi.api;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinder.graph.Path;
import com.navigation.pathfindingapi.api.dto.PathResponseDto;
import com.navigation.pathfindingapi.api.dto.NodeResponseDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ResponseMapper {

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
