package com.isensor.backend.service;

import com.isensor.backend.exception.custom.NullEntityReferenceException;
import com.isensor.backend.model.Data;
import com.isensor.backend.repositoty.DataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class DataServiceTest {

    @TestConfiguration
    static class DataServiceTestsConfiguration {
        @Bean
        public DataService stateService(DataRepository dataRepository, MongoTemplate mongoTemplate) {
            return new DataService(dataRepository, mongoTemplate);
        }
    }

    @Autowired
    private DataService dataService;

    @MockBean
    private DataRepository dataRepository;

    @MockBean
    private MongoTemplate mongoTemplate;

    @Test
    public void createTest() {
        Data expected = new Data(15, 895, 50);
        Mockito.when(dataRepository.save(any(Data.class))).thenReturn(expected);

        Data actual = dataService.create(new Data());

        assertEquals(expected, actual);
    }

    @Test
    public void createNullTest() {
        assertThrows(NullEntityReferenceException.class, () -> dataService.create(null));
    }

    private List<Data> getData() {
        List<Data> result = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            result.add(new Data(15, 895, 50));
        }

        return result;
    }
}
