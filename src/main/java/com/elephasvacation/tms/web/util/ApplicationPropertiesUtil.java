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
 */
/**
 * @author : Dhanusha Perera
 * @since : 21/04/2021
 */
package com.elephasvacation.tms.web.util;

import com.elephasvacation.tms.web.commonConstant.FailedMessages;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationPropertiesUtil {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ApplicationPropertiesUtil.class);

    /* variable to hold the ApplicationPropertiesUtil instance. */
    public static ApplicationPropertiesUtil applicationPropertiesUtil = createInstance();

    /* holds the properties instance. */
    Properties properties;

    /* default constructor */
    private ApplicationPropertiesUtil() {
    }

    /* create a object from ApplicationPropertiesUtil class. */
    private static ApplicationPropertiesUtil createInstance() {
        return new ApplicationPropertiesUtil();
    }

    /** expose the instance.
     * @return ApplicationPropertiesUtil instance. */
    public static ApplicationPropertiesUtil getInstance() {
        return applicationPropertiesUtil;
    }

    /** loads the properties file.
     * @return Properties instance.
     */
    public Properties loadPropertyFile(String propertiesFileName) {
        this.properties = new Properties();

        InputStream resourceAsStream = ApplicationPropertiesUtil.class.getResourceAsStream(propertiesFileName);
        if (resourceAsStream != null) {
            try {
                this.properties.load(resourceAsStream);
            } catch (IOException ioException) {
                /* if an error occurred when reading from the input stream. */
                System.out.println(FailedMessages.FAILED_LOADING_PROPERTIES_FILE + propertiesFileName);
                logger.error(FailedMessages.FAILED_LOADING_PROPERTIES_FILE + propertiesFileName, ioException);
                ioException.printStackTrace();
            } catch (Exception exception) {
                exception.printStackTrace();
                logger.error("Something went wrong when loading the properties file:", exception);
            }
        } else {
//            System.out.println(FailedMessages.FAILED_LOADING_PROPERTIES_FILE + propertiesFileName);
            logger.error(FailedMessages.FAILED_LOADING_PROPERTIES_FILE + propertiesFileName);
            throw new NullPointerException();
        }

        return this.properties;
    }


}
