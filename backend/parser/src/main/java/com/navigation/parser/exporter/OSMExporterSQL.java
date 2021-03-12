package com.navigation.parser.exporter;

import com.navigation.parser.elements.Bounds;
import com.navigation.parser.elements.Metadata;
import com.navigation.parser.elements.Node;
import com.navigation.parser.elements.Way;

public class OSMExporterSQL implements OSMExporter {

    private final String outputFilePath;

    public OSMExporterSQL(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    @Override
    public void export(Node node) {

    }

    @Override
    public void export(Way way) {

    }

    @Override
    public void export(Bounds bounds) {

    }

    @Override
    public void export(Metadata metadata) {

    }
}
