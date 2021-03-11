package com.navigation.parser.elements;

import java.util.List;
import java.util.Objects;

public class Way {

    private final String id;
    private final List<Tag> tags;
    private final List<String> nodeReferences;

    public Way(String id, List<Tag> tags, List<String> nodeReferences) {
        this.id = id;
        this.tags = tags;
        this.nodeReferences = nodeReferences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Way way = (Way) o;
        return id.equals(way.id) && tags.equals(way.tags) && nodeReferences.equals(way.nodeReferences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<String> getNodeReferences() {
        return nodeReferences;
    }
}
