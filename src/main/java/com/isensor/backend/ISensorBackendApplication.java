package com.isensor.backend;

import com.isensor.backend.repositoty.DataRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = DataRepository.class)
public class ISensorBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ISensorBackendApplication.class, args);
    }
}
