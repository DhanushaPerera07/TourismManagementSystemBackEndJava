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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.MealPlanDAO;
import com.elephasvacation.tms.web.entity.MealPlan;
import com.elephasvacation.tms.web.util.HibernateUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.Assert.*;

public class MealPlanDAOImplTest {

    EntityManagerFactory emf = null;
    EntityManager em = null;

    MealPlanDAO mealPlanDAO = DAOFactory.getInstance().getDAO(DAOTypes.MEAL_PLAN);

    @Before
    public void setEntityManager() {
        try {
            this.emf = HibernateUtil.getEntityManagerFactory();
            this.em = emf.createEntityManager();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void closeEntityManager() {
        if (em != null) {
            em.close();
            emf.close();
        }
    }

    @Test
    public void save() {
        MealPlan roomOnlyMealPlan = new MealPlan("Room Only");

        try {
            this.em.getTransaction().begin();
            this.mealPlanDAO.setEntityManager(this.em);
            Integer roomOnlyMealPlanId = mealPlanDAO.save(roomOnlyMealPlan);
            assertNotNull(roomOnlyMealPlanId);
            this.em.getTransaction().commit();

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Test
    public void update() {
        try {
            this.em.getTransaction().begin();
            this.mealPlanDAO.setEntityManager(this.em);

            // get the database record.
            MealPlan roomOnlyMealPlan = mealPlanDAO.get(1);
            assertNotNull(roomOnlyMealPlan);
            roomOnlyMealPlan.setMealPlan("RO");
            this.mealPlanDAO.update(roomOnlyMealPlan);

            MealPlan roomOnlyMealPlanAfter = mealPlanDAO.get(1);
            this.em.getTransaction().commit();
            assertEquals("RO", roomOnlyMealPlanAfter.getMealPlan());

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void delete() {
        try {
            this.em.getTransaction().begin();
            this.mealPlanDAO.setEntityManager(this.em);
            MealPlan mealPlan = this.mealPlanDAO.get(1);
            assertNotNull(mealPlan);
            this.mealPlanDAO.delete(1);
            MealPlan mealPlanAfter = this.mealPlanDAO.get(1);
            assertNull(mealPlanAfter);
            this.em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void get() {
        try {
            this.em.getTransaction().begin();
            this.mealPlanDAO.setEntityManager(this.em);
            MealPlan mealPlan = this.mealPlanDAO.get(1);
            /*if there is a record in the database, assertion will be passed. */
            assertNotNull(mealPlan);
            this.em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void getAll() {
//    }
}