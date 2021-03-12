package com.navigation.parser.exporter;

import com.navigation.parser.elements.Bounds;
import com.navigation.parser.elements.Metadata;
import com.navigation.parser.elements.Node;
import com.navigation.parser.elements.Way;

import java.util.HashMap;
import java.util.Map;

public class OSMExporterInMemory implements OSMExporter {

    private final Map<String, Node> nodes = new HashMap<>();
    private final Map<String, Way> ways = new HashMap<>();
    private Bounds bounds;
    private Metadata metadata;

    @Override
    public void export(Node node) {
        nodes.put(node.getId(), node);
    }

    @Override
    public void export(Way way) {
        ways.put(way.getId(), way);
    }

    @Override
    public void export(Bounds bounds) {
        this.bounds = bounds;
    }

    @Override
    public void export(Metadata metadata) {
        this.metadata = metadata;
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }

    public Map<String, Way> getWays() {
        return ways;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public Metadata getMetadata() {
        return metadata;
    }
}
