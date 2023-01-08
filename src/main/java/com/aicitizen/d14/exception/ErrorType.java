package com.aicitizen.d14.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorType {
    INTERNAL_ERROR(2000, "Internal Server Error", INTERNAL_SERVER_ERROR),
    BAD_REQUEST_ERROR(2001, "Invalid Parameter Error", BAD_REQUEST),
    CITIZEN_IS_EXIST(190, "Citizen is already exist", INTERNAL_SERVER_ERROR),
    CITIZEN_NOT_CREATED(1005, "Citizen is not created", INTERNAL_SERVER_ERROR),
    CITIZEN_NOT_UPDATED(1006, "Citizen is not updated", INTERNAL_SERVER_ERROR),
    CITIZEN_NOT_DELETED(1007, "Citizen is not deleted", INTERNAL_SERVER_ERROR),
    CITIZEN_NOT_FOUND(404, "Citizen is not found", NOT_FOUND);


    private int code;
    private String message;
    HttpStatus httpStatus;

}
