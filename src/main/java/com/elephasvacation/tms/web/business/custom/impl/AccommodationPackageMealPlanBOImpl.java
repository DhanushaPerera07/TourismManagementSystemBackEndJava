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
package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.AccommodationPackageMealPlanBO;
import com.elephasvacation.tms.web.business.custom.util.AccommodationPackageDTOMapper;
import com.elephasvacation.tms.web.business.custom.util.AccommodationPackageMealPlanDTOMapper;
import com.elephasvacation.tms.web.business.custom.util.MealPlanDTOMapper;
import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.AccommodationPackageMealPlanDAO;
import com.elephasvacation.tms.web.dal.custom.MealPlanDAO;
import com.elephasvacation.tms.web.dto.AccommodationPackageDTO;
import com.elephasvacation.tms.web.dto.AccommodationPackageMealPlanDTO;
import com.elephasvacation.tms.web.dto.MealPlanDTO;
import com.elephasvacation.tms.web.entity.AccommodationPackage;
import com.elephasvacation.tms.web.entity.AccommodationPackageMealPlan;

import javax.persistence.EntityManager;
import java.util.List;

public class AccommodationPackageMealPlanBOImpl implements AccommodationPackageMealPlanBO {

    //    private AccommodationPackageDAO accommodationPackageDAO = DAOFactory.getInstance()
//            .getDAO(DAOTypes.ACCOMMODATION_PACKAGE);
    private AccommodationPackageDTOMapper packageDTOMapper = AccommodationPackageDTOMapper.instance;

    private MealPlanDAO mealPlanDAO = DAOFactory.getInstance()
            .getDAO(DAOTypes.MEAL_PLAN);
    private MealPlanDTOMapper mealPlanDTOMapper = MealPlanDTOMapper.instance;

    private AccommodationPackageMealPlanDAO accommodationPackageMealPlanDAO = DAOFactory.getInstance()
            .getDAO(DAOTypes.MEAL_PLAN_FOR_ACCOMMODATION_PACKAGE);
    private AccommodationPackageMealPlanDTOMapper mapper = AccommodationPackageMealPlanDTOMapper.instance;

    private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;

//        this.accommodationPackageDAO.setEntityManager(this.entityManager);
        this.mealPlanDAO.setEntityManager(this.entityManager);
        this.accommodationPackageMealPlanDAO.setEntityManager(this.entityManager);
    }

    @Override
    public Integer addAccommodationPackageMealPlan(AccommodationPackageMealPlanDTO accommodationPackageMealPlanDTO) throws Exception {
        return this.accommodationPackageMealPlanDAO.save(this.mapper
                .getAccommodationPackageMealPlan(accommodationPackageMealPlanDTO));
    }

//    @Override
//    public void updateMealPlan(AccommodationPackageMealPlanDTO accommodationPackageMealPlanDTO,
//                               AccommodationPackageMealPlanDTO newAccommodationPackageMealPlanDTO) throws Exception {
//        this.accommodationPackageMealPlanDAO.update(this.mapper
//                .getAccommodationPackageMealPlan(newAccommodationPackageMealPlanDTO));
//    }

    @Override
    public void deleteAccommodationPackageMealPlan(AccommodationPackageMealPlanDTO accommodationPackageMealPlanDTO) throws Exception {
        this.accommodationPackageMealPlanDAO
                .delete(this.mapper.getAccommodationPackageMealPlan(accommodationPackageMealPlanDTO).getId());
    }

    @Override
    public MealPlanDTO getAccommodationPackageMealPlan(AccommodationPackageMealPlanDTO pkgMealPlanDTO) throws Exception {
        AccommodationPackageMealPlan pkgMealPlan = this.mapper
                .getAccommodationPackageMealPlan(pkgMealPlanDTO);
        return this.mealPlanDTOMapper.getMealPlanDTO(this.mealPlanDAO.get(pkgMealPlan
                .getId().getMealPlanId()));
    }

    @Override
    public List<AccommodationPackageMealPlanDTO> getAllMealPlansForAccommodationPackage(AccommodationPackageDTO accommodationPackageDTO) {
        /* AccommodationPackageDTO --> AccommodationPackage conversion */
        AccommodationPackage accommodationPackage = this.packageDTOMapper
                .getAccommodationPackage(accommodationPackageDTO);

        /* Find out all the all MealPlans For Accommodation Package */
        List<AccommodationPackageMealPlan> allMealPlansForAccommodationPackage =
                this.accommodationPackageMealPlanDAO.getAllMealPlansForAccommodationPackage(accommodationPackage);

        /* return the List in DTO form. */
        return this.mapper.getAccommodationPackageMealPlanDTOList(allMealPlansForAccommodationPackage);
    }
}
