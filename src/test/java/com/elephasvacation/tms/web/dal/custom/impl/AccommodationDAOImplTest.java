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
package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.entity.Accommodation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AccommodationDAOImplTest {

    EntityManagerFactory emf = null;

    EntityManager em = null;

    @Autowired
    AccommodationDAOImpl accommodationDAO;

//    @Before
//    public void setEntityManager() {
//        try {
//            this.emf = HibernateUtil.getEntityManagerFactory();
//            this.em = emf.createEntityManager();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @After
//    public void closeEntityManager() {
//        if (em != null) {
//            em.close();
//            emf.close();
//        }
//    }

    @Test
    public void save() {
        assertNotNull(accommodationDAO);
        Accommodation accommodation = new Accommodation("ABC",
                "Colombo",
                4,
                "Hotel",
                "01121234567",
                "abc@gmail.com",
                "123, Colombo",
                "abc.hotels.com",
                "None",
                "None");

        try {
            em.getTransaction().begin();
            accommodationDAO.setEntityManager(em);
            Accommodation newAccommodation = accommodationDAO.save(accommodation);
            assertNotNull(newAccommodation.getId());
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void update() {

        assertNotNull(accommodationDAO);
        Accommodation accommodation = new Accommodation(
                5,
                "ABC hotel",
                "Colombo 07",
                5,
                "Hotel",
                "01121234567",
                "abc.hotels@gmail.com",
                "123, Colombo 07, Sri Lanka",
                "abc.hotels.com",
                "None",
                "None");

        try {
            em.getTransaction().begin();
            accommodationDAO.setEntityManager(em);
            accommodationDAO.update(accommodation);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//        @Test
//    public void delete() {
//    }

    @Test
    public void get() {
        assertNotNull(accommodationDAO);

        try {
            em.getTransaction().begin();
            accommodationDAO.setEntityManager(em);
            Accommodation accommodation = accommodationDAO.get(new Integer("4"));

            em.getTransaction().commit();

            assertEquals(new Integer("4"), accommodation.getId());
            assertEquals("ABC hotel", accommodation.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void getAll() {
//    }
}