package com.navigation.osmdataprocessor.domain;

import com.navigation.parser.loader.ExportSummary;
import io.vavr.control.Try;

public interface DataProcessor {
  Try<ExportSummary> processAndExport();
}
