package com.navigation.parser.loader;

import com.navigation.parser.elements.Node;
import com.navigation.parser.elements.Tag;
import com.navigation.parser.elements.Way;
import com.navigation.parser.exporter.OSMExporter;
import com.navigation.parser.provider.OSMProvider;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.util.Collections;
import java.util.List;

public class OSMLoader {

    private final String WAY_ELEMENT = "way";
    private final String NODE_ELEMENT = "node";
    private final OSMProvider provider;
    private final OSMExporter exporter;

    public OSMLoader(OSMProvider provider, OSMExporter exporter) {
        this.provider = provider;
        this.exporter = exporter;
    }

    public void loadOSM() throws IOException, XMLStreamException {
        var reader = provider.loadOSMXml();

        while (reader.hasNext()) {
            reader.next();
            if (reader.getEventType() == XMLStreamReader.START_ELEMENT) {
                switch (reader.getLocalName()) {
                    case WAY_ELEMENT -> exporter.export(loadWay(reader));
                    case NODE_ELEMENT -> exporter.export(loadNode(reader));
                }
            }
        }
        reader.close();
    }

    private Way loadWay(XMLStreamReader reader) throws XMLStreamException {
        var id = reader.getAttributeValue(null, "id");

        while (reader.next() != XMLStreamConstants.END_ELEMENT || !reader.getLocalName().equals(WAY_ELEMENT));

        return new Way(id, Collections.emptyList(), Collections.emptyList());
    }

    private Node loadNode(XMLStreamReader reader) throws XMLStreamException {
        var id = reader.getAttributeValue(null, "id");
        var lat = reader.getAttributeValue(null, "lat");
        var lon = reader.getAttributeValue(null, "lon");

        if (reader.next() == XMLStreamReader.END_ELEMENT) {
            return new Node(id, lat, lon, Collections.emptyList());
        }

        var tags = loadTags(reader);

        return new Node(id, lat, lon, tags);
    }

    private List<Tag> loadTags(XMLStreamReader reader) {
        return Collections.emptyList();
    }
}
