package com.navigation.pathfinding.infrastructure.database;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinding.application.PathBetweenCoordinatesUseCase.BoundsQuery;
import org.springframework.data.geo.Box;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Component;

@Component
class PathfindingDatabaseMapper {

  public Coordinates toCoordinates(GeoJsonPoint point) {
    return new Coordinates(point.getY(), point.getX());
  }

  public Vertex toVertex(StreetNodeEntity entity) {
    return new Vertex(entity.getId(), toCoordinates(entity.getLocation()));
  }

  public GeoJsonPoint toGeoJsonPoint(Coordinates coordinates) {
    return new GeoJsonPoint(coordinates.getLongitude(), coordinates.getLatitude());
  }

  public Box toBox(BoundsQuery bounds) {
    return new Box(toGeoJsonPoint(bounds.getLeftBottom()), toGeoJsonPoint(bounds.getTopRight()));
  }
}
