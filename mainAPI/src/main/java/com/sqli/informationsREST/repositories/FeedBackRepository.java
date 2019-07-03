package com.sqli.informationsREST.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sqli.informationsREST.models.FeedBack;

public interface FeedBackRepository extends MongoRepository<FeedBack, String> {
}
