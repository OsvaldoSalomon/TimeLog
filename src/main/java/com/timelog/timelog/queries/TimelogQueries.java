package com.timelog.timelog.queries;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class TimelogQueries {
    private MongoTemplate mongoTemplate;

    public TimelogQueries(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
}
