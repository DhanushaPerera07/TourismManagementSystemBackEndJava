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
package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.AccommodationPackageMealPlanBO;
import com.elephasvacation.tms.web.business.custom.util.mapper.AccommodationPackageDTOMapper;
import com.elephasvacation.tms.web.business.custom.util.mapper.AccommodationPackageMealPlanDTOMapper;
import com.elephasvacation.tms.web.business.custom.util.mapper.MealPlanDTOMapper;
import com.elephasvacation.tms.web.dal.custom.AccommodationPackageMealPlanDAO;
import com.elephasvacation.tms.web.dal.custom.MealPlanDAO;
import com.elephasvacation.tms.web.dto.AccommodationPackageDTO;
import com.elephasvacation.tms.web.dto.AccommodationPackageMealPlanDTO;
import com.elephasvacation.tms.web.dto.MealPlanDTO;
import com.elephasvacation.tms.web.entity.AccommodationPackage;
import com.elephasvacation.tms.web.entity.AccommodationPackageMealPlan;
import com.elephasvacation.tms.web.entity.AccommodationPackageMealPlanId;
import com.elephasvacation.tms.web.entity.MealPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class AccommodationPackageMealPlanBOImpl implements AccommodationPackageMealPlanBO {

    @Autowired
    private AccommodationPackageDTOMapper packageDTOMapper;

    @Autowired
    private MealPlanDAO mealPlanDAO;

    @Autowired
    private MealPlanDTOMapper mealPlanDTOMapper;

    @Autowired
    private AccommodationPackageMealPlanDAO accommodationPackageMealPlanDAO;

    @Autowired
    private AccommodationPackageMealPlanDTOMapper mapper;

    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;

        /* Set Entity Manager to the DAL. */
        this.mealPlanDAO.setEntityManager(this.entityManager);
        this.accommodationPackageMealPlanDAO.setEntityManager(this.entityManager);
    }

    @Override
    public AccommodationPackageMealPlanDTO
    addAccommodationPackageMealPlan(AccommodationPackageMealPlanDTO accommodationPackageMealPlanDTO) throws Exception {
        this.entityManager.getTransaction().begin();

        /* convert DTO to AccommodationPackageMealPlan. */
        AccommodationPackageMealPlan accommodationPackageMealPlan = this.mapper.
                getAccommodationPackageMealPlan(accommodationPackageMealPlanDTO);

        /* add/save AccommodationPackageMealPlan. */
        AccommodationPackageMealPlan addedPkgMealPlan = this.accommodationPackageMealPlanDAO
                .save(accommodationPackageMealPlan);

        /* convert entity to addedPkgMealPlanDTO. */
        AccommodationPackageMealPlanDTO addedPkgMealPlanDTO = this.mapper.
                getAccommodationPackageMealPlanDTO(addedPkgMealPlan);
        this.entityManager.getTransaction().commit();
        return addedPkgMealPlanDTO;
    }

    @Override
    public void deleteAccommodationPackageMealPlan(AccommodationPackageMealPlanDTO accommodationPackageMealPlanDTO)
            throws Exception {
        this.entityManager.getTransaction().begin();

        /* convert DTO to entity, and get the AccommodationPackageMealPlanId. */
        AccommodationPackageMealPlanId packageMealPlanId = this.mapper.
                getAccommodationPackageMealPlan(accommodationPackageMealPlanDTO).getId();

        /* delete AccommodationPackageMealPlan By ID. */
        this.accommodationPackageMealPlanDAO.delete(packageMealPlanId);

        this.entityManager.getTransaction().commit();
    }

    @Override
    public MealPlanDTO getAccommodationPackageMealPlan(AccommodationPackageMealPlanDTO pkgMealPlanDTO)
            throws Exception {
        this.entityManager.getTransaction().begin();

        /* convert AccommodationPackageMealPlanDTO to entity. */
        AccommodationPackageMealPlan pkgMealPlan = this.mapper.getAccommodationPackageMealPlan(pkgMealPlanDTO);

        /* get MealPlan */
        MealPlan mealPlan = this.mealPlanDAO.get(pkgMealPlan.getId().getMealPlanId());

        /* convert mealPlan entity to DTO. */
        MealPlanDTO mealPlanDTO = this.mealPlanDTOMapper.getMealPlanDTO(mealPlan);

        this.entityManager.getTransaction().commit();
        return mealPlanDTO;
    }

    @Override
    public List<AccommodationPackageMealPlanDTO>
    getAllMealPlansForAccommodationPackage(AccommodationPackageDTO packageDTO) {
        this.entityManager.getTransaction().begin();

        /* AccommodationPackageDTO --> AccommodationPackage conversion */
        AccommodationPackage accommodationPackage = this.packageDTOMapper
                .getAccommodationPackage(packageDTO);

        /* Find out all the all MealPlans For Accommodation Package */
        List<AccommodationPackageMealPlan> allMealPlansForAccommodationPackage =
                this.accommodationPackageMealPlanDAO.getAllMealPlansForAccommodationPackage(accommodationPackage);

        /* convert entityList to DTOList. */
        List<AccommodationPackageMealPlanDTO> packageMealPlanDTOList = this.mapper.
                getAccommodationPackageMealPlanDTOList(allMealPlansForAccommodationPackage);

        this.entityManager.getTransaction().commit();

        /* return the List in DTO form. */
        return packageMealPlanDTOList;
    }
}
