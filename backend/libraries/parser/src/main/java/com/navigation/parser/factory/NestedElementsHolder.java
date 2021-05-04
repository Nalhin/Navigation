package com.navigation.parser.factory;

import com.navigation.parser.elements.Member;
import java.util.List;
import java.util.Map;

class NestedElementsHolder {
    private final Map<String, String> tags;
    private final List<Long> refs;
    private final List<Member> members;

    public NestedElementsHolder(Map<String, String> tags, List<Long> refs, List<Member> members) {
        this.tags = tags;
        this.refs = refs;
        this.members = members;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public List<Long> getRefs() {
        return refs;
    }

    public List<Member> getMembers() {
        return members;
    }
}
