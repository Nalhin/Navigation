package com.navigation.pathfinding.application;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinding.domain.PathWithExecutionSummary;
import com.navigation.pathfinding.domain.PathfindingAlgorithms;
import com.navigation.pathfinding.domain.PathfindingOptimizations;
import io.vavr.control.Either;
import java.util.Objects;

public interface PathBetweenCoordinatesUseCase {

  Either<PathBetweenCoordinatesErrors, PathWithExecutionSummary> calculatePathBetween(
      PathBetweenCoordinatesQuery query);

  Either<PathBetweenCoordinatesErrors, PathWithExecutionSummary> calculateBoundedPathBetween(
      PathBetweenCoordinatesQuery query, BoundsQuery bounds);

  enum PathBetweenCoordinatesErrors {
    START_NOT_FOUND,
    END_NOT_FOUND,
    START_AND_END_NOT_FOUND,
    START_AND_END_FETCH_ERROR,
    OPTIMIZATION_NOT_SUPPORTED,
  }

  final class PathBetweenCoordinatesQuery {

    private final Coordinates start;
    private final Coordinates end;

    private final PathfindingAlgorithms pathfindingAlgorithm;
    private final PathfindingOptimizations pathfindingOptimization;

    public PathBetweenCoordinatesQuery(
        Coordinates start,
        Coordinates end,
        PathfindingAlgorithms pathfindingAlgorithm,
        PathfindingOptimizations pathfindingOptimization) {
      this.start = start;
      this.end = end;
      this.pathfindingAlgorithm = pathfindingAlgorithm;
      this.pathfindingOptimization = pathfindingOptimization;
    }

    public Coordinates getStart() {
      return start;
    }

    public Coordinates getEnd() {
      return end;
    }

    public PathfindingAlgorithms getPathfindingAlgorithm() {
      return pathfindingAlgorithm;
    }

    public PathfindingOptimizations getPathfindingOptimization() {
      return pathfindingOptimization;
    }
  }

  final class BoundsQuery {
    private final Coordinates leftBottom;
    private final Coordinates topRight;

    public BoundsQuery(Coordinates leftBottom, Coordinates topRight) {
      this.leftBottom = leftBottom;
      this.topRight = topRight;
    }

    public Coordinates getLeftBottom() {
      return leftBottom;
    }

    public Coordinates getTopRight() {
      return topRight;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      BoundsQuery that = (BoundsQuery) o;
      return Objects.equals(leftBottom, that.leftBottom) && Objects.equals(topRight, that.topRight);
    }

    @Override
    public int hashCode() {
      return Objects.hash(leftBottom, topRight);
    }
  }
}
