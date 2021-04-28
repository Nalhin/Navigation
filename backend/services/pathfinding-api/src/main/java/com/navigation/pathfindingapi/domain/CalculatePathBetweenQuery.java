package com.navigation.pathfindingapi.domain;

import com.navigation.pathfinder.graph.Coordinates;

public class CalculatePathBetweenQuery {

    private final Coordinates start;
    private final Coordinates end;

    private final PathfindingStrategyFactory.Algorithms algorithm;
    private final PathfindingStrategyFactory.Optimizations optimizations;

    public CalculatePathBetweenQuery(Coordinates start, Coordinates end, PathfindingStrategyFactory.Algorithms algorithm, PathfindingStrategyFactory.Optimizations optimizations) {
        this.start = start;
        this.end = end;
        this.algorithm = algorithm;
        this.optimizations = optimizations;
    }

    public Coordinates getStart() {
        return start;
    }

    public Coordinates getEnd() {
        return end;
    }

    public PathfindingStrategyFactory.Algorithms getAlgorithm() {
        return algorithm;
    }

    public PathfindingStrategyFactory.Optimizations getOptimizations() {
        return optimizations;
    }
}
