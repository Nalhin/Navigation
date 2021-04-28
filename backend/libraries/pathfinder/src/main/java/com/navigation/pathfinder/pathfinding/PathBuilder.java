package com.navigation.pathfinder.pathfinding;

import com.navigation.pathfinder.graph.Path;
import com.navigation.pathfinder.graph.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

class PathBuilder {

    public Path buildPath(Map<Vertex, Vertex> predecessorTree, Vertex last, Vertex start) {
        var result = new ArrayList<Vertex>();

        var currNode = last;
        while (currNode != null && currNode != start) {
            result.add(currNode);
            currNode = predecessorTree.get(currNode);
        }
        if(currNode != start){
            return new Path(Collections.emptyList());
        }
        result.add(start);
        Collections.reverse(result);

        return new Path(result);
    }

}
