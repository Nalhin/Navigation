package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Vertex;

final class ScoredGraphVertex implements Comparable<ScoredGraphVertex> {
    private final Vertex vertex;
    private final double score;

    ScoredGraphVertex(Vertex vertex, double score) {
        this.vertex = vertex;
        this.score = score;
    }

    Vertex getVertex() {
        return vertex;
    }

    double getScore() {
        return score;
    }

    @Override
    public int compareTo(ScoredGraphVertex scoredGraphVertex) {
        return Double.compare(score, scoredGraphVertex.score);
    }
}
