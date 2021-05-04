package com.navigation.parser.factory;

import com.navigation.parser.elements.Element;
import com.navigation.parser.types.ElementTypes;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public interface ElementFactory {
  Element loadElement(ElementTypes elementTypes, XMLStreamReader reader) throws XMLStreamException;
}
