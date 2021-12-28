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

import com.elephasvacation.tms.web.business.custom.util.mapper.AccommodationPackageDTOMapper;
import com.elephasvacation.tms.web.dto.AccommodationPackageDTO;
import com.elephasvacation.tms.web.entity.AccommodationPackage;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccommodationPackageDTOMapperTest {
    AccommodationPackageDTOMapper mapper = AccommodationPackageDTOMapper.instance;



    @Test
    public void getAccommodationPackage() {
        AccommodationPackageDTO accommodationPackageDTO = new AccommodationPackageDTO(2021,
                "Summer",
                "Summer",
                1);

        AccommodationPackage accommodationPackage = this.mapper.getAccommodationPackage(accommodationPackageDTO);

        assertEquals(new Integer("2021"), accommodationPackage.getYear());
        assertEquals("Summer", accommodationPackage.getSeason());
        assertEquals(new Integer("1"), accommodationPackage.getAccommodation().getId());

    }

    @Test
    public void getAccommodationPackageDTO() {
    }

    @Test
    public void getAccommodationPackageDTOList() {
    }
}