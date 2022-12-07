package com.isensor.backend.model;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document
@lombok.Data
public class Data {

    @Id
    @Indexed(unique = true)
    private String id;

    @Field("temperature")
    private double temperature;

    @Field("pressure")
    private double pressure;

    @Field("humidity")
    private double humidity;

    @DateTimeFormat(style = "M-")
    @CreatedDate
    private Date createdDate;

    public Data() {
    }

    public Data(double temperature, double pressure, int humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.createdDate = new Date();
    }
}
