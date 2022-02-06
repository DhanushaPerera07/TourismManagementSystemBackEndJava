package com.elephasvacation.tms.web.api.exceptionhandler;

import com.elephasvacation.tms.web.exception.IdFormatException;
import com.elephasvacation.tms.web.exception.RecordNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class AppWideExceptionHandler {
    Logger logger = LoggerFactory.getLogger(AppWideExceptionHandler.class);

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
    @ExceptionHandler(value = {RecordNotFoundException.class, NoSuchElementException.class})
    public String recordNotFoundException() {
        return "Record not found.";
    }

//    @ResponseStatus(code = HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NoSuchElementException.class)
//    public String NoMatchingRecordNotFoundException() {
//        return "Record not found.";
//    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public String globalExceptionHandler(Throwable t) {
        logger.error(null, t);
        return "Something went wrong, please contact IT Dept";
    }
}
