package com.navigation.parser.exporter;

import com.navigation.parser.elements.Bounds;
import com.navigation.parser.elements.Metadata;
import com.navigation.parser.elements.Node;
import com.navigation.parser.elements.Way;

public interface OSMExporter {

    void export(Node node);
    void export(Way way);
    void export(Bounds bounds);
    void export(Metadata metadata);
}
