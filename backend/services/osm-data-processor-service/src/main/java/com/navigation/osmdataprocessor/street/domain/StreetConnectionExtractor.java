package com.navigation.osmdataprocessor.street.domain;

import com.navigation.parser.elements.Way;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StreetConnectionExtractor {

  private static final Pattern ONLY_DIGITS_PATTERN = Pattern.compile("\\d+");
  private static final String MAX_SPEED_TAG = "maxspeed";
  private static final String ONE_WAY_TAG = "oneway";
  private static final String ONE_WAY_TRUTHY_VALUE = "yes";

  private static final int DEFAULT_SPEED = 50;

  public List<StreetConnection> extractFromWay(Way way) {
    List<StreetConnection> streetConnections = new ArrayList<>();

    List<Long> nodeReferences = way.getNodeReferences();
    int maxSpeed = extractMaxSpeed(way);

    for (int i = 1; i < nodeReferences.size(); i++) {
      long from = nodeReferences.get(i - 1);
      long to = nodeReferences.get(i);
      streetConnections.add(new StreetConnection(generateId(from, to), from, to, maxSpeed));
    }
    if (!way.containsTagWithValue(ONE_WAY_TAG, ONE_WAY_TRUTHY_VALUE)) {
      for (int i = nodeReferences.size() - 2; i >= 0; i--) {
        long from = nodeReferences.get(i + 1);
        long to = nodeReferences.get(i);
        streetConnections.add(new StreetConnection(generateId(from, to), from, to, maxSpeed));
      }
    }

    return streetConnections;
  }

  private int extractMaxSpeed(Way way) {
    if (!way.containsTag(MAX_SPEED_TAG)) {
      return DEFAULT_SPEED;
    }
    String maxSpeedTagValue = way.getTag(MAX_SPEED_TAG);
    if (!ONLY_DIGITS_PATTERN.matcher(maxSpeedTagValue).matches()) {
      return DEFAULT_SPEED;
    }
    return Integer.parseInt(maxSpeedTagValue);
  }

  private String generateId(long from, long to) {
    return from + "#" + to;
  }
}
