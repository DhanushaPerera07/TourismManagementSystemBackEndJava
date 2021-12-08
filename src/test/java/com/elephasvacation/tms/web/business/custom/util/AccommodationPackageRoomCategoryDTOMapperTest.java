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

import com.elephasvacation.tms.web.dto.AccommodationPackageRoomCategoryDTO;
import com.elephasvacation.tms.web.entity.AccommodationPackageRoomCategory;
import com.elephasvacation.tms.web.entity.AccommodationPackageRoomCategoryId;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccommodationPackageRoomCategoryDTOMapperTest {

    AccommodationPackageRoomCategoryDTOMapper mapper = AccommodationPackageRoomCategoryDTOMapper.instance;

    @Test
    public void getAccommodationPackageRoomCategoryDTO() {
        AccommodationPackageRoomCategory pkgRoomCategory = new AccommodationPackageRoomCategory(
                new AccommodationPackageRoomCategoryId(1, 1)
        );

        AccommodationPackageRoomCategoryDTO pkgRoomCategoryDTO =
                this.mapper.getAccommodationPackageRoomCategoryDTO(pkgRoomCategory);

        assertEquals(new Integer("1"), pkgRoomCategoryDTO.getAccommodationPackageId());
        assertEquals(new Integer("1"), pkgRoomCategoryDTO.getRoomCategoryId());

    }

    @Test
    public void getAccommodationPackageRoomCategory() {

        AccommodationPackageRoomCategoryDTO pkgRoomCategoryDTO =
                new AccommodationPackageRoomCategoryDTO(1, 1);

        AccommodationPackageRoomCategory pkgRoomCategory =
                this.mapper.getAccommodationPackageRoomCategory(pkgRoomCategoryDTO);

        assertEquals(new Integer("1"), pkgRoomCategory.getId().getAccommodationPackageId());
        assertEquals(new Integer("1"), pkgRoomCategory.getId().getRoomCategoryId());
    }
}