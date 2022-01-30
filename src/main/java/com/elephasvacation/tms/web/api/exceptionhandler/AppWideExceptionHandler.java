package com.elephasvacation.tms.web.api.exceptionhandler;

import com.elephasvacation.tms.web.exception.IdFormatException;
import com.elephasvacation.tms.web.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppWideExceptionHandler {
    /**
     * Since `reason` is returned with the method,
     * HTTP response will NOT contain HTML response body (it just contains the `reason`).
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IdFormatException.class)
    public String handleIdNotValid() {
        return "ID is not valid.";
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecordNotFoundException.class)
    public String recordNotFoundException() {
        return "Record not found.";
    }
}
