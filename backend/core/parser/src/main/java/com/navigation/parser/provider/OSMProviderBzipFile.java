package com.navigation.parser.provider;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;

public class OSMProviderBzipFile implements OSMProvider {

  private final String filePath;

  public OSMProviderBzipFile(String filePath) {
    this.filePath = filePath;
  }

  @Override
  public XMLStreamReader loadOSMXml() throws IOException, XMLStreamException {
    var xmlInputFactory = XMLInputFactory.newInstance();
    return xmlInputFactory.createXMLStreamReader(new BZip2CompressorInputStream(new FileInputStream(filePath)));
  }
}
