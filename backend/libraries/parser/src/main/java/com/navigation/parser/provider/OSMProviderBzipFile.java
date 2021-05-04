package com.navigation.parser.provider;

import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

public class OSMProviderBzipFile implements OSMProvider {

  private final String filePath;

  public OSMProviderBzipFile(String filePath) {
    this.filePath = filePath;
  }

  @Override
  public OSMStreamReader loadOSMXml() throws XMLStreamException, IOException {
    return new OSMStreamReader(new BZip2CompressorInputStream(new FileInputStream(filePath)));
  }
}
