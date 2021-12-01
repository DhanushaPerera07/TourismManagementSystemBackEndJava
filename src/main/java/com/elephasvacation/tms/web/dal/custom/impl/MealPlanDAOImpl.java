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
/*
 * @author : Dhanusha Perera
 * @date : 18/07/2021
 */
package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.dal.custom.MealPlanDAO;
import com.elephasvacation.tms.web.entity.MealPlan;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class MealPlanDAOImpl implements MealPlanDAO {

    private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Integer save(MealPlan mealPlan) throws Exception {
        this.entityManager.persist(mealPlan);
        //  call the flush method on EntityManager manually, because we need to get the Generated ID
        this.entityManager.flush();
        return mealPlan.getId();
    }

    @Override
    public void update(MealPlan mealPlan) throws Exception {
        this.entityManager.merge(mealPlan);
    }

    @Override
    public void delete(Integer key) throws Exception {
        this.entityManager.remove(this.entityManager.find(MealPlan.class, key));
    }

    @Override
    public MealPlan get(Integer key) throws Exception {
        return this.entityManager.find(MealPlan.class, key);
    }

    @Override
    public List<MealPlan> getAll() throws Exception {
//        This is how you can do the same thing using 'Query', but if you do that you have to cast it.
//        Query allMealPlanQuery = this.entityManager.createQuery("SELECT mp FROM MealPlan mp");
//        return (List<MealPlan>) allMealPlanQuery.getResultList();

        TypedQuery<MealPlan> selectMpFromMealPlanMpTypedQuery = this.entityManager
                .createQuery("SELECT mp FROM MealPlan mp", MealPlan.class);

        return selectMpFromMealPlanMpTypedQuery.getResultList();

    }
}
