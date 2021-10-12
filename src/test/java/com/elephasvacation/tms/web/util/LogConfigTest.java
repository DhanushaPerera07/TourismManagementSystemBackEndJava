/*
@author : Dhanusha Perera
@since : 22/04/2021
*/

package com.elephasvacation.tms.web.util;

import org.junit.Test;

public class LogConfigTest {

    @Test
    public void initLogging() {
        LogConfig.initLogging();
    }

    @Test
    public void createLoggingPath() {
        LogConfig.createLoggingPath();
    }
}
