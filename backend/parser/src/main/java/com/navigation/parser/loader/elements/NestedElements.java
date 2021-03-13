package com.navigation.parser.loader.elements;

import com.navigation.parser.loader.exceptions.InvalidOsmTagException;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum NestedElements {

  REF("nd"),
  TAG("tag"),
  MEMBER("member");

  public final String TAG_VALUE;

  NestedElements(String TAG_VALUE) {
    this.TAG_VALUE = TAG_VALUE;
  }

  private static final Map<String, NestedElements> ALLOWED_VALUES;

  static {
    var map = new HashMap<String, NestedElements>();
    for (var instance : NestedElements.values()) {
      map.put(instance.TAG_VALUE, instance);
    }
    ALLOWED_VALUES = Collections.unmodifiableMap(map);
  }

  public static NestedElements fromTag(String tag) {
    if (!ALLOWED_VALUES.containsKey(tag)) {
      throw new InvalidOsmTagException(MessageFormat.format("Tag with name {0} is not a valid nested OSM tag", tag));
    }
    return ALLOWED_VALUES.get(tag);
  }
}
