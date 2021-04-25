package com.navigation.parser.exporter;

import com.navigation.parser.elements.*;

public interface OSMExporter {

  void export(Node node);

  void export(Way way);

  void export(Bounds bounds);

  void export(Metadata metadata);

  void export(Relation relation);
}
