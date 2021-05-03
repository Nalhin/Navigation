package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Vertex;

import java.util.Collection;
import java.util.List;

public interface PathSummary {

  List<Vertex> getSimplePath();

  int numberOfVertices();

  int totalVisitedVertices();

  double totalDistance();

  double totalDuration();

  Collection<List<Vertex>> searchBoundaries();

  boolean isFound();
}
