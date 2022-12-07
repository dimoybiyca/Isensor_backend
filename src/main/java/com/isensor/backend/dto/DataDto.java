package com.isensor.backend.dto;


import com.isensor.backend.model.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;


@lombok.Data
@NoArgsConstructor
public class DataDto {

    private String id;

    @NotNull
    @Min(-50)
    @Max(100)
    private double temperature;

    @NotNull
    @Min(200)
    @Max(1400)
    private double pressure;

    @NotNull
    @Min(0)
    @Max(100)
    private double humidity;

    private Date date;



    public DataDto(Data data) {
        this.id = data.getId();
        this.temperature = data.getTemperature();
        this.humidity = data.getHumidity();
        this.pressure = data.getPressure();
        this.date = data.getCreatedDate();
    }



    public Data toObject() {
        Data data = new Data();

        data.setTemperature(this.temperature);
        data.setPressure(this.pressure);
        data.setHumidity(this.humidity);
        data.setCreatedDate(new Date());

        return data;
    }
}
