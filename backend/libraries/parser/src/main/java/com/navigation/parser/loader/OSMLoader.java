package com.navigation.parser.loader;

import com.navigation.parser.exporter.OSMExporter;
import com.navigation.parser.factory.ElementFactory;
import com.navigation.parser.elements.ElementTypes;
import com.navigation.parser.factory.ElementFactoryImpl;
import com.navigation.parser.specification.OSMLoadAllSpecification;
import com.navigation.parser.specification.OSMLoaderSpecification;
import com.navigation.parser.provider.OSMProvider;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.util.HashSet;

public class OSMLoader {

  private final OSMProvider provider;
  private final OSMExporter exporter;
  private final OSMLoaderSpecification specification;
  private final ElementFactory elementsFactory = new ElementFactoryImpl();
  private final ExportSummarizer summarizer = new ExportSummarizer();

  public OSMLoader(OSMProvider provider, OSMExporter exporter) {
    this(provider, exporter, new OSMLoadAllSpecification());
  }

  public OSMLoader(
      OSMProvider provider, OSMExporter exporter, OSMLoaderSpecification specification) {
    this.provider = provider;
    this.exporter = exporter;
    this.specification = specification;
  }

  public ExportSummary export() throws IOException, XMLStreamException {
    var reader = provider.loadOSMXml();
    var readOrder = specification.getReadOrder();

    if (new HashSet<>(readOrder).size() != readOrder.size()) {
      throw new IllegalArgumentException("Read order must have all unique nodes");
    }

    var prevElement = ElementTypes.METADATA;

    for (var element : readOrder) {
      if (OSMLoaderSpecification.getDefaultPosition(prevElement)
          > OSMLoaderSpecification.getDefaultPosition(element)) {
        reader.close();
        reader = provider.loadOSMXml();
      }

      advanceStreamToFirstElementOfType(reader, element);
      parseAllElementsOfType(element, reader);

      prevElement = element;
    }

    reader.close();
    return summarizer.toSummary();
  }

  private void advanceStreamToFirstElementOfType(XMLStreamReader reader, ElementTypes element)
      throws XMLStreamException {
    while (reader.hasNext()) {
      if (reader.isStartElement() && element.isElement(reader.getLocalName())) {
        return;
      }

      reader.next();
    }
  }

  private void parseAllElementsOfType(ElementTypes element, XMLStreamReader reader)
      throws XMLStreamException {
    while (reader.hasNext()) {
      if (reader.isStartElement() && ElementTypes.fromTag(reader.getLocalName()) != element) {
        return;
      }

      if (reader.isStartElement()) {
        parseElement(element, reader);
      }
      reader.next();
    }
  }

  private void parseElement(ElementTypes element, XMLStreamReader reader)
      throws XMLStreamException {
    var loadedElement = elementsFactory.loadElement(element, reader);
    summarizer.incrementParsed(element);
    if (!loadedElement.accept(specification)) {
      return;
    }
    summarizer.incrementAccepted(element);

    if (loadedElement.accept(exporter)) {
      summarizer.incrementExported(element);
    }
  }
}
