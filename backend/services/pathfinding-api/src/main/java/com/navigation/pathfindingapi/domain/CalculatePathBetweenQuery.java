package com.navigation.pathfindingapi.domain;

import com.navigation.pathfinder.graph.Coordinates;

public class CalculatePathBetweenQuery {

    private final Coordinates start;
    private final Coordinates end;

    private final PathfindingAlgorithms algorithm;
    private final PathfindingOptimizations pathfindingOptimizations;

    public CalculatePathBetweenQuery(Coordinates start, Coordinates end, PathfindingAlgorithms algorithm, PathfindingOptimizations pathfindingOptimizations) {
        this.start = start;
        this.end = end;
        this.algorithm = algorithm;
        this.pathfindingOptimizations = pathfindingOptimizations;
    }

    public Coordinates getStart() {
        return start;
    }

    public Coordinates getEnd() {
        return end;
    }

    public PathfindingAlgorithms getAlgorithm() {
        return algorithm;
    }

    public PathfindingOptimizations getOptimizations() {
        return pathfindingOptimizations;
    }
}
