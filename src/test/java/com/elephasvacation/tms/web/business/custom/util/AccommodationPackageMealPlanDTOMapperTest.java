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
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.elephasvacation.tms.web.business.custom.util;

import com.elephasvacation.tms.web.business.custom.util.mapper.AccommodationPackageMealPlanDTOMapper;
import com.elephasvacation.tms.web.dto.AccommodationPackageMealPlanDTO;
import com.elephasvacation.tms.web.entity.AccommodationPackageMealPlan;
import com.elephasvacation.tms.web.entity.AccommodationPackageMealPlanId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class AccommodationPackageMealPlanDTOMapperTest {

    @Autowired
    AccommodationPackageMealPlanDTOMapper mapper;

    @Test
    public void getAccommodationPackageMealPlanDTO() {
        AccommodationPackageMealPlan accommodationPackageMealPlan = new AccommodationPackageMealPlan(new AccommodationPackageMealPlanId(1, 2));

        AccommodationPackageMealPlanDTO accommodationPackageMealPlanDTO = this.mapper.getAccommodationPackageMealPlanDTO(accommodationPackageMealPlan);

        assertEquals(new Integer("1"), accommodationPackageMealPlanDTO.getAccommodationPackageId());
        assertEquals(new Integer("2"), accommodationPackageMealPlanDTO.getMealPlanId());

    }

    @Test
    public void getAccommodationPackageMealPlan() {
        AccommodationPackageMealPlanDTO accommodationPackageMealPlanDTO = new AccommodationPackageMealPlanDTO(1, 2);

        AccommodationPackageMealPlan accommodationPackageMealPlan = this.mapper.getAccommodationPackageMealPlan(accommodationPackageMealPlanDTO);

        assertEquals(new Integer("1"), accommodationPackageMealPlan.getId().getAccommodationPackageId());
        assertEquals(new Integer("2"), accommodationPackageMealPlan.getId().getMealPlanId());
    }

}