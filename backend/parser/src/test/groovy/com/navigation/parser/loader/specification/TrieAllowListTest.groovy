package com.navigation.parser.loader.specification

import spock.lang.Specification

class TrieAllowListTest extends Specification {
  def "allow() should add #ids to allowed and return #expectedResult when isAllowed() is called with #inspected"(List<String> ids, String inspected, boolean expectedResult) {
    given:
    def trie = new TrieAllowList()
    when:
    ids.forEach(trie::allow)
    then:
    trie.isAllowed(inspected) == expectedResult
    where:
    ids                    | inspected || expectedResult
    ["123", "1"]           | "123"     || true
    ["12"]                 | "12"      || true
    ["12"]                 | "1"       || false
    ["102"]                | "102"     || true
    ["44", "9999999"]      | "45"      || false
    ["9999999", "99999"]   | "99999"   || true
    ["9999", "9999999999"] | "9999"    || true
  }
}
