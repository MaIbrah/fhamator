package com.sqli.informationsREST.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sqli.informationsREST.models.Domain;

public interface DomainRepository extends MongoRepository<Domain, String> {
}
