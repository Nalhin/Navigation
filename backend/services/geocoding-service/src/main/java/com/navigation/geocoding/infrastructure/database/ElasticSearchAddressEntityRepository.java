package com.navigation.geocoding.infrastructure.database;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElasticSearchAddressEntityRepository extends ElasticsearchRepository<AddressEntity, String>  {

  @Cacheable("searchAddress")
  @Query("""
        {
        "multi_match": {
            "query": "?0",
            "fields": ["city", "houseNumber", "street"],
            "type": "cross_fields"
        }
       }
      """)
  List<AddressEntity> searchAllByAddress(String search);

}
