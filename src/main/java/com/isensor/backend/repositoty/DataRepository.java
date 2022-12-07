package com.isensor.backend.repositoty;


import com.isensor.backend.model.Data;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DataRepository extends MongoRepository<Data, String> {

//    @Aggregation("{'temperature': {$gt: ?0, $lt: ?0}} , {'skip': ?0}")
//    Data[] findLastValues(Long skip);
}
