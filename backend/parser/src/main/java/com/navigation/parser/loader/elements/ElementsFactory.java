package com.navigation.parser.loader.elements;

import com.navigation.parser.elements.*;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElementsFactory {

  public Way loadWay(XMLStreamReader reader) throws XMLStreamException {
    var id = Long.parseLong(reader.getAttributeValue(null, "id"));
    var nested = loadNestedElements(reader, Elements.WAY);

    return new Way(id, nested.tags, nested.refs);
  }

  public Node loadNode(XMLStreamReader reader) throws XMLStreamException {
    var id = Long.parseLong(reader.getAttributeValue(null, "id"));
    var lat = Double.parseDouble(reader.getAttributeValue(null, "lat"));
    var lon = Double.parseDouble(reader.getAttributeValue(null, "lon"));
    var nested = loadNestedElements(reader, Elements.NODE);

    return new Node(id, nested.tags, lat, lon);
  }

  public Bounds loadBounds(XMLStreamReader reader) {
    var minLatitude = Double.parseDouble(reader.getAttributeValue(null, "minlat"));
    var maxLatitude = Double.parseDouble(reader.getAttributeValue(null, "maxlat"));
    var minLongitude = Double.parseDouble(reader.getAttributeValue(null, "minlon"));
    var maxLongitude = Double.parseDouble(reader.getAttributeValue(null, "maxlon"));

    return new Bounds(minLatitude, maxLatitude, minLongitude, maxLongitude);
  }

  public Metadata loadMetadata(XMLStreamReader reader) {
    return new Metadata(reader.getAttributeValue(null, "version"), reader.getAttributeValue(null, "generator"));
  }

  public Relation loadRelation(XMLStreamReader reader) throws XMLStreamException {
    var id = Long.parseLong(reader.getAttributeValue(null, "id"));
    var nested = loadNestedElements(reader, Elements.RELATION);
    return new Relation(id, nested.tags, nested.members);
  }

  private NestedElementsReturn loadNestedElements(XMLStreamReader reader, Elements endElement) throws XMLStreamException {
    var tags = new HashMap<String, String>();
    var refs = new ArrayList<Long>();
    var members = new ArrayList<Member>();

    reader.nextTag();

    while (!reader.isEndElement() || !endElement.isElement(reader.getLocalName())) {
      if (reader.isStartElement()) {
        switch (NestedElements.fromTag(reader.getLocalName())) {
          case TAG -> tags.put(reader.getAttributeValue(null, "k"), reader.getAttributeValue(null, "v"));
          case REF -> refs.add(Long.parseLong(reader.getAttributeValue(null, "ref")));
          case MEMBER -> members.add(new Member(reader.getAttributeValue(null, "type"), Long.parseLong(reader.getAttributeValue(null, "ref")), reader.getAttributeValue(null, "role")));
        }
      }
      reader.nextTag();
    }

    return new NestedElementsReturn(tags, refs, members);
  }

  private static class NestedElementsReturn {
    private final Map<String, String> tags;
    private final List<Long> refs;
    private final List<Member> members;

    public NestedElementsReturn(Map<String, String> tags, List<Long> refs, List<Member> members) {
      this.tags = tags;
      this.refs = refs;
      this.members = members;
    }
  }
}
