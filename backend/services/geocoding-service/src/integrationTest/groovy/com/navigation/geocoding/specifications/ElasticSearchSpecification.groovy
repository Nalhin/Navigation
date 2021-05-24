package com.navigation.geocoding.specifications

import com.navigation.geocoding.annotations.IntegrationTest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.support.WriteRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.MatchAllQueryBuilder
import org.elasticsearch.index.reindex.DeleteByQueryRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.cache.CacheManager
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.GenericContainer
import org.testcontainers.elasticsearch.ElasticsearchContainer
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
@IntegrationTest
@SpringBootTest
@ContextConfiguration(initializers = Initializer)
abstract class ElasticSearchSpecification extends Specification {

  @Autowired
  private RestHighLevelClient elasticSearchClient

  @Autowired
  private CacheManager cacheManager

  def saveInIndex(Map<String, Object> entity, String index) {
    def request = new IndexRequest(index)
        .source(entity)
        .setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE)
    elasticSearchClient.index(request, RequestOptions.DEFAULT)
  }

  def clearIndex(String index) {
    def request = new DeleteByQueryRequest(index)
        .setRefresh(true)
        .setQuery(new MatchAllQueryBuilder())
    elasticSearchClient.deleteByQuery(request, RequestOptions.DEFAULT)
  }

  def clearCache() {
    def manager = cacheManager
    manager.getCacheNames().each { manager.getCache(it).clear() }
  }

  @Shared
  static ElasticsearchContainer elasticsearchContainer = new ElasticsearchContainer(
      DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:7.7.0")).
      withExposedPorts(9200)

  @Shared
  static GenericContainer redisContainer = new GenericContainer<>(
      DockerImageName.parse("redis:6.0.9-alpine"))
      .withExposedPorts(6379)


  static class Initializer implements
      ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      elasticsearchContainer.start()
      redisContainer.start()
      def values = TestPropertyValues.of(
          "spring.elasticsearch.rest.uris=${elasticsearchContainer.getHttpHostAddress()}",
          "spring.redis.host=${redisContainer.getHost()}",
          "spring.redis.port=${redisContainer.getMappedPort(6379)}"
      )
      values.applyTo(configurableApplicationContext)
    }
  }
}