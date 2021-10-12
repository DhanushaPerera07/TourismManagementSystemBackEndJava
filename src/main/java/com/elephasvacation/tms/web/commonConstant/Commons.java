/**
 * MIT License
 * <p>
 * Copyright (c) 2021 Dhanusha Perera
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * @author : Dhanusha Perera
 * @author : Dhanusha Perera
 * @author : Dhanusha Perera
 * @author : Dhanusha Perera
 * @author : Dhanusha Perera
 * @since : 21/04/2021
 * @since : 21/04/2021
 * @since : 21/04/2021
 * @since : 21/04/2021
 * @since : 21/04/2021
 */
/**
 * @author : Dhanusha Perera
 * @since : 21/04/2021
 */
package com.elephasvacation.tms.web.commonConstant;

import java.io.File;

public final class Commons {

    public static final String EMPTY_STRING = "";
    public static final String EMPTY_SPACE = " ";
    public static final String NEXT_LINE = "\n";
    public static final String CP = "cp";
    public static final String MESSAGES_PROPERTIES = "messages_properties";
    public static final String APPLICATION_PROPERTIES_FILE_NAME = "/application.properties";
    public static final String PERSISTENCE_UNIT_NAME = "TMS";
    public static final String USER_DIR = "user.dir";

    /* logger file related. */
    public static final String LOGGER_FILE_DIRECTORY = "logs";
    public static final String LOGGER_FILE_NAME = "tms-logs.log";
    public static final String LOGGER_FILE_PATH = "logs" + File.separator + LOGGER_FILE_NAME;
    public static final String LOGGER_FILE_FULL_PATH =
            System.getProperty(USER_DIR) + File.separator + LOGGER_FILE_PATH;


    public static final String ID = "id";
    public static final String CUSTOMER_ID = "customerId";


    /* names of the entities. */
    public static final String EMPLOYEE = "Employee";
    public static final String CUSTOMER = "Customer";
    public static final String ACCOMMODATION = "Accommodation";
    public static final String ACCOMMODATION_PACKAGE = "Accommodation Package";
    public static final String ACCOMMODATION_RATE = "Accommodation Rate";
    public static final String TOUR_DETAIL = "Tour Detail";
    public static final String ROOM_TYPE = "Room Type";
    public static final String ROOM_CATEGORY = "Room Category";
    public static final String MEAL_PLAN = "Meal Plan";


    public static final String NO_RECORDS_FOUND = "No {0} records found!";
    public static final String NO_MATCHING_RECORDS_FOUND = "No {0} matching record(s) found!";


}
