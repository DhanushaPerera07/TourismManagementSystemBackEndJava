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
import com.elephasvacation.tms.web.business.custom.util.MealPlanDTOMapper;
import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.MealPlanDAO;
import com.elephasvacation.tms.web.dto.MealPlanDTO;
import com.elephasvacation.tms.web.entity.MealPlan;

import javax.persistence.EntityManager;
import java.util.List;

public class MealPlanBOImpl implements MealPlanBO {

    private final MealPlanDAO mealPlanDAO = DAOFactory.getInstance()
            .getDAO(DAOTypes.MEAL_PLAN);
    private final MealPlanDTOMapper mapper = MealPlanDTOMapper.instance;
    private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;

        /* Set Entity Manager to the DAL. */
        this.mealPlanDAO.setEntityManager(this.entityManager);
    }

    @Override
    public Integer createMealPlan(MealPlanDTO mealPlanDTO) throws Exception {
        this.entityManager.getTransaction().begin();

        /* convert DTO to entity. */
        MealPlan mealPlan = this.mapper.getMealPlan(mealPlanDTO);

        /* save. */
        Integer generatedMealPlanId = this.mealPlanDAO.save(mealPlan).getId();

        this.entityManager.getTransaction().commit();
        return generatedMealPlanId;
    }

    @Override
    public void updateMealPlan(MealPlanDTO mealPlanDTO) throws Exception {
        this.entityManager.getTransaction().begin();
        this.mealPlanDAO.update(this.mapper.getMealPlan(mealPlanDTO));
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void deleteMealPlan(Integer mealPlanID) throws Exception {
        this.entityManager.getTransaction().begin();
        this.mealPlanDAO.delete(mealPlanID);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public MealPlanDTO getMealPlanByID(Integer mealPlanID) throws Exception {
        this.entityManager.getTransaction().begin();

        /* get meal plan by ID. */
        MealPlan mealPlan = this.mealPlanDAO.get(mealPlanID);

        /* convert entity to DTO. */
        MealPlanDTO mealPlanDTO = this.mapper.getMealPlanDTO(mealPlan);

        this.entityManager.getTransaction().commit();
        return mealPlanDTO;
    }

    @Override
    public List<MealPlanDTO> getAllMealPlans() throws Exception {
        this.entityManager.getTransaction().begin();

        /* get all meal plans. */
        List<MealPlan> mealPlanList = this.mealPlanDAO.getAll();

        /* convert mealPlanList to DTOList. */
        List<MealPlanDTO> mealPlanDTOList = this.mapper.getMealPlanDTOList(mealPlanList);

        this.entityManager.getTransaction().commit();
        return mealPlanDTOList;
    }
}
