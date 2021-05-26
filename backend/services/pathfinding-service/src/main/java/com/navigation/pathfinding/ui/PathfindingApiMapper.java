package com.navigation.pathfinding.ui;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinding.ui.dto.params.BoundsRequestDtoParams;
import com.navigation.pathfinding.ui.dto.params.PathRequestDtoParams;
import com.navigation.pathfinding.ui.dto.response.NodeResponseDto;
import com.navigation.pathfinding.ui.dto.response.PathResponseDto;
import com.navigation.pathfinding.ui.dto.shared.PathfindingOptimizationsDto;
import com.navigation.pathfinding.ui.dto.shared.PathfindingAlgorithmsDto;
import com.navigation.pathfinding.domain.*;
import com.navigation.pathfinding.application.PathBetweenCoordinatesUseCase.BoundsQuery;
import com.navigation.pathfinding.application.PathBetweenCoordinatesUseCase.PathBetweenCoordinatesQuery;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class PathfindingApiMapper {

  PathResponseDto toDto(PathfindingExecutionSummary pathWithSummary) {
    var response = new PathResponseDto();
    var path = pathWithSummary.getPathSummary();
    response.setSimplePath(
        path.simplePath().stream().map(this::toDto).collect(Collectors.toList()));

    response.setTotalDistance(path.totalDistance());
    response.setTotalNodes(path.numberOfVertices());
    response.setTotalVisitedNodes(path.totalVisitedVertices());
    response.setTotalDuration(path.totalDuration());
    response.setExecutionDuration(pathWithSummary.getExecutionDurationInSeconds());
    response.setSearchBoundaries(
        path.searchBoundaries().stream()
            .map(hull -> hull.stream().map(this::toDto).collect(Collectors.toList()))
            .collect(Collectors.toList()));
    response.setAlgorithm(toDto(pathWithSummary.getAlgorithm()));
    response.setOptimization(toDto(pathWithSummary.getOptimization()));
    response.setFound(path.isFound());

    return response;
  }

  NodeResponseDto toDto(Vertex vertex) {
    return new NodeResponseDto(
        vertex.getCoordinates().getLatitude(),
        vertex.getCoordinates().getLongitude(),
        vertex.getId());
  }

  PathBetweenCoordinatesQuery toQuery(PathRequestDtoParams params) {
    var start = new Coordinates(params.getStartLatitude(), params.getStartLongitude());
    var end = new Coordinates(params.getEndLatitude(), params.getEndLongitude());
    var algorithm = toDomain(params.getAlgorithm());
    var optimization = toDomain(params.getOptimization());
    return new PathBetweenCoordinatesQuery(start, end, algorithm, optimization);
  }

  BoundsQuery toQuery(BoundsRequestDtoParams params) {
    var start = new Coordinates(params.getMinLatitude(), params.getMinLongitude());
    var end = new Coordinates(params.getMaxLatitude(), params.getMaxLongitude());
    return new BoundsQuery(start, end);
  }

  PathfindingAlgorithms toDomain(PathfindingAlgorithmsDto algorithmsDto) {
    return PathfindingAlgorithms.valueOf(algorithmsDto.toString());
  }

  PathfindingAlgorithmsDto toDto(PathfindingAlgorithms algorithms) {
    return PathfindingAlgorithmsDto.valueOf(algorithms.toString());
  }

  PathfindingOptimizations toDomain(PathfindingOptimizationsDto optimizationsDto) {
    return PathfindingOptimizations.valueOf(optimizationsDto.toString());
  }

  PathfindingOptimizationsDto toDto(PathfindingOptimizations optimizations) {
    return PathfindingOptimizationsDto.valueOf(optimizations.toString());
  }
}
