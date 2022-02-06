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
/*
 * @author : Dhanusha Perera
 * @date : 18/07/2021
 */
package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.MealPlanBO;
import com.elephasvacation.tms.web.business.custom.util.mapper.MealPlanDTOMapper;
import com.elephasvacation.tms.web.dal.MealPlanDAO;
import com.elephasvacation.tms.web.dto.MealPlanDTO;
import com.elephasvacation.tms.web.entity.MealPlan;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NoArgsConstructor
@Transactional
@Service
public class MealPlanBOImpl implements MealPlanBO {

    @Autowired
    private MealPlanDAO mealPlanDAO;

    @Autowired
    private MealPlanDTOMapper mapper;

    @Override
    public Integer createMealPlan(MealPlanDTO mealPlanDTO) {

        /* convert DTO to entity. */
        MealPlan mealPlan = this.mapper.getMealPlan(mealPlanDTO);

        /* save. */
        return this.mealPlanDAO.save(mealPlan).getId();
    }

    @Override
    public void updateMealPlan(MealPlanDTO mealPlanDTO) {
        this.mealPlanDAO.save(this.mapper.getMealPlan(mealPlanDTO));
    }

    @Override
    public void deleteMealPlan(Integer mealPlanID) {
        this.mealPlanDAO.deleteById(mealPlanID);
    }

    @Transactional(readOnly = true)
    @Override
    public MealPlanDTO getMealPlanByID(Integer mealPlanID) {

        /* get meal plan by ID. */
        MealPlan mealPlan = this.mealPlanDAO.findById(mealPlanID).get();

        /* convert entity to DTO. */
        return this.mapper.getMealPlanDTO(mealPlan);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MealPlanDTO> getAllMealPlans() {

        /* get all meal plans. */
        List<MealPlan> mealPlanList = this.mealPlanDAO.findAll();

        /* convert mealPlanList to DTOList. */
        return this.mapper.getMealPlanDTOList(mealPlanList);
    }
}
