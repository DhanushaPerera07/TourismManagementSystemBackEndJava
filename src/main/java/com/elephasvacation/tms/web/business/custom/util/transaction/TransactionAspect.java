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
package com.elephasvacation.tms.web.business.custom.util.transaction;

import com.elephasvacation.tms.web.business.SuperBO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
@Aspect
public class TransactionAspect {

    /* Before service method is executed. */
    @Before("@annotation(com.elephasvacation.tms.web.business.custom.util.transaction.TMSTransaction)")
    public void beforeServiceMethod(JoinPoint jp) {
        EntityManager em = ((SuperBO) (jp.getThis())).getEntityManager();
        em.getTransaction().begin();
    }

    /* After the service method successfully executed, */
    @After("@annotation(com.elephasvacation.tms.web.business.custom.util.transaction.TMSTransaction)")
    public void afterServiceMethod(JoinPoint jp) {
        EntityManager em = ((SuperBO) (jp.getThis())).getEntityManager();
        em.getTransaction().commit();
    }

    /* After service method with exception. */
    @AfterThrowing("@annotation(com.elephasvacation.tms.web.business.custom.util.transaction.TMSTransaction)")
    public void afterServiceMethodWithExp(JoinPoint jp) {
        EntityManager em = ((SuperBO) (jp.getThis())).getEntityManager();
        em.getTransaction().rollback();
    }
}
