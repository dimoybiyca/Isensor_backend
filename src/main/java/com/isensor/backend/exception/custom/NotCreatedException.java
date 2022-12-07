package com.isensor.backend.exception.custom;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class NotCreatedException extends RuntimeException {

    public NotCreatedException(String message) {
        super(message);
    }

    public static String constructErrMessage(BindingResult bindingResult) {
        StringBuilder errMsg = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();

        for(FieldError error : errors) {
            errMsg.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append(";");
        }

        return errMsg.toString();
    }
}