/*
 * MIT License
 *
 * Copyright (c) 2021 Dhanusha Perera
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * @author : Dhanusha Perera
 * @since : 21/04/2021
 */
package com.elephasvacation.tms.web.listener;

import com.elephasvacation.tms.web.AppInitializer;
import com.elephasvacation.tms.web.commonConstant.FailedMessages;
import com.elephasvacation.tms.web.commonConstant.HibernateConstant;
import com.elephasvacation.tms.web.commonConstant.SuccessfulMessages;
import com.elephasvacation.tms.web.util.LogConfig;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyContextListener implements ServletContextListener {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MyContextListener.class);

    /* default constructor */
    public MyContextListener() {
    }

    /**
     * This method is called when the servlet context is initialized
     * (when the Web application is deployed).
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* Initialize AppInitializer.java */
        try {
            Class.forName(AppInitializer.class.getName());
        } catch (ClassNotFoundException e) {
            logger.error(FailedMessages.Spring.FAILED_LOADING_SPRING_CONTAINER, e);
        }

        /* Initialize Logging. */
        LogConfig.initLogging();

        /* get EntityManagerFactory instance from the Spring context. */
        EntityManagerFactory entityManagerFactory = AppInitializer.getContext().getBean(EntityManagerFactory.class);

        // let's set an attribute for EntityManagerFactory, and pass the EMF object.
        sce.getServletContext().setAttribute(HibernateConstant.JPA.ENTITY_MANAGER_FACTORY, entityManagerFactory);
        logger.info(SuccessfulMessages.ServletContext.CONTEXT_INITIALIZED_SUCCESSFULLY);

    }

    /**
     * This method is called when the servlet Context is undeploy or
     * Application Server shuts down.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        EntityManagerFactory entityManagerFactory = AppInitializer.getContext().getBean(EntityManagerFactory.class);

        EntityManagerFactory entityManagerFactory = (EntityManagerFactory) sce.getServletContext().
                getAttribute(HibernateConstant.JPA.ENTITY_MANAGER_FACTORY);

        /* close EntityManagerFactory. */
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }

        AppInitializer.getContext().close();

    }


}
