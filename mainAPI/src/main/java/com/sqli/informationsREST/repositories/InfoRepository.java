package com.sqli.informationsREST.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sqli.informationsREST.models.Information;

public interface InfoRepository extends MongoRepository<Information, String> {
    @Query("{'_id': ?0}")
    Information findBy_id(String id);

    @Query("{'type' : ?0, name : {$regex : ?1}}")
    List<Information> findByTypeAndName(String Type, String name);

}
