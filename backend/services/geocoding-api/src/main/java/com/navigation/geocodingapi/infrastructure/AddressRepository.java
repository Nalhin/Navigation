package com.navigation.geocodingapi.infrastructure;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends ElasticsearchRepository<AddressEntity, String> {

  @Query("""
        {
        "multi_match": {
            "query": "?0",
            "fields": ["city", "country", "houseNumber", "street"]
        }
       } 
      """)
  List<AddressEntity> searchByAddress(String search);

}
