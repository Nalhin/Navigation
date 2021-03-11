package com.navigation.parser.exporter;

import com.navigation.parser.elements.Node;
import com.navigation.parser.elements.Way;

import java.util.HashMap;
import java.util.Map;

public class OSMExporterInMemory implements OSMExporter {

    private final Map<String, Node> nodes = new HashMap<>();
    private final Map<String, Way> ways = new HashMap<>();


    @Override
    public void export(Node node) {
        nodes.put(node.getId(), node);
    }

    @Override
    public void export(Way way) {
        ways.put(way.getId(), way);
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }

    public Map<String, Way> getWays() {
        return ways;
    }
}
