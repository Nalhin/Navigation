package com.navigation.parser.loader.specification;

class TrieAllowList {

  private final TrieNode trie;

  public TrieAllowList() {
    trie = new TrieNode();
  }

  public void allow(String id) {
    TrieNode curr = trie;

    for (int i = 0; i < id.length(); i++) {
      int index = id.charAt(i) - '0';
      if (curr.children[index] == null) {
        curr.children[index] = new TrieNode();
      }
      curr = curr.children[index];
    }

    curr.allowed = true;
  }

  public boolean isAllowed(String id) {
    TrieNode curr = trie;
    for (int i = 0; i < id.length(); i++) {
      curr = curr.children[id.charAt(i) - '0'];
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
