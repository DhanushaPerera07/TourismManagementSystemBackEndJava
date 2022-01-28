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
package com.elephasvacation.tms.web.business.custom.util;

import com.elephasvacation.tms.web.business.custom.util.mapper.AccommodationRateDTOMapper;
import com.elephasvacation.tms.web.dto.AccommodationRateDTO;
import com.elephasvacation.tms.web.entity.AccommodationRate;
import com.elephasvacation.tms.web.entity.AccommodationRateId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class AccommodationRateDTOMapperTest {

    @Autowired
    AccommodationRateDTOMapper mapper;
//    AccommodationRateDTOMapper mapper = AccommodationRateDTOMapper.instance;

    @Test
    public void getAccommodationRateDTO() {
        AccommodationRate accommodationRate = new AccommodationRate(
                new AccommodationRateId(1, 1, 1, 1),
                new BigDecimal("40")
        );

        AccommodationRateDTO accommodationRateDTO =
                this.mapper.getAccommodationRateDTO(accommodationRate);

        assertEquals(new Integer("1"), accommodationRateDTO.getAccommodationRateId().getPkgRoomTypeId());
        assertEquals(new Integer("1"), accommodationRateDTO.getAccommodationRateId().getPkgRoomCategoryId());
        assertEquals(new Integer("1"), accommodationRateDTO.getAccommodationRateId().getPkgMealPlanId());
        assertEquals(new Integer("1"), accommodationRateDTO.getAccommodationRateId().getAccommodationPackageId());
        assertEquals(new BigDecimal("40"), accommodationRateDTO.getRate());
    }

    @Test
    public void getAccommodationRate() {
        BigDecimal rate = new BigDecimal("40");

        AccommodationRateDTO accommodationRateDTO = new AccommodationRateDTO(1,
                1,
                1,
                1,
                rate);

        AccommodationRate accommodationRate = this.mapper.getAccommodationRate(accommodationRateDTO);

        assertEquals(new Integer("1"), accommodationRate.getId().getPkgRoomTypeId());
        assertEquals(new Integer("1"), accommodationRate.getId().getPkgRoomCategoryId());
        assertEquals(new Integer("1"), accommodationRate.getId().getPkgMealPlanId());
        assertEquals(rate, accommodationRate.getRate());

    }
}