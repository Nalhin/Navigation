package com.navigation.pathfindingapi.api;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfindingapi.api.dto.PathRequestDtoParams;
import com.navigation.pathfindingapi.api.dto.PathResponseDto;
import com.navigation.pathfindingapi.api.dto.NodeResponseDto;
import com.navigation.pathfindingapi.domain.CalculatePathBetweenQuery;
import com.navigation.pathfindingapi.domain.PathWithExecutionDuration;
import com.navigation.pathfindingapi.domain.PathfindingStrategyFactory;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ApiMapper {

  public PathResponseDto toResponse(PathWithExecutionDuration extendedPath) {
    var response = new PathResponseDto();
    var path = extendedPath.getPath();
    response.setSimplePath(
        path.getSimplePath().stream().map(this::toResponse).collect(Collectors.toList()));

    response.setTotalDistance(path.totalDistance());
    response.setTotalNodes(path.numberOfVertices());
    response.setTotalVisitedNodes(path.totalVisitedVertices());
    response.setTotalDuration(path.totalDuration());
    response.setExecutionDuration(extendedPath.getExecutionDuration());
    return response;
  }

  public NodeResponseDto toResponse(Vertex vertex) {
    return new NodeResponseDto(
        vertex.getCoordinates().getLatitude(),
        vertex.getCoordinates().getLongitude(),
        vertex.getId());
  }

  public CalculatePathBetweenQuery toQuery(PathRequestDtoParams params) {
    var start = new Coordinates(params.getStartLatitude(), params.getStartLongitude());
    var end = new Coordinates(params.getEndLatitude(), params.getEndLongitude());
    var algorithm = PathfindingStrategyFactory.Algorithms.valueOf(params.getAlgorithm().toString());
    var optimization =
        PathfindingStrategyFactory.Optimizations.valueOf(params.getOptimization().toString());
    return new CalculatePathBetweenQuery(start, end, algorithm, optimization);
  }
}
