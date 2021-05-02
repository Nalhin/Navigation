module com.navigation.parser {
  requires java.xml;
  requires org.apache.commons.compress;

  exports com.navigation.parser.elements;
  exports com.navigation.parser.provider;
  exports com.navigation.parser.specification;
  exports com.navigation.parser.exceptions;
  exports com.navigation.parser.loader;
  exports com.navigation.parser.exporter;
}
