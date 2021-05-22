package com.navigation.pathfinder.pathfinding

import com.navigation.pathfinder.graph.Edge
import com.navigation.pathfinder.graph.Vertex
import com.navigation.pathfinder.weight.EdgeWeightCalculator

class EuclideanDistanceTestEdgeWeightCalculator implements
    EdgeWeightCalculator {

  @Override
  double calculateWeight(Edge edge) {
    def fromCoords = edge.from.coordinates
    def toCoords = edge.to.coordinates
    return Math.sqrt(Math.pow(fromCoords.latitude - toCoords.latitude, 2) +
        Math.pow(fromCoords.longitude - toCoords.longitude, 2))
  }

  @Override
  double estimateWeight(Vertex start, Vertex end) {
    return calculateWeight(new Edge(start, end, 0))
  }
}