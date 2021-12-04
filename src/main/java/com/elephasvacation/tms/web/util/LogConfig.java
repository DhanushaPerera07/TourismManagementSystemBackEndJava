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
 * @since : 21/04/2021
 */
/**
 * @author : Dhanusha Perera
 * @since : 21/04/2021
 */
package com.elephasvacation.tms.web.util;


import com.elephasvacation.tms.web.commonConstant.Commons;
import com.elephasvacation.tms.web.commonConstant.SuccessfulMessages;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogConfig {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LogConfig.class);


    public static void initLogging() {
        createLoggingPath();

        try {
            FileHandler fileHandler = new FileHandler(Commons.LOGGER_FILE_FULL_PATH, true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.INFO);
            Logger.getLogger("").addHandler(fileHandler);
            logger.info(SuccessfulMessages.LOGGER_INITIALIZED_SUCCESSFUL);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void createLoggingPath() {
        File file = new File(Commons.LOGGER_FILE_FULL_PATH);
        if (!file.exists()) {
            File fileLogFileDir = new File(System.getProperty(Commons.USER_DIR) + File.separator +
                    Commons.LOGGER_FILE_DIRECTORY);
            fileLogFileDir.mkdir();
            try {
                createAFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static boolean createAFile(File file) throws IOException {
        return file.createNewFile();
    }

}
