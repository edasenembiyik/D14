package com.aicitizen.d14.exception;

import lombok.Getter;

@Getter
public class CitizenServiceException extends RuntimeException{
    private final ErrorType errorType;

    public CitizenServiceException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public CitizenServiceException(ErrorType errorType, String message){
        super(message);
        this.errorType = errorType;
    }
}