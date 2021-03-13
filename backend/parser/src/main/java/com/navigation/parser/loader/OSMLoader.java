package com.navigation.parser.loader;

import com.navigation.parser.elements.*;
import com.navigation.parser.exporter.OSMExporter;
import com.navigation.parser.loader.elements.Elements;
import com.navigation.parser.loader.elements.NestedElements;
import com.navigation.parser.loader.specification.OSMLoadAllSpecification;
import com.navigation.parser.loader.specification.OSMLoaderSpecification;
import com.navigation.parser.provider.OSMProvider;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OSMLoader {

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
        switch (Elements.fromTag(reader.getLocalName())) {
          case WAY -> {
            var way = loadWay(reader);
            if (specification.isSatisfiedBy(way)) {
              exporter.export(way);
            }
          }
          case NODE -> {
            var node = loadNode(reader);
            if (specification.isSatisfiedBy(node)) {
              exporter.export(node);
            }
          }
          case BOUNDS -> {
            var bounds = loadBounds(reader);
            if (specification.isSatisfiedBy(bounds)) {
              exporter.export(bounds);
            }
          }
          case METADATA -> exporter.export(loadMetadata(reader));
          case RELATION -> {
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
    var nested = loadNestedElements(reader, Elements.WAY);

    return new Way(id, nested.refs, nested.tags);
  }


  private Node loadNode(XMLStreamReader reader) throws XMLStreamException {
    var id = reader.getAttributeValue(null, "id");
    var lat = reader.getAttributeValue(null, "lat");
    var lon = reader.getAttributeValue(null, "lon");
    var nested = loadNestedElements(reader, Elements.NODE);

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
    var nested = loadNestedElements(reader, Elements.RELATION);
    return new Relation(id, nested.members, nested.tags);
  }

  private NestedElementsReturn loadNestedElements(XMLStreamReader reader, Elements endElement) throws XMLStreamException {
    var tags = new ArrayList<Tag>();
    var refs = new ArrayList<String>();
    var members = new ArrayList<Member>();

    reader.nextTag();

    while (!reader.isEndElement() || !reader.getLocalName().equals(endElement.TAG_VALUE)) {
      if (reader.isStartElement()) {
        switch (NestedElements.fromTag(reader.getLocalName())) {
          case TAG -> tags.add(new Tag(reader.getAttributeValue(null, "k"), reader.getAttributeValue(null, "v")));
          case REF -> refs.add(reader.getAttributeValue(null, "ref"));
          case MEMBER -> members.add(new Member(reader.getAttributeValue(null, "type"), reader.getAttributeValue(null, "ref"), reader.getAttributeValue(null, "role")));
        }
      }
      reader.nextTag();
    }

    return new NestedElementsReturn(tags, refs, members);
  }

  private static class NestedElementsReturn {
    private final List<Tag> tags;
    private final List<String> refs;
    private final List<Member> members;

    public NestedElementsReturn(List<Tag> tags, List<String> refs, List<Member> members) {
      this.tags = tags;
      this.refs = refs;
      this.members = members;
    }
  }
}
