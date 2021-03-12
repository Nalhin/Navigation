package com.navigation.parser.elements;

import java.util.Objects;

public class Bounds {

    private final String minLatitude;
    private final String maxLatitude;
    private final String minLongitude;
    private final String maxLongitude;

    public Bounds(String minLatitude, String maxLatitude, String minLongitude, String maxLongitude) {
        this.minLatitude = minLatitude;
        this.maxLatitude = maxLatitude;
        this.minLongitude = minLongitude;
        this.maxLongitude = maxLongitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bounds bounds = (Bounds) o;
        return Objects.equals(minLatitude, bounds.minLatitude) && Objects.equals(maxLatitude, bounds.maxLatitude) && Objects.equals(minLongitude, bounds.minLongitude) && Objects.equals(maxLongitude, bounds.maxLongitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minLatitude, maxLatitude, minLongitude, maxLongitude);
    }

    public String getMinLatitude() {
        return minLatitude;
    }

    public String getMaxLatitude() {
        return maxLatitude;
    }

    public String getMinLongitude() {
        return minLongitude;
    }

    public String getMaxLongitude() {
        return maxLongitude;
    }

    @Override
    public String toString() {
        return "Bounds{" +
                "minLatitude='" + minLatitude + '\'' +
                ", maxLatitude='" + maxLatitude + '\'' +
                ", minLongitude='" + minLongitude + '\'' +
                ", maxLongitude='" + maxLongitude + '\'' +
                '}';
    }
}
