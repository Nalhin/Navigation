package com.navigation.pathfindingapi.api;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfindingapi.api.dto.params.BoundsRequestDtoParams;
import com.navigation.pathfindingapi.api.dto.params.PathRequestDtoParams;
import com.navigation.pathfindingapi.api.dto.response.NodeResponseDto;
import com.navigation.pathfindingapi.api.dto.response.PathResponseDto;
import com.navigation.pathfindingapi.api.dto.shared.OptimizationDto;
import com.navigation.pathfindingapi.api.dto.shared.PathfindingAlgorithmsDto;
import com.navigation.pathfindingapi.domain.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class ApiMapper {

  public PathResponseDto toResponse(PathWithExecutionSummary pathWithSummary) {
    var response = new PathResponseDto();
    var path = pathWithSummary.getPath();
    response.setSimplePath(
        path.simplePath().stream().map(this::toResponse).collect(Collectors.toList()));

    response.setTotalDistance(path.totalDistance());
    response.setTotalNodes(path.numberOfVertices());
    response.setTotalVisitedNodes(path.totalVisitedVertices());
    response.setTotalDuration(path.totalDuration());
    response.setExecutionDuration(pathWithSummary.getExecutionDurationInSeconds());
    response.setSearchBoundaries(
        path.searchBoundaries().stream()
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
