package com.navigation.pathfinding.application;

import static io.vavr.API.For;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinding.domain.PathfindingExecutionSummary;
import com.navigation.pathfinding.domain.Pathfinders;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.time.Clock;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;

@Service
public class PathBetweenCoordinatesService implements PathBetweenCoordinatesUseCase {

  private static final double NODE_DISTANCE_THRESHOLD_IN_KM = 5;

  private final GraphRepository graphRepository;
  private final Pathfinders pathfinders;
  private final Clock clock;

  public PathBetweenCoordinatesService(
      GraphRepository graphRepository, Pathfinders pathfinders, Clock clock) {
    this.graphRepository = graphRepository;
    this.pathfinders = pathfinders;
    this.clock = clock;
  }

  @Override
  public Either<PathBetweenCoordinatesErrors, PathfindingExecutionSummary> calculatePathBetween(
      PathBetweenCoordinatesQuery query) {
    return findPathBetweenPoints(query, graphRepository::prepareGraph);
  }

  @Override
  public Either<PathBetweenCoordinatesErrors, PathfindingExecutionSummary>
      calculateBoundedPathBetween(PathBetweenCoordinatesQuery query, BoundsQuery bounds) {
    return findPathBetweenPoints(query, () -> graphRepository.prepareGraphWithinBounds(bounds));
  }

  private Either<PathBetweenCoordinatesErrors, PathfindingExecutionSummary> findPathBetweenPoints(
      PathBetweenCoordinatesQuery query, Supplier<Graph> graphSupplier) {

    var startAndEndVertices = findStartAndEndVertices(query.getStart(), query.getEnd());
    var pathfindingStrategy =
        pathfinders.selectPathfinder(
            query.getPathfindingAlgorithm(), query.getPathfindingOptimization());

    var executionStartTime = clock.instant();

    return pathfindingStrategy
        .toEither(PathBetweenCoordinatesErrors.OPTIMIZATION_NOT_SUPPORTED)
        .flatMap(
            strategy ->
                startAndEndVertices.map(
                    vertices -> strategy.findPath(vertices._1, vertices._2, graphSupplier.get())))
        .map(
            path ->
                new PathfindingExecutionSummary(
                    path,
                    executionStartTime,
                    clock.instant(),
                    query.getPathfindingAlgorithm(),
                    query.getPathfindingOptimization()));
  }

  private Either<PathBetweenCoordinatesErrors, Tuple2<Vertex, Vertex>> findStartAndEndVertices(
      Coordinates start, Coordinates end) {

    var startFuture =
        Future.of(() -> graphRepository.closestVertex(start, NODE_DISTANCE_THRESHOLD_IN_KM));
    var endFuture =
        Future.of(() -> graphRepository.closestVertex(end, NODE_DISTANCE_THRESHOLD_IN_KM));

    return For(startFuture, endFuture)
        .yield(Tuple::of)
        .await()
        .toEither(PathBetweenCoordinatesErrors.START_AND_END_FETCH_ERROR)
        .flatMap((tuple) -> transformStartEndErrors(tuple._1, tuple._2));
  }

  private Either<PathBetweenCoordinatesErrors, Tuple2<Vertex, Vertex>> transformStartEndErrors(
      Option<Vertex> start, Option<Vertex> end) {
    if (start.isEmpty() && end.isEmpty()) {
      return Either.left(PathBetweenCoordinatesErrors.START_AND_END_NOT_FOUND);
    }
    if (start.isEmpty()) {
      return Either.left(PathBetweenCoordinatesErrors.START_NOT_FOUND);
    }
    if (end.isEmpty()) {
      return Either.left(PathBetweenCoordinatesErrors.END_NOT_FOUND);
    }
    return Either.right(Tuple.of(start.get(), end.get()));
  }
}
