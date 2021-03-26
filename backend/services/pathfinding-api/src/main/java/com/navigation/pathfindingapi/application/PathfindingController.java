package com.navigation.pathfindingapi.application;

import com.navigation.pathfindingapi.application.dto.PathRequestDtoParams;
import com.navigation.pathfindingapi.application.dto.PathResponseDto;
import com.navigation.pathfindingapi.domain.MapLocation;
import com.navigation.pathfindingapi.domain.PathfindingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PathfindingController {

  private final PathfindingService service;
  private final PathfindingMapper mapper;

  public PathfindingController(PathfindingService service, PathfindingMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @GetMapping("/find-path")
  public ResponseEntity<PathResponseDto> findPath(PathRequestDtoParams requestDtoParams) {
    var start = new MapLocation(requestDtoParams.getStartLatitude(), requestDtoParams.getStartLongitude());
    var end = new MapLocation(requestDtoParams.getEndLatitude(), requestDtoParams.getEndLongitude());
    var path = service.getClosestPathBetween(start, end);
    return ResponseEntity.ok(mapper.toResponse(path));
  }
}
