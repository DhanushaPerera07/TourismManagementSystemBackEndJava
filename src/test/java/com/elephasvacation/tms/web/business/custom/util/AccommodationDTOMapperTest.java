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
package com.elephasvacation.tms.web.business.custom.util;

import com.elephasvacation.tms.web.business.custom.util.mapper.AccommodationDTOMapper;
import com.elephasvacation.tms.web.dto.AccommodationDTO;
import com.elephasvacation.tms.web.entity.Accommodation;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class AccommodationDTOMapperTest {
    @Autowired
    AccommodationDTOMapper mapper;

    @Test
    public void getAccommodation() {
        AccommodationDTO accommodationDTO = new AccommodationDTO("ABC",
                "Colombo",
                4,
                "Hotel",
                "011212345667",
                "abc@hotel.com",
                "Colombo",
                "abc.hotels.com",
                "None",
                "None");

        Accommodation accommodation = this.mapper.getAccommodation(accommodationDTO);

        Assert.assertEquals("ABC", accommodation.getName());
        Assert.assertEquals(new Integer(4), accommodation.getStarRating());
        Assert.assertEquals("Hotel", accommodation.getType());
        Assert.assertEquals("011212345667", accommodation.getContact());
        Assert.assertEquals("abc@hotel.com", accommodation.getEmail());
        Assert.assertEquals("Colombo", accommodation.getAddress());
    }
}