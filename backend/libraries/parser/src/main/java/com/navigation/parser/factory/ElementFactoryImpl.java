package com.navigation.parser.factory;

import com.navigation.parser.elements.*;

import com.navigation.parser.types.ElementTypes;
import com.navigation.parser.types.NestedElementTypes;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class ElementFactoryImpl implements ElementFactory {

  public Element loadElement(ElementTypes elementTypes, XMLStreamReader reader) throws XMLStreamException {
    return switch (elementTypes){
      case WAY -> loadWay(reader);
      case NODE -> loadNode(reader);
      case BOUNDS -> loadBounds(reader);
      case METADATA -> loadMetadata(reader);
      case RELATION -> loadRelation(reader);
    };
  }


  private Way loadWay(XMLStreamReader reader) throws XMLStreamException {
    var id = Long.parseLong(reader.getAttributeValue(null, "id"));
    var nested = loadNestedElements(reader, ElementTypes.WAY);

    return new Way(id, nested.getTags(), nested.getRefs());
  }

  private Node loadNode(XMLStreamReader reader) throws XMLStreamException {
    var id = Long.parseLong(reader.getAttributeValue(null, "id"));
    var lat = Double.parseDouble(reader.getAttributeValue(null, "lat"));
    var lon = Double.parseDouble(reader.getAttributeValue(null, "lon"));
    var nested = loadNestedElements(reader, ElementTypes.NODE);

    return new Node(id, nested.getTags(), lat, lon);
  }

  private Bounds loadBounds(XMLStreamReader reader) {
    var minLatitude = Double.parseDouble(reader.getAttributeValue(null, "minlat"));
    var maxLatitude = Double.parseDouble(reader.getAttributeValue(null, "maxlat"));
    var minLongitude = Double.parseDouble(reader.getAttributeValue(null, "minlon"));
    var maxLongitude = Double.parseDouble(reader.getAttributeValue(null, "maxlon"));

    return new Bounds(minLatitude, maxLatitude, minLongitude, maxLongitude);
  }

  private Metadata loadMetadata(XMLStreamReader reader) {
    return new Metadata(reader.getAttributeValue(null, "version"), reader.getAttributeValue(null, "generator"));
  }

  private Relation loadRelation(XMLStreamReader reader) throws XMLStreamException {
    var id = Long.parseLong(reader.getAttributeValue(null, "id"));
    var nested = loadNestedElements(reader, ElementTypes.RELATION);
    return new Relation(id, nested.getTags(), nested.getMembers());
  }

  private NestedElementsHolder loadNestedElements(XMLStreamReader reader, ElementTypes endElement) throws XMLStreamException {
    var tags = new HashMap<String, String>();
    var refs = new ArrayList<Long>();
    var members = new ArrayList<Member>();

    reader.nextTag();

    while (!reader.isEndElement() || !endElement.isElement(reader.getLocalName())) {
      if (reader.isStartElement()) {
        switch (NestedElementTypes.fromTag(reader.getLocalName())) {
          case TAG -> tags.put(reader.getAttributeValue(null, "k"), reader.getAttributeValue(null, "v"));
          case REF -> refs.add(Long.parseLong(reader.getAttributeValue(null, "ref")));
          case MEMBER -> members.add(new Member(reader.getAttributeValue(null, "type"), Long.parseLong(reader.getAttributeValue(null, "ref")), reader.getAttributeValue(null, "role")));
        }
      }
      reader.nextTag();
    }

    return new NestedElementsHolder(tags, refs, members);
  }
}
