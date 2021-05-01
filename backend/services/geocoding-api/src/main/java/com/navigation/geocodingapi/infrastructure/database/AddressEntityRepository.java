package com.navigation.geocodingapi.infrastructure.database;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface AddressEntityRepository extends ElasticsearchRepository<AddressEntity, String>  {

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
