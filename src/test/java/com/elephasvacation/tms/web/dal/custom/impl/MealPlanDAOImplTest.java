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

import com.elephasvacation.tms.web.WebAppConfig;
import com.elephasvacation.tms.web.WebRootConfig;
import com.elephasvacation.tms.web.dal.MealPlanDAO;
import com.elephasvacation.tms.web.entity.MealPlan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebRootConfig.class, WebAppConfig.class})
public class MealPlanDAOImplTest {

    @Autowired
    MealPlanDAO mealPlanDAO;

    @Test
    public void save() {
        MealPlan roomOnlyMealPlan = new MealPlan("Room Only");

        try {
            Integer roomOnlyMealPlanId = mealPlanDAO.save(roomOnlyMealPlan).getId();
            assertNotNull(roomOnlyMealPlanId);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Test
    public void update() {
        try {
            // get the database record.
            MealPlan roomOnlyMealPlan = mealPlanDAO.getById(1);
            assertNotNull(roomOnlyMealPlan);
            roomOnlyMealPlan.setMealPlan("RO");
            this.mealPlanDAO.save(roomOnlyMealPlan);

            MealPlan roomOnlyMealPlanAfter = mealPlanDAO.getById(1);
            assertEquals("RO", roomOnlyMealPlanAfter.getMealPlan());

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void delete() {
        try {
            MealPlan mealPlan = this.mealPlanDAO.getById(1);
            assertNotNull(mealPlan);
            this.mealPlanDAO.deleteById(1);
            MealPlan mealPlanAfter = this.mealPlanDAO.getById(1);
            assertNull(mealPlanAfter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void get() {
        try {
            MealPlan mealPlan = this.mealPlanDAO.getById(1);
            /*if there is a record in the database, assertion will be passed. */
            assertNotNull(mealPlan);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void getAll() {
//    }
}