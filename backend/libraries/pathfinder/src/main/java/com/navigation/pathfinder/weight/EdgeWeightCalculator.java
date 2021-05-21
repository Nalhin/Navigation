package com.navigation.pathfinder.weight;

import com.navigation.pathfinder.graph.Edge;
import com.navigation.pathfinder.graph.Vertex;

public interface EdgeWeightCalculator {

  double calculateWeight(Edge edge);

  double estimateWeight(Vertex start, Vertex end);
}
