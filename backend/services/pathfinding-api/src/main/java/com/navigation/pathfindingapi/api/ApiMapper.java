package com.navigation.pathfindingapi.api;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinder.graph.Path;
import com.navigation.pathfinder.graph.PathDetailsVertex;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfindingapi.api.dto.DetailedNodeResponseDto;
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
    var response = new PathResponseDto();
    response.setSimplePath(
        path.getSimplePath().stream().map(this::toResponse).collect(Collectors.toList()));
    response.setDetailedPath(
        path.getDetailedPath().stream().map(this::toResponse).collect(Collectors.toList()));

    response.setTotalDistance(path.getTotalDistance());
    response.setTotalNodes(path.getNumberOfVertices());
    response.setTotalDuration(path.getTotalDuration());
    return response;
  }

  public NodeResponseDto toResponse(Vertex vertex) {
    return new NodeResponseDto(
        vertex.getCoordinates().getLatitude(),
        vertex.getCoordinates().getLongitude(),
        vertex.getId());
  }

  public DetailedNodeResponseDto toResponse(PathDetailsVertex detailedVertex) {
    var response = new DetailedNodeResponseDto();
    response.setCumulativeDistance(detailedVertex.getCumulativeDistance());
    response.setDistanceFromPrevious(detailedVertex.getDistanceFromPrevious());
    response.setCumulativeTime(detailedVertex.getCumulativeTime());
    response.setTimeFromPrevious(detailedVertex.getTimeFromPrevious());
    response.setMaxSpeedFromPrevious(detailedVertex.getMaxSpeedFromPrevious());
    response.setNode(toResponse(detailedVertex.getVertex()));
    response.setTotalPathPercentage(detailedVertex.getTotalPathPercentage());
    return response;
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
