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
package com.elephasvacation.tms.web.api.mealPlan;

import com.elephasvacation.tms.web.business.BOFactory;
import com.elephasvacation.tms.web.business.BOTypes;
import com.elephasvacation.tms.web.business.custom.MealPlanBO;
import com.elephasvacation.tms.web.dto.MealPlanDTO;

import java.sql.Connection;
import java.util.List;

public class MealPlanAPIImpl implements MealPlanAPI {

    private MealPlanBO mealPlanBO = BOFactory.getInstance().getBO(BOTypes.MEAL_PLAN);

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.mealPlanBO.setConnection(connection);
    }

    @Override
    public Integer createMealPlan(MealPlanDTO mealPlanDTO) throws Exception {
        return this.mealPlanBO.createMealPlan(mealPlanDTO);
    }

    @Override
    public boolean updateMealPlan(MealPlanDTO mealPlanDTO) throws Exception {
        return this.mealPlanBO.updateMealPlan(mealPlanDTO);
    }

    @Override
    public boolean deleteMealPlan(Integer mealPlanID) throws Exception {
        return this.mealPlanBO.deleteMealPlan(mealPlanID);
    }

    @Override
    public MealPlanDTO getMealPlanByID(Integer mealPlanID) throws Exception {
        return this.mealPlanBO.getMealPlanByID(mealPlanID);
    }

    @Override
    public List<MealPlanDTO> getAllMealPlans() throws Exception {
        return this.mealPlanBO.getAllMealPlans();
    }
}
