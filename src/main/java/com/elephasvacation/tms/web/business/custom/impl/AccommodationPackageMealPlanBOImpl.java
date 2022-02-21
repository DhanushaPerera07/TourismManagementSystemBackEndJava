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
import com.elephasvacation.tms.web.business.custom.util.mapper.AccommodationPackageMealPlanDTOMapper;
import com.elephasvacation.tms.web.business.custom.util.mapper.MealPlanDTOMapper;
import com.elephasvacation.tms.web.dal.AccommodationDAO;
import com.elephasvacation.tms.web.dal.AccommodationPackageDAO;
import com.elephasvacation.tms.web.dal.AccommodationPackageMealPlanDAO;
import com.elephasvacation.tms.web.dal.MealPlanDAO;
import com.elephasvacation.tms.web.dto.AccommodationPackageMealPlanDTO;
import com.elephasvacation.tms.web.dto.MealPlanDTO;
import com.elephasvacation.tms.web.entity.AccommodationPackage;
import com.elephasvacation.tms.web.entity.AccommodationPackageMealPlan;
import com.elephasvacation.tms.web.entity.AccommodationPackageMealPlanId;
import com.elephasvacation.tms.web.entity.MealPlan;
import com.elephasvacation.tms.web.exception.BadRequestException;
import com.elephasvacation.tms.web.exception.RecordNotFoundException;
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
    private AccommodationDAO accommodationDAO;

    @Autowired
    private AccommodationPackageDAO accommodationPackageDAO;

    @Autowired
    private MealPlanDAO mealPlanDAO;

    @Autowired
    private AccommodationPackageMealPlanDAO accommodationPackageMealPlanDAO;

    @Autowired
    private MealPlanDTOMapper mealPlanDTOMapper;


    @Autowired
    private AccommodationPackageMealPlanDTOMapper mapper;

    @Override
    public AccommodationPackageMealPlanDTO
    addAccommodationPackageMealPlan(Integer accommodationId,
                                    AccommodationPackageMealPlanDTO packageMealPlanDTO) {

        /* validation. */
        checkForIDs(accommodationId, packageMealPlanDTO);

        /* Let's check whether an AccommodationPackageMealPlan record already exists or not. */
        checkForAccommodationPackageMealPlanRecord(packageMealPlanDTO);

        /* convert DTO to AccommodationPackageMealPlan. */
        AccommodationPackageMealPlan accommodationPackageMealPlan = this.mapper.
                getAccommodationPackageMealPlan(packageMealPlanDTO);

        /* add/save AccommodationPackageMealPlan. */
        AccommodationPackageMealPlan addedPkgMealPlan = this.accommodationPackageMealPlanDAO
                .save(accommodationPackageMealPlan);

        /* convert entity to addedPkgMealPlanDTO. */
        return this.mapper.
                getAccommodationPackageMealPlanDTO(addedPkgMealPlan);
    }

    @Override
    public void deleteAccommodationPackageMealPlan(Integer accommodationId,
                                                   AccommodationPackageMealPlanDTO packageMealPlanDTO) {
        /* validation. */
        checkForIDs(accommodationId, packageMealPlanDTO);

        /* convert DTO to entity, and get the AccommodationPackageMealPlanId. */
        AccommodationPackageMealPlanId packageMealPlanId = this.mapper.
                getAccommodationPackageMealPlan(packageMealPlanDTO).getId();

        /* delete AccommodationPackageMealPlan By ID. */
        this.accommodationPackageMealPlanDAO.deleteById(packageMealPlanId);

    }

    @Transactional(readOnly = true)
    @Override
    public List<MealPlanDTO>
    getAllMealPlansForAccommodationPackage(Integer accommodationId, Integer accommodationPackageId) {

        /* Check Accommodation ID. If Accommodation is not found. */
        if (!this.accommodationDAO.existsById(accommodationId))
            throw new RecordNotFoundException("No matching Accommodation record found for ID: " + accommodationId);

        /* Check Accommodation Package ID. If not found. */
        AccommodationPackage accommodationPackage = this.accommodationPackageDAO
                .findById(accommodationPackageId)
                .orElseThrow(() -> new RecordNotFoundException("No matching Accommodation Package record found for ID: " +
                        accommodationPackageId));

        /* Find out all the all MealPlans For Accommodation Package */
        /* TODO: let's get the required details using a custom query. */
        List<MealPlan> allMealPlansForAccommodationPackage = this.accommodationPackageMealPlanDAO
                .getAllMealPlansForAccommodationPackage(accommodationPackage);

        /* MealPlan entities to MealPlanDTOList  conversion. */
        return this.mealPlanDTOMapper.getMealPlanDTOList(allMealPlansForAccommodationPackage);
    }

    private void checkForIDs(Integer accommodationId,
                             AccommodationPackageMealPlanDTO packageMealPlanDTO) {
        /* Check Accommodation ID. If Accommodation is not found. */
        if (!this.accommodationDAO.existsById(accommodationId))
            throw new RecordNotFoundException("No matching Accommodation record found for ID: " + accommodationId);

        /* Check Accommodation Package ID. If not found. */
        if (!this.accommodationPackageDAO.existsById(packageMealPlanDTO.getAccommodationPackageId()))
            throw new RecordNotFoundException("No matching Accommodation Package record found for ID: " +
                    packageMealPlanDTO.getAccommodationPackageId());

        /* Check Meal Plan ID. If not found. */
        if (!this.mealPlanDAO.existsById(packageMealPlanDTO.getMealPlanId()))
            throw new RecordNotFoundException("No matching Meal Plan record found for ID: " +
                    packageMealPlanDTO.getMealPlanId());
    }

    private void checkForAccommodationPackageMealPlanRecord(AccommodationPackageMealPlanDTO accommodationPackageMealPlanDTO) {
        AccommodationPackageMealPlan packageMealPlan = this.mapper.getAccommodationPackageMealPlan(accommodationPackageMealPlanDTO);
        if (this.accommodationPackageMealPlanDAO.existsById(packageMealPlan.getId()))
            throw new BadRequestException("Record already exists for ID: " + packageMealPlan.getId());
    }
}
