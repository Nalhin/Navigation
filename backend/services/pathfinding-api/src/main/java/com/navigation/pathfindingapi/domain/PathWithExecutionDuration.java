package com.navigation.pathfindingapi.domain;

import com.navigation.pathfinder.graph.Path;

import java.time.Duration;

public class PathWithExecutionDuration {

    private final Path path;
    private final Duration executionDuration;

    public PathWithExecutionDuration(Path path, Duration executionDuration) {
        this.path = path;
        this.executionDuration = executionDuration;
    }

    public Path getPath() {
        return path;
    }

    public Duration getExecutionDuration() {
        return executionDuration;
    }
}
