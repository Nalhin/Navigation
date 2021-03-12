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
import java.util.List;

public class OSMLoader {

    private final static String WAY_ELEMENT = "way";
    private final static String NODE_ELEMENT = "node";

    private final static String REF_NESTED_ELEMENT = "nd";
    private final static String TAG_NESTED_ELEMENT = "tag";

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
        var nested = loadNestedElements(reader, WAY_ELEMENT);

        return new Way(id, nested.refs, nested.tags);
    }


    private Node loadNode(XMLStreamReader reader) throws XMLStreamException {
        var id = reader.getAttributeValue(null, "id");
        var lat = reader.getAttributeValue(null, "lat");
        var lon = reader.getAttributeValue(null, "lon");
        var nested = loadNestedElements(reader, NODE_ELEMENT);

        return new Node(id, lat, lon, nested.tags);
    }

    private NestedElements loadNestedElements(XMLStreamReader reader, String endElement) throws XMLStreamException {
        var result = new NestedElements();

        while (reader.getEventType() != XMLStreamReader.END_ELEMENT || !reader.getLocalName().equals(endElement)) {
            if (reader.getEventType() == XMLStreamReader.START_ELEMENT) {
                switch (reader.getLocalName()) {
                    case TAG_NESTED_ELEMENT -> result.addTag(new Tag(reader.getAttributeValue(null, "k"), reader.getAttributeValue(null, "v")));
                    case REF_NESTED_ELEMENT -> result.addRef(reader.getAttributeValue(null, "ref"));
                }
            }
            reader.nextTag();
        }

        return result;
    }

    private static class NestedElements {
        private final List<Tag> tags = new ArrayList<>();
        private final List<String> refs = new ArrayList<>();

        public void addTag(Tag tag) {
            tags.add(tag);
        }

        public void addRef(String ref) {
            refs.add(ref);
        }
    }
}
