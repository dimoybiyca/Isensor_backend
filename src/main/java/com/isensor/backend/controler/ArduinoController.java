package com.isensor.backend.controler;

import com.isensor.backend.dto.DataDto;
import com.isensor.backend.dto.converter.DataConverter;
import com.isensor.backend.exception.custom.NotCreatedException;
import com.isensor.backend.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/data")
@CrossOrigin(maxAge = 36000)
public class ArduinoController {

    private final DataService dataService;



    @Autowired
    public ArduinoController(DataService dataService) {
        this.dataService = dataService;
    }



    @PostMapping("/write")
    public ResponseEntity<HttpStatus> writeData(@RequestBody @Valid DataDto dataDto,
                                                BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new NotCreatedException(NotCreatedException.constructErrMessage(bindingResult));
        }

        System.out.println("\n");
        System.out.println(dataDto.getTemperature());
        System.out.println("\n");

        dataService.create(dataDto.toObject());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/read")
    public DataDto[] readData(@Param("limit")Long limit) {

        return DataConverter.allToDto(dataService.read(limit)).toArray(new DataDto[0]);
    }
}
