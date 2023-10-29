package com.example.chatservice.service;

import com.example.chatservice.document.DatabaseSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GenerateSequenceService {
    private final MongoTemplate mongoTemplate;

    public Long generateSequence(String seqName) {
        DatabaseSequence counter = mongoTemplate
                .findAndModify(
                        Query.query(Criteria.where("gatherId").is(seqName)),
                        new Update().inc("sequence", 1),
                        FindAndModifyOptions.options().returnNew(true).upsert(true),
                        DatabaseSequence.class
                );

        return !Objects.isNull(counter) ? counter.getSequence() : 1;
    }

    public void deleteAllOnlyForTest() {
        mongoTemplate.dropCollection(DatabaseSequence.class);
    }
}
