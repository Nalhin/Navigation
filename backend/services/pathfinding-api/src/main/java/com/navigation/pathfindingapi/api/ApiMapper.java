package com.navigation.pathfindingapi.api;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfindingapi.api.dto.*;
import com.navigation.pathfindingapi.domain.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ApiMapper {

  public PathResponseDto toResponse(PathWithExecutionSummary pathWithSummary) {
    var response = new PathResponseDto();
    var path = pathWithSummary.getPath();
    response.setSimplePath(
        path.getSimplePath().stream().map(this::toResponse).collect(Collectors.toList()));

    response.setTotalDistance(path.totalDistance());
    response.setTotalNodes(path.numberOfVertices());
    response.setTotalVisitedNodes(path.totalVisitedVertices());
    response.setTotalDuration(path.totalDuration());
    response.setExecutionDuration(pathWithSummary.getExecutionDurationInSeconds());
    response.setSearchBoundaries(
        path.convexHulls().stream()
            .map(hull -> hull.stream().map(this::toResponse).collect(Collectors.toList()))
            .collect(Collectors.toList()));
    response.setAlgorithm(PathfindingAlgorithmsDto.valueOf(pathWithSummary.getAlgorithm().toString()));
    response.setOptimization(OptimizationDto.valueOf(pathWithSummary.getOptimization().toString()));
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
    var algorithm = PathfindingAlgorithms.valueOf(params.getAlgorithm().toString());
    var optimization =
        PathfindingOptimizations.valueOf(params.getOptimization().toString());
    return new CalculatePathBetweenQuery(start, end, algorithm, optimization);
  }

  public BoundsQuery toQuery(BoundsRequestDtoParams params) {
    var start = new Coordinates(params.getMinLatitude(), params.getMinLongitude());
    var end = new Coordinates(params.getMaxLatitude(), params.getMaxLongitude());
    return new BoundsQuery(start, end);
  }
}
