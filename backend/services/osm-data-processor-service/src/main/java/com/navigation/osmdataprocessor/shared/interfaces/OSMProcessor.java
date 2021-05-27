package com.navigation.osmdataprocessor.shared.interfaces;

import com.navigation.parser.loader.ExportSummary;
import io.vavr.control.Try;

public interface OSMProcessor {
  Try<ExportSummary> processAndPublish();
}
