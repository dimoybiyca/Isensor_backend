package com.isensor.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isensor.backend.dto.DataDto;
import com.isensor.backend.model.Data;
import com.isensor.backend.repositoty.DataRepository;
import com.isensor.backend.service.DataService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ISensorBackendApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataService dataService;

    private static ObjectMapper mapper = new ObjectMapper();

    @AfterEach
    private void truncate(@Autowired DataRepository dataRepository) {
        dataRepository.deleteAll();
    }

    @Test
    public void readDataTest() throws Exception {
        fillDatabase();

        mockMvc.perform(MockMvcRequestBuilders.get("/data/read")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(10)))
                .andExpect(jsonPath("$[0].temperature", Matchers.equalTo(13D)))
                .andExpect(jsonPath("$[0].pressure", Matchers.equalTo(895D)))
                .andExpect(jsonPath("$[0].humidity", Matchers.equalTo(50)));
    }

    @Test
    public void readDataLimitTest() throws Exception {
        fillDatabase();

        mockMvc.perform(MockMvcRequestBuilders.get("/data/read?limit=5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(5)))
                .andExpect(jsonPath("$[4].temperature", Matchers.equalTo(13D)))
                .andExpect(jsonPath("$[4].pressure", Matchers.equalTo(895D)))
                .andExpect(jsonPath("$[4].humidity", Matchers.equalTo(50)));
    }

    @Test
    public void writeCorrectDataTest() throws Exception {
        Data temp = new Data(15, 900, 40);
        DataDto newData = new DataDto(temp);

        String json = mapper.writeValueAsString(newData);

        mockMvc.perform(MockMvcRequestBuilders.post("/data/write")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void writeWrongTemperatureDataTest() throws Exception {
        Data temp = new Data(150, 900, 40);
        DataDto newData = new DataDto(temp);

        String json = mapper.writeValueAsString(newData);

        mockMvc.perform(MockMvcRequestBuilders.post("/data/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message",
                        Matchers.equalTo("temperature - must be less than or equal to 100;")));
    }

    @Test
    public void writeWrongHumidityDataTest() throws Exception {
        Data temp = new Data(15, 900, 200);
        DataDto newData = new DataDto(temp);

        String json = mapper.writeValueAsString(newData);

        mockMvc.perform(MockMvcRequestBuilders.post("/data/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message",
                        Matchers.equalTo("humidity - must be less than or equal to 100;")));
    }

    @Test
    public void writeWrongPressureDataTest() throws Exception {
        Data temp = new Data(15, -900, 60);
        DataDto newData = new DataDto(temp);

        String json = mapper.writeValueAsString(newData);

        mockMvc.perform(MockMvcRequestBuilders.post("/data/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message",
                        Matchers.equalTo("pressure - must be greater than or equal to 200;")));
    }

    @Test
    public void writeNullTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/data/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content("{}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message",
                        Matchers.equalTo("pressure - must be greater than or equal to 200;")));
    }

    private void fillDatabase() {
        for (int i = 0; i < 10; i++) {
            dataService.create(new Data(13, 895, 50));
        }
    }
}
