package com.navigation.parser.types;

import com.navigation.parser.exceptions.InvalidOsmTagException;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum ElementTypes {
  WAY("way"),
  NODE("node"),
  BOUNDS("bounds"),
  METADATA("osm"),
  RELATION("relation");

  private final String TAG_VALUE;

  ElementTypes(String TAG_VALUE) {
    this.TAG_VALUE = TAG_VALUE;
  }

  private static final Map<String, ElementTypes> ALLOWED_VALUES;

  static {
    var map = new HashMap<String, ElementTypes>();
    for (var instance : ElementTypes.values()) {
      map.put(instance.TAG_VALUE, instance);
    }
    ALLOWED_VALUES = Collections.unmodifiableMap(map);
  }

  public static ElementTypes fromTag(String tag) {
    if (!ALLOWED_VALUES.containsKey(tag)) {
      throw new InvalidOsmTagException(
          MessageFormat.format("Tag with name {0} is not a valid OSM tag", tag));
    }
    return ALLOWED_VALUES.get(tag);
  }

  public boolean isElement(String tag) {
    return TAG_VALUE.equals(tag);
  }
}
