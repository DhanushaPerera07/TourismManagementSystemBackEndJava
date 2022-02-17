package com.elephasvacation.tms.web.api.exceptionhandler;

import com.elephasvacation.tms.web.exception.BadRequestException;
import com.elephasvacation.tms.web.exception.DateTimeInvalidException;
import com.elephasvacation.tms.web.exception.IdFormatException;
import com.elephasvacation.tms.web.exception.RecordNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
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
    @ExceptionHandler({IdFormatException.class})
    public String handleIdNotValid() {
        return "ID is not valid.";
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {RecordNotFoundException.class, NoSuchElementException.class, EmptyResultDataAccessException.class})
    public String recordNotFoundException(RecordNotFoundException recordNotFoundException) {
        return (recordNotFoundException == null) ? "Record not found." : "Record not found: " +
                recordNotFoundException.getMessage();
    }

//    @ResponseStatus(code = HttpStatus.NOT_FOUND)
//    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
//    public String NoMatchingRecordNotFoundException() {
//        return "Record not found.";
//    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public String BadRequestException(BadRequestException badRequestException) {
        return (badRequestException == null) ? "Invalid data." : "Invalid data: " + badRequestException.getMessage();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateTimeInvalidException.class)
    public String DateTimeInvalidException() {
        return "Provided DateTime is invalid.";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public String globalExceptionHandler(Throwable t) {
        logger.error(null, t);
        return "Something went wrong, please contact IT Dept";
    }
}
