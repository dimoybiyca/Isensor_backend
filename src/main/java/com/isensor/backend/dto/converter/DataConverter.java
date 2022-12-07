package com.isensor.backend.dto.converter;

import com.isensor.backend.dto.DataDto;
import com.isensor.backend.model.Data;


import java.util.List;
import java.util.stream.Collectors;

public class DataConverter {

    public static DataDto toDto(Data data) {
        return new DataDto(data);
    }

    public static List<DataDto> allToDto(List<Data> data) {
        return data.stream().map(DataConverter::toDto).collect(Collectors.toList());
    }
}
