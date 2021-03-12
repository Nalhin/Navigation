package com.navigation.parser.loader;

import com.navigation.parser.elements.*;
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
  private final static String BOUNDS_ELEMENT = "bounds";
  private final static String METADATA_ELEMENT = "osm";
  private final static String RELATION_ELEMENT = "relation";

  private final static String REF_NESTED_ELEMENT = "nd";
  private final static String TAG_NESTED_ELEMENT = "tag";
  private final static String MEMBER_NESTED_ELEMENT = "member";

  private final OSMProvider provider;
  private final OSMExporter exporter;
  private final OSMLoaderSpecification specification;

  public OSMLoader(OSMProvider provider, OSMExporter exporter) {
    this(provider, exporter, new OSMLoadAllSpecification());
  }

  public OSMLoader(OSMProvider provider, OSMExporter exporter, OSMLoaderSpecification specification) {
    this.provider = provider;
    this.exporter = exporter;
    this.specification = specification;
  }

  public void loadOSM() throws IOException, XMLStreamException {
    var reader = provider.loadOSMXml();

    while (reader.hasNext()) {
      reader.next();
      if (reader.isStartElement()) {
        switch (reader.getLocalName()) {
          case WAY_ELEMENT -> {
            var way = loadWay(reader);
            if (specification.isSatisfiedBy(way)) {
              exporter.export(way);
            }
          }
          case NODE_ELEMENT -> {
            var node = loadNode(reader);
            if (specification.isSatisfiedBy(node)) {
              exporter.export(node);
            }
          }
          case BOUNDS_ELEMENT -> {
            var bounds = loadBounds(reader);
            if (specification.isSatisfiedBy(bounds)) {
              exporter.export(bounds);
            }
          }
          case METADATA_ELEMENT -> exporter.export(loadMetadata(reader));
          case RELATION_ELEMENT -> {
            var relation = loadRelation(reader);
            if (specification.isSatisfiedBy(relation)) {
              exporter.export(relation);
            }
          }
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

  private Bounds loadBounds(XMLStreamReader reader) {
    var minLatitude = reader.getAttributeValue(null, "minlat");
    var maxLatitude = reader.getAttributeValue(null, "maxlat");
    var minLongitude = reader.getAttributeValue(null, "minlon");
    var maxLongitude = reader.getAttributeValue(null, "maxlon");

    return new Bounds(minLatitude, maxLatitude, minLongitude, maxLongitude);
  }

  private Metadata loadMetadata(XMLStreamReader reader) {
    return new Metadata(reader.getAttributeValue(null, "version"), reader.getAttributeValue(null, "generator"));
  }

  private Relation loadRelation(XMLStreamReader reader) throws XMLStreamException {
    var id = reader.getAttributeValue(null, "id");
    var nested = loadNestedElements(reader, RELATION_ELEMENT);
    return new Relation(id, nested.members, nested.tags);
  }

  private NestedElements loadNestedElements(XMLStreamReader reader, String endElement) throws XMLStreamException {
    var result = new NestedElements();

    while (!reader.isEndElement() || !reader.getLocalName().equals(endElement)) {
      if (reader.isStartElement()) {
        switch (reader.getLocalName()) {
          case TAG_NESTED_ELEMENT -> result.addTag(new Tag(reader.getAttributeValue(null, "k"), reader.getAttributeValue(null, "v")));
          case REF_NESTED_ELEMENT -> result.addRef(reader.getAttributeValue(null, "ref"));
          case MEMBER_NESTED_ELEMENT -> result.addMember(new Member(reader.getAttributeValue(null, "type"), reader.getAttributeValue(null, "ref"), reader.getAttributeValue(null, "role")));
        }
      }
      reader.nextTag();
    }

    return result;
  }

  private static class NestedElements {
    private final List<Tag> tags = new ArrayList<>();
    private final List<String> refs = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();

    public void addTag(Tag tag) {
      tags.add(tag);
    }

    public void addRef(String ref) {
      refs.add(ref);
    }

    public void addMember(Member member) {
      members.add(member);
    }
  }
}
