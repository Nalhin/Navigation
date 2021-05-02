package com.navigation.parser.provider;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;

public interface OSMProvider {
  XMLStreamReader loadOSMXml() throws IOException, XMLStreamException;
}
