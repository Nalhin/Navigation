package com.navigation.pathfinding.application;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinding.application.PathBetweenCoordinatesUseCase.BoundsQuery;
import io.vavr.control.Option;

public interface GraphRepository {

  Option<Vertex> closestVertex(Coordinates location, double distanceThresholdInKm);

  Graph prepareGraph();

  Graph prepareGraphWithinBounds(BoundsQuery bounds);
}
