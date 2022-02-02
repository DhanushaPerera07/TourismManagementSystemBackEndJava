package com.elephasvacation.tms.web.api.util;

import com.elephasvacation.tms.web.exception.IdFormatException;

public class ApiUtil {

    public static Integer getIntegerId(String value) {
        Integer id;
        try {
            id = new Integer(value);
        } catch (NumberFormatException e) {
            throw new IdFormatException();
        }
        return id;
    }
}
