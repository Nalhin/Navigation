package com.navigation.osmdataprocessor.street.domain

import com.navigation.parser.elements.Way
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.that

class StreetConnectionExtractorTest extends Specification {

  @Subject
  @Shared
  def extractor = new StreetConnectionExtractor()

  def "extractFromWay() should extract street connections from osm way"() {
    given:
    def way = new Way(11, [maxspeed: "50", oneway: "yes"], [1L, 2L])
    when:
    def actualResult = extractor.extractFromWay(way)
    then:
    that actualResult, containsInAnyOrder(new StreetConnection("1#2", 1, 2, 50))
  }

  def "extractFromWay() should provide default max speed value when max speed has illegal value"() {
    given:
    def wayWithInvalidMaxSpeed = new Way(11, [oneway: "yes", maxspeed: "dd"], [1L, 2L])
    when:
    def actualResult = extractor.extractFromWay(wayWithInvalidMaxSpeed)
    then:
    that actualResult, containsInAnyOrder(new StreetConnection("1#2", 1, 2, 50))
  }

  def "extractFromWay() should provide default max speed value is not present"() {
    given:
    def wayWithoutMaxSpeed = new Way(11, [oneway: "yes"], [1L, 2L])
    when:
    def actualResult = extractor.extractFromWay(wayWithoutMaxSpeed)
    then:
    that actualResult, containsInAnyOrder(new StreetConnection("1#2", 1, 2, 50))
  }

  def "extractFromWay() should treat connections without oneway tag as two-way connections"() {
    given:
    def twoWayWay = new Way(11, [:], [1L, 2L, 3L])
    when:
    def actualResult = extractor.extractFromWay(twoWayWay)
    then:
    that actualResult, containsInAnyOrder(
        new StreetConnection("1#2", 1, 2, 50),
        new StreetConnection("2#3", 2, 3, 50),
        new StreetConnection("3#2", 3, 2, 50),
        new StreetConnection("2#1", 2, 1, 50)
    )
  }
}
