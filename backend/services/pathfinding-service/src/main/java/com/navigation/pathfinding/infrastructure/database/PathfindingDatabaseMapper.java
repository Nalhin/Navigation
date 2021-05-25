package com.navigation.pathfinding.infrastructure.database;

import com.navigation.pathfinder.graph.Coordinates;
import com.navigation.pathfinder.graph.Graph;
import com.navigation.pathfinder.graph.GraphBuilder;
import com.navigation.pathfinder.graph.Vertex;
import com.navigation.pathfinding.application.PathBetweenCoordinatesUseCase.BoundsQuery;
import java.util.List;
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

  public Graph buildGraph(
          List<StreetNodeEntity> streetNodes, List<StreetConnectionEntity> connections) {

    var builder = new GraphBuilder();
    streetNodes.forEach(
            streetNode -> builder.addVertex(toVertex(streetNode)));
    connections.forEach(
            connection ->
                    builder.connectByIds(
                            connection.getFromId(), connection.getToId(), connection.getMaxSpeed()));

    return builder.asGraph();
  }
}
