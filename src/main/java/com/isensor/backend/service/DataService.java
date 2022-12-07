package com.isensor.backend.service;

import com.isensor.backend.exception.custom.NullEntityReferenceException;
import com.isensor.backend.model.Data;
import com.isensor.backend.repositoty.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {

    private final DataRepository dataRepository;
    private final MongoTemplate mongoTemplate;


    @Autowired
    public DataService(DataRepository dataRepository, MongoTemplate mongoTemplate) {
        this.dataRepository = dataRepository;
        this.mongoTemplate = mongoTemplate;
    }


    public Data create(Data data) {
        if(data != null) {
            return dataRepository.save(data);
        }

        throw new NullEntityReferenceException("Values can not be null");
    }

    public List<Data> read(Long limit) {
        if(limit == null) {
            limit = 10L;
        }

        long toSkip = 0L;
        if(dataRepository.count() > limit) {
            toSkip = dataRepository.count() - limit;
        }

        AggregationOperation skip = Aggregation.skip(toSkip);
        Aggregation aggregation = Aggregation.newAggregation(skip);

        return mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(Data.class), Data.class).getMappedResults();
    }



}
