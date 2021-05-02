module com.navigation.osmdataproessor {
  requires com.navigation.parser;

  requires spring.beans;
  requires spring.kafka;
  requires spring.context;
  requires spring.boot;
  requires java.xml;
  requires org.jetbrains.annotations;
  requires spring.boot.autoconfigure;
  requires kafka.clients;
}
