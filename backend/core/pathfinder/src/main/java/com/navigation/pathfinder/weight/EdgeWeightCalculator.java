package com.navigation.pathfinder.weight;

import com.navigation.pathfinder.graph.GraphEdge;

public interface EdgeWeightCalculator {

  double calculateWeight(GraphEdge edge);

}
