package com.navigation.pathfindingapi.api;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinder.graph.Path;
import com.navigation.pathfindingapi.api.dto.PathRequestDtoParams;
import com.navigation.pathfindingapi.api.dto.PathResponseDto;
import com.navigation.pathfindingapi.api.dto.NodeResponseDto;
import com.navigation.pathfindingapi.domain.CalculatePathBetweenQuery;
import com.navigation.pathfindingapi.domain.PathfindingStrategyFactory;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ApiMapper {

  public PathResponseDto toResponse(Path path) {
    var points = path.getVertices().stream()
        .map(vert -> toResponse(vert.getCoordinates()))
        .collect(Collectors.toList());
    return new PathResponseDto(points);
  }

  public NodeResponseDto toResponse(Coordinates coordinates) {
    return new NodeResponseDto(coordinates.getLatitude(), coordinates.getLongitude());
  }

  public CalculatePathBetweenQuery toQuery(PathRequestDtoParams params){
    var start = new Coordinates(params.getStartLatitude(), params.getStartLongitude());
    var end = new Coordinates(params.getEndLatitude(), params.getEndLongitude());
    var algorithm = PathfindingStrategyFactory.Algorithms.valueOf(params.getAlgorithm().toString());
    var optimization = PathfindingStrategyFactory.Optimizations.valueOf(params.getOptimization().toString());
    return new CalculatePathBetweenQuery(start, end, algorithm, optimization);
  }
}
