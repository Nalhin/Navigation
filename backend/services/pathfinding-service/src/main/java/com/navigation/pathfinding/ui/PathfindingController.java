package com.navigation.pathfinding.ui;

import com.navigation.pathfinding.ui.dto.params.BoundsRequestDtoParams;
import com.navigation.pathfinding.ui.dto.params.PathRequestDtoParams;
import com.navigation.pathfinding.ui.dto.response.ErrorResponse;
import com.navigation.pathfinding.ui.dto.response.PathResponseDto;
import com.navigation.pathfinding.ui.dto.shared.PathfindingAlgorithmsDto;
import com.navigation.pathfinding.ui.dto.shared.PathfindingOptimizationsDto;
import com.navigation.pathfinding.application.AvailableOptimizationsUseCase;
import com.navigation.pathfinding.application.PathBetweenCoordinatesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

  private final PathBetweenCoordinatesUseCase pathBetweenUseCase;
  private final AvailableOptimizationsUseCase availableOptimizationsUseCase;
  private final PathfindingDtoMapper dtoMapper;
  private final PathfindingRestResponseMapper responseMapper;

  public PathfindingController(
      PathBetweenCoordinatesUseCase pathBetweenUseCase,
      AvailableOptimizationsUseCase availableOptimizationsUseCase,
      PathfindingDtoMapper dtoMapper,
      PathfindingRestResponseMapper responseMapper) {
    this.pathBetweenUseCase = pathBetweenUseCase;
    this.availableOptimizationsUseCase = availableOptimizationsUseCase;
    this.dtoMapper = dtoMapper;
    this.responseMapper = responseMapper;
  }

  @Operation(
      tags = "pathfinding",
      description = "Find path between two points with selected algorithm and strategy")
  @ApiResponse(
      responseCode = "200",
      description = "Success",
      content = @Content(schema = @Schema(implementation = PathResponseDto.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Invalid configuration",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Points not found",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @GetMapping(path = "/path-between", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findPath(@Validated PathRequestDtoParams requestDtoParams) {
    var path = pathBetweenUseCase.calculatePathBetween(dtoMapper.toQuery(requestDtoParams));

    return path.map((result) -> responseMapper.mapToSuccess(dtoMapper.toResponse(result)))
        .getOrElseGet(responseMapper::mapToError);
  }

  @Operation(
      tags = "pathfinding",
      description = "Find bounded path between two points with selected algorithm and strategy")
  @ApiResponse(
      responseCode = "200",
      description = "Success",
      content = @Content(schema = @Schema(implementation = PathResponseDto.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Invalid configuration",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Points not found",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @GetMapping(path = "/path-between/bounded", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findPath(
      @Validated PathRequestDtoParams requestDtoParams,
      @Validated BoundsRequestDtoParams boundsRequestDtoParams) {
    var path =
        pathBetweenUseCase.calculateBoundedPathBetween(
            dtoMapper.toQuery(requestDtoParams), dtoMapper.toQuery(boundsRequestDtoParams));

    return path.map((result) -> responseMapper.mapToSuccess(dtoMapper.toResponse(result)))
        .getOrElseGet(responseMapper::mapToError);
  }

  @Operation(
      tags = "pathfinding",
      description = "Return available optimizations for the pathfinding algorithm")
  @ApiResponse(responseCode = "200", description = "Success")
  @GetMapping(path = "/algorithms/{algorithm}/available-optimizations")
  public ResponseEntity<List<PathfindingOptimizationsDto>> getAlgorithmOptimizations(
      @PathVariable PathfindingAlgorithmsDto algorithm) {
    var availableOptimizations =
        availableOptimizationsUseCase.availableOptimizations(dtoMapper.toDomain(algorithm));

    return ResponseEntity.ok(
        availableOptimizations.stream().map(dtoMapper::toResponse).collect(Collectors.toList()));
  }
}
