package com.navigation.parser.elements;

import java.util.Objects;

public class Metadata {

  private final String OSMVersion;
  private final String generator;

  public Metadata(String osmVersion, String generator) {
    OSMVersion = osmVersion;
    this.generator = generator;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Metadata metadata = (Metadata) o;
    return Objects.equals(OSMVersion, metadata.OSMVersion) && Objects.equals(generator, metadata.generator);
  }

  @Override
  public int hashCode() {
    return Objects.hash(OSMVersion, generator);
  }

  public String getOSMVersion() {
    return OSMVersion;
  }

  public String getGenerator() {
    return generator;
  }

  @Override
  public String toString() {
    return "Metadata{" +
        "OSMVersion='" + OSMVersion + '\'' +
        ", generator='" + generator + '\'' +
        '}';
  }
}
