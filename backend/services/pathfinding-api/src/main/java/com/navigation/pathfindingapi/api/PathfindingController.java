package com.navigation.pathfindingapi.api;

import com.navigation.pathfindingapi.api.dto.params.BoundsRequestDtoParams;
import com.navigation.pathfindingapi.api.dto.params.PathRequestDtoParams;
import com.navigation.pathfindingapi.api.dto.response.PathResponseDto;
import com.navigation.pathfindingapi.api.dto.shared.PathfindingAlgorithmsDto;
import com.navigation.pathfindingapi.api.dto.shared.PathfindingOptimizationsDto;
import com.navigation.pathfindingapi.domain.PathfindingOptimizations;
import com.navigation.pathfindingapi.domain.PathfindingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "pathfinding")
@RestController
@RequestMapping("/api/v1/pathfinding")
class PathfindingController {

  private final PathfindingService service;
  private final PathfindingApiMapper mapper;

  public PathfindingController(PathfindingService service, PathfindingApiMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @Operation(
      tags = "pathfinding",
      description = "Find path between two points with selected algorithm and strategy")
  @ApiResponse(responseCode = "200", description = "Success")
  @ApiResponse(responseCode = "404", description = "Path not found")
  @GetMapping(path = "/path-between", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PathResponseDto> findPath(
      @Validated PathRequestDtoParams requestDtoParams) {
    var path = service.calculatePathBetween(mapper.toQuery(requestDtoParams));
    return ResponseEntity.ok(mapper.toResponse(path));
  }

  @Operation(
      tags = "pathfinding",
      description = "Find bounded path between two points with selected algorithm and strategy")
  @ApiResponse(responseCode = "200", description = "Success")
  @ApiResponse(responseCode = "404", description = "Path not found")
  @GetMapping(path = "/path-between/bounded", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PathResponseDto> findPath(
      @Validated PathRequestDtoParams requestDtoParams,
      @Validated BoundsRequestDtoParams boundsRequestDtoParams) {
    var path =
        service.calculateBoundedPathBetween(
            mapper.toQuery(requestDtoParams), mapper.toBounds(boundsRequestDtoParams));
    return ResponseEntity.ok(mapper.toResponse(path));
  }

  @Operation(
      tags = "pathfinding",
      description = "Return available optimizations for the pathfinding algorithm")
  @ApiResponse(responseCode = "200", description = "Success")
  @GetMapping(path = "/algorithms/{algorithm}/available-optimizations")
  public ResponseEntity<List<PathfindingOptimizationsDto>> getAlgorithmOptimizations(
      @PathVariable PathfindingAlgorithmsDto algorithm) {
    var availableOptimizations = service.availableOptimisations(mapper.toDomain(algorithm));
    return ResponseEntity.ok(
        availableOptimizations.stream().map(mapper::toResponse).collect(Collectors.toList()));
  }
}
