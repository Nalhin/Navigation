package com.navigation.pathfinding.ui;

import com.navigation.pathfinding.ui.dto.params.BoundsRequestDtoParams;
import com.navigation.pathfinding.ui.dto.params.PathRequestDtoParams;
import com.navigation.pathfinding.ui.dto.response.ErrorResponseDto;
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
  private final PathfindingApiMapper apiMapper;
  private final PathfindingResponseMapper responseMapper;

  public PathfindingController(
          PathBetweenCoordinatesUseCase pathBetweenUseCase,
          AvailableOptimizationsUseCase availableOptimizationsUseCase,
          PathfindingApiMapper apiMapper,
          PathfindingResponseMapper responseMapper) {
    this.pathBetweenUseCase = pathBetweenUseCase;
    this.availableOptimizationsUseCase = availableOptimizationsUseCase;
    this.apiMapper = apiMapper;
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
      content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Points not found",
      content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  @GetMapping(path = "/path-between", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findPath(@Validated PathRequestDtoParams requestDtoParams) {
    var path = pathBetweenUseCase.calculatePathBetween(apiMapper.toQuery(requestDtoParams));

    return path.map((result) -> responseMapper.mapToSuccess(apiMapper.toDto(result)))
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
      content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Points not found",
      content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  @GetMapping(path = "/path-between/bounded", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> findPath(
      @Validated PathRequestDtoParams requestDtoParams,
      @Validated BoundsRequestDtoParams boundsRequestDtoParams) {
    var path =
        pathBetweenUseCase.calculateBoundedPathBetween(
            apiMapper.toQuery(requestDtoParams), apiMapper.toQuery(boundsRequestDtoParams));

    return path.map((result) -> responseMapper.mapToSuccess(apiMapper.toDto(result)))
        .getOrElseGet(responseMapper::mapToError);
  }

  @Operation(
      tags = "pathfinding",
      description = "Return optimizations supported by the pathfinding algorithm")
  @ApiResponse(responseCode = "200", description = "Success")
  @GetMapping(path = "/algorithms/{algorithm}/supported-optimizations")
  public ResponseEntity<List<PathfindingOptimizationsDto>> getAlgorithmOptimizations(
      @PathVariable PathfindingAlgorithmsDto algorithm) {
    var availableOptimizations =
        availableOptimizationsUseCase.availableOptimizations(apiMapper.toDomain(algorithm));

    return ResponseEntity.ok(
        availableOptimizations.stream().map(apiMapper::toDto).collect(Collectors.toList()));
  }
}
