package com.elephasvacation.tms.web.api.exceptionhandler;

import com.elephasvacation.tms.web.exception.IdFormatException;
import com.elephasvacation.tms.web.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AppWideExceptionHandler {
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "ID is not valid.")
    @ExceptionHandler(IdFormatException.class)
    public void handleIdNotValid() {
        // Nothing to do
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecordNotFoundException.class)
    public String recordNotFoundException() {
        return "Record not found.";
    }
}
