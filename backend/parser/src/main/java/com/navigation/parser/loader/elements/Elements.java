package com.navigation.parser.loader.elements;

import com.navigation.parser.loader.exceptions.InvalidOsmTagException;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Elements {

  WAY("way"),
  NODE("node"),
  BOUNDS("bounds"),
  METADATA("osm"),
  RELATION("relation");

  private final String TAG_VALUE;

  Elements(String TAG_VALUE) {
    this.TAG_VALUE = TAG_VALUE;
  }

  private static final Map<String, Elements> ALLOWED_VALUES;

  static {
    var map = new HashMap<String, Elements>();
    for (var instance : Elements.values()) {
      map.put(instance.TAG_VALUE, instance);
    }
    ALLOWED_VALUES = Collections.unmodifiableMap(map);
  }

  public static Elements fromTag(String tag) {
    if (!ALLOWED_VALUES.containsKey(tag)) {
      throw new InvalidOsmTagException(MessageFormat.format("Tag with name {0} is not a valid OSM tag", tag));
    }
    return ALLOWED_VALUES.get(tag);
  }

  public boolean isElement(String tag) {
    return TAG_VALUE.equals(tag);
  }
}
