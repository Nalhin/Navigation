package com.navigation.parser.loader.specification;

import java.util.Collection;

class TrieAllowList {

  private final TrieNode trie;

  public TrieAllowList() {
    trie = new TrieNode();
  }

  public void allowAll(Collection<Long> ids) {
    ids.forEach(this::allow);
  }

  public void allow(long longId) {
    TrieNode curr = trie;
    String id = String.valueOf(longId);
    for (int i = 0; i < id.length(); i++) {
      int index = id.charAt(i) - '0';
      if (curr.children[index] == null) {
        curr.children[index] = new TrieNode();
      }
      curr = curr.children[index];
    }

    curr.allowed = true;
  }

  public boolean isAllowed(long id) {
    TrieNode curr = trie;
    String stringId = String.valueOf(id);
    for (int i = 0; i < stringId.length(); i++) {
      curr = curr.children[stringId.charAt(i) - '0'];
      if (curr == null) {
        return false;
      }
    }
    return curr.allowed;
  }


  private static class TrieNode {
    private final TrieNode[] children = new TrieNode[10];
    private boolean allowed = false;
  }
}
