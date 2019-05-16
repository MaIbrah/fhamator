package com.sqli.elasticsearch.repository;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.sqli.elasticsearch.model.InformationElasticSearch;

@Repository
 public interface InformationRepository extends ElasticsearchRepository<InformationElasticSearch, String> {

 //@Query("{\"query\":{\"match\": {\"name\": \"?0\"}}}")
 @Query("{\"bool\": {\"must\": [{\"multi_match\":{\"query\": \"?0\", \"fields\": [\"keyWords^5\", \"name\", \"type\" ," +
     "\"attributes.value\"]}}]}}")
 List<InformationElasticSearch> listinformationMatches(String values);

 @Query("{\"bool\": {\"must\": [{\"match\" : {\"type\" : \"?0\"\n}},{\"multi_match\":{\"query\": \"?1\", \"fields\": [\"keyWords^5\", \"name\"," +
     "\"attributes.value\"]}}]}}")
 List<InformationElasticSearch> listinformationMatchesTypeAndKeywords(String type,String keyWord);


 List<InformationElasticSearch> getByAttributes(String name);



}
