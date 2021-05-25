package com.navigation.pathfinding.ui;

import com.navigation.pathfinding.application.PathBetweenCoordinatesUseCase.PathBetweenCoordinatesErrors;
import com.navigation.pathfinding.ui.dto.response.ErrorResponse;
import com.navigation.pathfinding.ui.dto.response.PathResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PathfindingRestResponseMapper {

  public ResponseEntity<Object> mapToSuccess(PathResponseDto pathResponseDto){
    return ResponseEntity.ok(pathResponseDto);
  }

  public ResponseEntity<Object> mapToError(PathBetweenCoordinatesErrors errors){
    return switch (errors){
      case END_NOT_FOUND -> new ResponseEntity<>(new ErrorResponse("End coordinates are too far from any street node."), HttpStatus.NOT_FOUND);
      case START_NOT_FOUND -> new ResponseEntity<>(new ErrorResponse("Start coordinates are too far from any street node."), HttpStatus.NOT_FOUND);
      case START_AND_END_NOT_FOUND ->  new ResponseEntity<>(new ErrorResponse("Start and end coordinates are too far from any street node."), HttpStatus.NOT_FOUND);
      case START_AND_END_FETCH_ERROR -> new ResponseEntity<>(new ErrorResponse("Start and end fetch error."), HttpStatus.INTERNAL_SERVER_ERROR);
      case OPTIMIZATION_NOT_SUPPORTED -> new ResponseEntity<>(new ErrorResponse("Optimization is not supported"), HttpStatus.BAD_REQUEST);
    };
  }
}
