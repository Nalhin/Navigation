package com.navigation.parser.loader;

import com.navigation.parser.elements.Node;
import com.navigation.parser.elements.Tag;
import com.navigation.parser.elements.Way;
import com.navigation.parser.exporter.OSMExporter;
import com.navigation.parser.provider.OSMProvider;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OSMLoader {

    private final String WAY_ELEMENT = "way";
    private final String NODE_ELEMENT = "node";
    private final String REF_ELEMENT = "nd";
    private final String TAG_ELEMENT = "tag";
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
        reader.nextTag();
        var refs = loadRefs(reader);
        var tags = loadTags(reader);
        return new Way(id, refs, tags);
    }

    private List<String> loadRefs(XMLStreamReader reader) throws XMLStreamException {
        List<String> refs = new ArrayList<>();

        while (reader.getLocalName().equals(REF_ELEMENT)) {
            if (reader.getEventType() == XMLStreamReader.START_ELEMENT) {
                refs.add(reader.getAttributeValue(null, "ref"));
            }
            reader.nextTag();
        }

        return refs;
    }

    private Node loadNode(XMLStreamReader reader) throws XMLStreamException {
        var id = reader.getAttributeValue(null, "id");
        var lat = reader.getAttributeValue(null, "lat");
        var lon = reader.getAttributeValue(null, "lon");
        reader.nextTag();
        var tags = loadTags(reader);

        return new Node(id, lat, lon, tags);
    }

    private List<Tag> loadTags(XMLStreamReader reader) throws XMLStreamException {
        List<Tag> tags = new ArrayList<>();

        while (reader.getLocalName().equals(TAG_ELEMENT)) {
            if (reader.getEventType() == XMLStreamReader.START_ELEMENT) {
                tags.add(new Tag(reader.getAttributeValue(null, "k"), reader.getAttributeValue(null, "v")));
            }
            reader.nextTag();
        }

        return tags;
    }
}
