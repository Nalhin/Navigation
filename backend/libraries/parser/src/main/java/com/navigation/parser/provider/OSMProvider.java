package com.navigation.parser.provider;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public interface OSMProvider {
  OSMStreamReader loadOSMXml() throws IOException, XMLStreamException;
}
