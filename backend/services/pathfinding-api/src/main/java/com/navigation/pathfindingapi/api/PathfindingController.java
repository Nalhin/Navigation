package com.navigation.pathfindingapi.api;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfindingapi.api.dto.PathRequestDtoParams;
import com.navigation.pathfindingapi.api.dto.PathResponseDto;
import com.navigation.pathfindingapi.domain.PathfindingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pathfinding")
public class PathfindingController {

  private final PathfindingService service;
  private final ResponseMapper mapper;

  public PathfindingController(PathfindingService service, ResponseMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @GetMapping("/path-between")
  public ResponseEntity<PathResponseDto> findPath(PathRequestDtoParams requestDtoParams) {
    var start = new Coordinates(requestDtoParams.getStartLatitude(), requestDtoParams.getStartLongitude());
    var end = new Coordinates(requestDtoParams.getEndLatitude(), requestDtoParams.getEndLongitude());
    var path = service.getClosestPathBetween(start, end);
    return ResponseEntity.ok(mapper.toResponse(path));
  }
}
