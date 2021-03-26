package com.navigation.pathfindingapi.infrastructure;

import com.navigation.parser.elements.Node;
import com.navigation.parser.elements.Way;
import com.navigation.parser.exporter.ExportedOSM;
import com.navigation.pathfindingapi.domain.MapConnection;
import com.navigation.pathfindingapi.domain.MapLocation;
import com.navigation.pathfindingapi.domain.MapNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
class OsmMapper {

  LoadedMap exportedOSMToLoadedMap(ExportedOSM exportedData) {

    var nodes = exportedData.getNodes().values().stream()
        .collect(Collectors.toMap(node -> node.getId(), this::nodeToMapNode));
    var connections = exportedData.getWays().values().stream()
        .flatMap(way -> wayToMapConnection(way, nodes).stream()).collect(Collectors.toList());
    return new LoadedMap(new ArrayList<>(nodes.values()), connections);
  }

  private List<MapConnection> wayToMapConnection(Way way, Map<Long, MapNode> nodes) {
    var nodesAlongWay = way.getNodeReferences();
    var prev = nodesAlongWay.get(0);

    List<MapConnection> connections = new ArrayList<>();

    for (int i = 1; i < nodesAlongWay.size(); i++) {
      var curr = nodesAlongWay.get(i);
      connections.add(new MapConnection(nodes.get(prev), nodes.get(curr)));
      prev = curr;
    }

    return connections;
  }

  private MapNode nodeToMapNode(Node node) {
    return new MapNode(new MapLocation(node.getLatitude(), node.getLongitude()), node.getId());
  }
}
