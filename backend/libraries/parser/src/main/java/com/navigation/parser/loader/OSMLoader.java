package com.navigation.parser.loader;

import com.navigation.parser.exporter.OSMExporter;
import com.navigation.parser.loader.elements.Elements;
import com.navigation.parser.loader.elements.ElementsFactory;
import com.navigation.parser.loader.specification.OSMLoadAllSpecification;
import com.navigation.parser.loader.specification.OSMLoaderSpecification;
import com.navigation.parser.provider.OSMProvider;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.util.HashSet;

public class OSMLoader {

  private final OSMProvider provider;
  private final OSMExporter exporter;
  private final OSMLoaderSpecification specification;
  private final ElementsFactory elementsFactory;

  public OSMLoader(OSMProvider provider, OSMExporter exporter) {
    this(provider, exporter, new OSMLoadAllSpecification());
  }

  public OSMLoader(OSMProvider provider, OSMExporter exporter, OSMLoaderSpecification specification) {
    this.provider = provider;
    this.exporter = exporter;
    this.specification = specification;
    this.elementsFactory = new ElementsFactory();
  }

  public void export() throws IOException, XMLStreamException {
    var reader = provider.loadOSMXml();
    var readOrder = specification.getReadOrder();

    if (new HashSet<>(readOrder).size() != readOrder.size()) {
      throw new IllegalArgumentException("Read order must have all unique nodes");
    }

    var prevElement = Elements.METADATA;

    for (var element : readOrder) {
      if (OSMLoaderSpecification.getDefaultPosition(prevElement) > OSMLoaderSpecification.getDefaultPosition(element)) {
        reader.close();
        reader = provider.loadOSMXml();
      }

      advanceStreamToFirstElementOfType(reader, element);
      parseAllElementsOfType(reader, element);

      prevElement = element;
    }

    reader.close();
  }

  private void advanceStreamToFirstElementOfType(XMLStreamReader reader, Elements element) throws XMLStreamException {
    while (reader.hasNext()) {
      if (reader.isStartElement() && element.isElement(reader.getLocalName())) {
        return;
      }

      reader.next();
    }
  }

  private void parseAllElementsOfType(XMLStreamReader reader, Elements element) throws XMLStreamException {
    while (reader.hasNext()) {
      if (reader.isStartElement() && Elements.fromTag(reader.getLocalName()) != element) {
        return;
      }

      if (reader.isStartElement()) {
        parseElement(reader, element);
      }
      reader.next();
    }
  }


  private void parseElement(XMLStreamReader reader, Elements element) throws XMLStreamException {
    switch (element) {
      case WAY -> {
        var way = elementsFactory.loadWay(reader);
        if (specification.isSatisfiedBy(way)) {
          exporter.export(way);
        }
      }
      case NODE -> {
        var node = elementsFactory.loadNode(reader);
        if (specification.isSatisfiedBy(node)) {
          exporter.export(node);
        }
      }
      case BOUNDS -> {
        var bounds = elementsFactory.loadBounds(reader);
        if (specification.isSatisfiedBy(bounds)) {
          exporter.export(bounds);
        }
      }
      case METADATA -> exporter.export(elementsFactory.loadMetadata(reader));
      case RELATION -> {
        var relation = elementsFactory.loadRelation(reader);
        if (specification.isSatisfiedBy(relation)) {
          exporter.export(relation);
        }
      }
    }
  }
}
