package com.navigation.pathfindingapi.api;

import com.navigation.pathfindingapi.api.dto.params.BoundsRequestDtoParams;
import com.navigation.pathfindingapi.api.dto.params.PathRequestDtoParams;
import com.navigation.pathfindingapi.api.dto.response.PathResponseDto;
import com.navigation.pathfindingapi.domain.PathfindingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "pathfinding")
@RestController
@RequestMapping("/api/v1/pathfinding")
class PathfindingController {

  private final PathfindingService service;
  private final ApiMapper mapper;

  public PathfindingController(PathfindingService service, ApiMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @Operation(
          tags = "pathfinding",
          description = "Find path between two points with selected algorithm and strategy")
  @ApiResponse(responseCode = "200", description = "Success")
  @ApiResponse(responseCode = "404", description = "Path not found")
  @GetMapping("/path-between")
  public ResponseEntity<PathResponseDto> findPath(@Validated PathRequestDtoParams requestDtoParams) {
    var path = service.calculatePathBetween(mapper.toQuery(requestDtoParams));
    return ResponseEntity.ok(mapper.toResponse(path));
  }


  @Operation(
          tags = "pathfinding",
          description = "Find bounded path between two points with selected algorithm and strategy")
  @ApiResponse(responseCode = "200", description = "Success")
  @ApiResponse(responseCode = "404", description = "Path not found")
  @GetMapping("/path-between/bounded")
  public ResponseEntity<PathResponseDto> findPath(@Validated PathRequestDtoParams requestDtoParams, @Validated BoundsRequestDtoParams boundsRequestDtoParams) {
    var path = service.calculateBoundedPathBetween(mapper.toQuery(requestDtoParams), mapper.toQuery(boundsRequestDtoParams));
    return ResponseEntity.ok(mapper.toResponse(path));
  }
}
