package com.navigation.osmdataprocessor.address.domain

import com.navigation.parser.elements.Node
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class AddressExtractorTest extends Specification {

  @Subject
  @Shared
  def mapper = new AddressExtractor()

  def "extractFromNode() should create address from node"() {
    given:
    def node = new Node(1,
        [
            "addr:city"       : "city",
            "addr:housenumber": "9",
            "addr:street"     : "street",
            "addr:postcode"   : "postcode"
        ],
        3,
        4)
    when:
    def result = mapper.extractFromNode(node)
    then:
    verifyAll(result) {
      id == 1
      city == "city"
      country == "Poland"
      houseNumber == "9"
      street == "street"
      postCode == "postcode"
      location.coordinates == [4d, 3d]
    }
  }
}
