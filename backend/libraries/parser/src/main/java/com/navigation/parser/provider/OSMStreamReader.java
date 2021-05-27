package com.navigation.parser.provider;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.util.StreamReaderDelegate;

public class OSMStreamReader extends StreamReaderDelegate implements AutoCloseable {
  private static final XMLInputFactory factory = XMLInputFactory.newFactory();
  private final InputStream stream;

  public OSMStreamReader(InputStream stream) throws XMLStreamException {
    super();
    this.stream = stream;
    setParent(factory.createXMLStreamReader(stream));
  }

  @Override
  public void close() {
    try {
      super.close();
      stream.close();
    } catch (XMLStreamException | IOException ignored) {
    }
  }
}
