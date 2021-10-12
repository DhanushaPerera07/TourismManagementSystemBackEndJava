/*
@author : Dhanusha Perera
@date : 12/10/2021
*/
package com.elephasvacation.tms.web.util;

import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;

public class HibernateUtilTest {

    @Test
    public void getEntityManagerFactory() {
        EntityManagerFactory entityManagerFactory = HibernateUtil.getEntityManagerFactory();
        Assert.assertNotNull(entityManagerFactory);
    }
}
