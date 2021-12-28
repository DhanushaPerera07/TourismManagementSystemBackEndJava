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
 */
package com.elephasvacation.tms.web;

import com.elephasvacation.tms.web.commonConstant.SuccessfulMessages;
import com.elephasvacation.tms.web.config.AppConfig;
import com.elephasvacation.tms.web.dal.DAOFactory;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static com.elephasvacation.tms.web.dal.DAOTypes.ACCOMMODATION;

public class AppInitializer {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AppInitializer.class);

    /* buildApplicationContext() will be executed when loading AppInitializer
     the in the MyContextListener.java */
    private static AnnotationConfigApplicationContext ctx = buildApplicationContext();

    private static AnnotationConfigApplicationContext buildApplicationContext() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        logger.info(SuccessfulMessages.Spring.APP_INITIALIZER_EXECUTED_SUCCESSFULLY);
        logger.info("Do we have EntityManagerFactory: " + ctx.containsBean("entityManagerFactory"));
        logger.info("Do we have daoFactory: " + ctx.containsBean("daoFactory"));
        logger.info("Do we have AccDAO: " + ctx.getBean(DAOFactory.class).getDAO(ACCOMMODATION));
        return ctx;
    }

    public static AnnotationConfigApplicationContext getContext() {
        return ctx;
    }
}
