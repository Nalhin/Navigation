package com.navigation.pathfindingapi.api;

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
  private final ApiMapper mapper;

  public PathfindingController(PathfindingService service, ApiMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @GetMapping("/path-between")
  public ResponseEntity<PathResponseDto> findPath(PathRequestDtoParams requestDtoParams) {
    var path = service.calculatePathBetween(mapper.toQuery(requestDtoParams));
    return ResponseEntity.ok(mapper.toResponse(path));
  }
}
