package com.isensor.backend.repository;

import com.isensor.backend.model.Data;
import com.isensor.backend.repositoty.DataRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@DataMongoTest
@TestConfiguration
@ExtendWith(SpringExtension.class)
public class DataRepositoryTest {

    @Autowired
    private DataRepository dataRepository;

    @AfterEach
    private void trunacate() {
        dataRepository.deleteAll();
    }

    @Test
    public void newRecordTest() {
        Data data = makeRecord();

        dataRepository.save(data);
        Data actual = dataRepository.findAll().stream().filter(d -> d.equals(data)).findFirst().orElse(null);

        Assertions.assertEquals(data, actual, "Data was not written to collection");
    }

    @Test
    public void readAllRecordsTest() {
        fillCollection(5);

        List<Data> actual = dataRepository.findAll();

        Assertions.assertEquals(5, actual.size());
    }

    private void fillCollection(int size) {
        for (int i = 0; i < size; i++) {
            Data data = new Data(Math.random() * 80, 985, 50);
            dataRepository.save(data);
        }
    }

    private Data makeRecord() {
        Data data = new Data(Math.random() * 80, 985, 50);
        dataRepository.save(data);

        return data;
    }
}
