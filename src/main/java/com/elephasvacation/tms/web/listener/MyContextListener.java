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
 * @since : 21/04/2021
 * @since : 21/04/2021
 */
/**
 * @author : Dhanusha Perera
 * @since : 21/04/2021
 */
package com.elephasvacation.tms.web.listener;

import com.elephasvacation.tms.web.commonConstant.ApplicationProperties;
import com.elephasvacation.tms.web.commonConstant.Commons;
import com.elephasvacation.tms.web.commonConstant.SuccessfulMessages;
import com.elephasvacation.tms.web.util.ApplicationPropertiesUtil;
import com.elephasvacation.tms.web.util.LogConfig;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;
import java.util.Properties;

@WebListener
public class MyContextListener implements ServletContextListener {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MyContextListener.class);

    Properties properties = ApplicationPropertiesUtil
            .getInstance()
            .loadPropertyFile(Commons.APPLICATION_PROPERTIES_FILE_NAME);

    /* default constructor */
    public MyContextListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized
        (when the Web application is deployed). */
        LogConfig.initLogging();
        logger.info(SuccessfulMessages.CONTEXT_INITIALIZED_SUCCESSFUL);
        sce.getServletContext().setAttribute(Commons.CP, basicDataSource());

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or
        Application Server shuts down. */
        BasicDataSource basicDataSource = (BasicDataSource) sce.getServletContext().getAttribute(Commons.CP);
        try {
            basicDataSource.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    /** config the basic configurations for the database connection pool
     * by reading application.properties file.
     * @return BasicDataSource instance.
     */
    private BasicDataSource basicDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        /* set user name. */
        basicDataSource.setUsername(properties.getProperty(ApplicationProperties.DATABASE_USER_NAME));

        /* set password. */
        basicDataSource.setPassword(properties.getProperty(ApplicationProperties.DATABASE_PASSWORD));

        /* set url. */
        basicDataSource.setUrl(properties.getProperty(ApplicationProperties.DATABASE_URL));

        /* set database driver class name. */
        basicDataSource.setDriverClassName(properties.getProperty(ApplicationProperties.DATABASE_DRIVER));

        /* set initial connection size.
         * The initial number of connections that are created when the pool is started.*/
        basicDataSource.setInitialSize(Integer
                .parseInt(properties.getProperty(ApplicationProperties.DATABASE_INITIAL_SIZE)));

        /* set max connection size.
         * The maximum number of active connections
         * that can be allocated from this pool at the same time, or negative for no limit. */
        basicDataSource.setMaxTotal(Integer
                .parseInt(properties.getProperty(ApplicationProperties.DATABASE_MAX_TOTAL)));

        return basicDataSource;
    }
}
