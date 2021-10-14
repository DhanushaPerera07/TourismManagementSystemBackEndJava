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
 * @author : Dhanusha Perera
 * @author : Dhanusha Perera
 * @author : Dhanusha Perera
 * @since : 21/04/2021
 * @since : 21/04/2021
 * @since : 21/04/2021
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
package com.elephasvacation.tms.web.listener;

import com.elephasvacation.tms.web.commonConstant.HibernateConstant;
import com.elephasvacation.tms.web.commonConstant.SuccessfulMessages;
import com.elephasvacation.tms.web.util.HibernateUtil;
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

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized
        (when the Web application is deployed). */
        LogConfig.initLogging();
        logger.info(SuccessfulMessages.CONTEXT_INITIALIZED_SUCCESSFUL);
        EntityManagerFactory entityManagerFactory = HibernateUtil.getEntityManagerFactory();
        // let's set an attribute for EntityManagerFactory, and pass the EMF object.
        sce.getServletContext().setAttribute(HibernateConstant.ENTITY_MANAGER_FACTORY, entityManagerFactory);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeploy or
        Application Server shuts down. */
        EntityManagerFactory entityManagerFactory = (EntityManagerFactory) sce.getServletContext()
                .getAttribute(HibernateConstant.ENTITY_MANAGER_FACTORY);

        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }

    }


}
