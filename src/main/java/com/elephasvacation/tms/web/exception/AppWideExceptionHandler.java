package com.elephasvacation.tms.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AppWideExceptionHandler {
    Logger logger = LoggerFactory.getLogger(AppWideExceptionHandler.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public String globalExceptionHandler(Throwable t) {
        logger.error(null, t);
        return "Something went wrong, please contact Elephas Vacations IT Department.";
    }
}
