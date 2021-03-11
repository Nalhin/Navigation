package com.navigation.parser.elements;

import java.util.List;
import java.util.Objects;

public class Node {
    private final String id;
    private final String latitude;
    private final String longitude;
    private final List<Tag> tags;

    public Node(String id, String latitude, String longitude, List<Tag> tags) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public List<Tag> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id.equals(node.id) && latitude.equals(node.latitude) && longitude.equals(node.longitude) && tags.equals(node.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
