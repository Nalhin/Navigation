package com.navigation.pathfinder.convexhull;

import com.navigation.pathfinder.graph.Vertex;

import java.util.Collection;
import java.util.List;

public interface ConvexHullCalculator {

    List<Vertex> calculateConvexHull(Collection<Vertex> vertices);
}
