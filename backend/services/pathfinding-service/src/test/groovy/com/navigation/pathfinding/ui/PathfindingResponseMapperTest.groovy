package com.navigation.pathfinding.ui

import com.navigation.pathfinding.application.PathBetweenCoordinatesUseCase.PathBetweenCoordinatesErrors
import org.springframework.http.HttpStatus
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class PathfindingResponseMapperTest extends Specification {

  @Subject
  @Shared
  def mapper = new PathfindingResponseMapper()

  def "mapToError() should map #error error to #expectedStatus status"(
      PathBetweenCoordinatesErrors error, HttpStatus expectedStatus) {
    when:
    def result = mapper.mapToError(error)
    then:
    result.statusCode == expectedStatus
    where:
    error                                                   || expectedStatus
    PathBetweenCoordinatesErrors.END_NOT_FOUND              || HttpStatus.NOT_FOUND
    PathBetweenCoordinatesErrors.OPTIMIZATION_NOT_SUPPORTED || HttpStatus.BAD_REQUEST
    PathBetweenCoordinatesErrors.START_AND_END_FETCH_ERROR  || HttpStatus.INTERNAL_SERVER_ERROR
    PathBetweenCoordinatesErrors.START_NOT_FOUND            || HttpStatus.NOT_FOUND
    PathBetweenCoordinatesErrors.START_AND_END_NOT_FOUND    || HttpStatus.NOT_FOUND
  }
}
