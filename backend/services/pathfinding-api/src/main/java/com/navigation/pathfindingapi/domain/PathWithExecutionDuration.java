package com.navigation.pathfindingapi.domain;

import com.navigation.pathfinder.pathfinding.PathSummary;

import java.time.Duration;

public class PathWithExecutionDuration {

    private final PathSummary pathSummary;
    private final Duration executionDuration;

    public PathWithExecutionDuration(PathSummary pathSummary, Duration executionDuration) {
        this.pathSummary = pathSummary;
        this.executionDuration = executionDuration;
    }

    public PathSummary getPath() {
        return pathSummary;
    }

    public Duration getExecutionDuration() {
        return executionDuration;
    }
}
