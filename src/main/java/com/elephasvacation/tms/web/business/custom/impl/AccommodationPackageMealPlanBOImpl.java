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
import com.elephasvacation.tms.web.dal.AccommodationPackageMealPlanDAO;
import com.elephasvacation.tms.web.dal.MealPlanDAO;
import com.elephasvacation.tms.web.dto.AccommodationPackageDTO;
import com.elephasvacation.tms.web.dto.AccommodationPackageMealPlanDTO;
import com.elephasvacation.tms.web.dto.MealPlanDTO;
import com.elephasvacation.tms.web.entity.AccommodationPackage;
import com.elephasvacation.tms.web.entity.AccommodationPackageMealPlan;
import com.elephasvacation.tms.web.entity.AccommodationPackageMealPlanId;
import com.elephasvacation.tms.web.entity.MealPlan;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NoArgsConstructor
@Transactional
@Service
public class AccommodationPackageMealPlanBOImpl implements AccommodationPackageMealPlanBO {

    @Autowired
    private MealPlanDAO mealPlanDAO;

    @Autowired
    private AccommodationPackageMealPlanDAO accommodationPackageMealPlanDAO;

    @Autowired
    private AccommodationPackageDTOMapper packageDTOMapper;

    @Autowired
    private MealPlanDTOMapper mealPlanDTOMapper;


    @Autowired
    private AccommodationPackageMealPlanDTOMapper mapper;

    @Override
    public AccommodationPackageMealPlanDTO
    addAccommodationPackageMealPlan(AccommodationPackageMealPlanDTO accommodationPackageMealPlanDTO) {

        /* convert DTO to AccommodationPackageMealPlan. */
        AccommodationPackageMealPlan accommodationPackageMealPlan = this.mapper.
                getAccommodationPackageMealPlan(accommodationPackageMealPlanDTO);

        /* add/save AccommodationPackageMealPlan. */
        AccommodationPackageMealPlan addedPkgMealPlan = this.accommodationPackageMealPlanDAO
                .save(accommodationPackageMealPlan);

        /* convert entity to addedPkgMealPlanDTO. */
        return this.mapper.
                getAccommodationPackageMealPlanDTO(addedPkgMealPlan);
    }

    @Override
    public void deleteAccommodationPackageMealPlan(AccommodationPackageMealPlanDTO accommodationPackageMealPlanDTO) {

        /* convert DTO to entity, and get the AccommodationPackageMealPlanId. */
        AccommodationPackageMealPlanId packageMealPlanId = this.mapper.
                getAccommodationPackageMealPlan(accommodationPackageMealPlanDTO).getId();

        /* delete AccommodationPackageMealPlan By ID. */
        this.accommodationPackageMealPlanDAO.deleteById(packageMealPlanId);

    }

    @Transactional(readOnly = true)
    @Override
    public MealPlanDTO getAccommodationPackageMealPlan(AccommodationPackageMealPlanDTO pkgMealPlanDTO) {

        /* convert AccommodationPackageMealPlanDTO to entity. */
        AccommodationPackageMealPlan pkgMealPlan = this.mapper.getAccommodationPackageMealPlan(pkgMealPlanDTO);

        /* get MealPlan */
        MealPlan mealPlan = this.mealPlanDAO.getById(pkgMealPlan.getId().getMealPlanId());

        /* convert mealPlan entity to DTO. */
        return this.mealPlanDTOMapper.getMealPlanDTO(mealPlan);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AccommodationPackageMealPlanDTO>
    getAllMealPlansForAccommodationPackage(AccommodationPackageDTO packageDTO) {

        /* AccommodationPackageDTO --> AccommodationPackage conversion */
        AccommodationPackage accommodationPackage = this.packageDTOMapper
                .getAccommodationPackage(packageDTO);

        /* Find out all the all MealPlans For Accommodation Package */
        List<AccommodationPackageMealPlan> allMealPlansForAccommodationPackage =
                this.accommodationPackageMealPlanDAO.getAllMealPlansForAccommodationPackage(accommodationPackage);

        /* convert entityList to DTOList. */
        /* return the List in DTO form. */
        return this.mapper.
                getAccommodationPackageMealPlanDTOList(allMealPlansForAccommodationPackage);
    }
}
