package com.navigation.parser.loader.specification

import spock.lang.Specification

class TrieAllowListTest extends Specification {
  def "allow() should add #allowed to allowed and return #expectedResult when isAllowed() is called with #inspected"(List<Long> allowed, Long inspected, boolean expectedResult) {
    given:
    def trie = new TrieAllowList()
    when:
    allowed.forEach(trie::allow)
    then:
    trie.isAllowed(inspected) == expectedResult
    where:
    allowed              | inspected || expectedResult
    [123L, 1L]           | 123L      || true
    [12L]                | 12L       || true
    [12L]                | 1L        || false
    [102L]               | 102L      || true
    [44L, 9999999L]      | 45L       || false
    [9999999L, 99999L]   | 99999L    || true
    [9999L, 9999999999L] | 9999L     || true
  }


  def "allowAll() should add all #allowed to allowed and return #expectedResult when isAllowed() is called with #inspected"(List<Long> allowed, Long inspected, boolean expectedResult) {
    given:
    def trie = new TrieAllowList()
    when:
    trie.allowAll(allowed)
    then:
    trie.isAllowed(inspected) == expectedResult
    where:
    allowed              | inspected || expectedResult
    [123L, 1L]           | 123L      || true
    [12L]                | 12L       || true
    [12L]                | 1L        || false
    [102L]               | 102L      || true
    [44L, 9999999L]      | 45L       || false
    [9999999L, 99999L]   | 99999L    || true
    [9999L, 9999999999L] | 9999L     || true
  }
}
