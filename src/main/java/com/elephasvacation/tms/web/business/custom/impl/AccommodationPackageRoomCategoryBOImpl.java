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

import com.elephasvacation.tms.web.business.custom.AccommodationPackageRoomCategoryBO;
import com.elephasvacation.tms.web.business.custom.util.mapper.AccommodationPackageDTOMapper;
import com.elephasvacation.tms.web.business.custom.util.mapper.AccommodationPackageRoomCategoryDTOMapper;
import com.elephasvacation.tms.web.dal.AccommodationPackageRoomCategoryDAO;
import com.elephasvacation.tms.web.dto.AccommodationPackageDTO;
import com.elephasvacation.tms.web.dto.AccommodationPackageRoomCategoryDTO;
import com.elephasvacation.tms.web.entity.AccommodationPackage;
import com.elephasvacation.tms.web.entity.AccommodationPackageRoomCategory;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NoArgsConstructor
@Transactional
@Service
public class AccommodationPackageRoomCategoryBOImpl implements AccommodationPackageRoomCategoryBO {

    @Autowired
    private AccommodationPackageRoomCategoryDAO packageRoomCategoryDAO;

    @Autowired
    private AccommodationPackageRoomCategoryDTOMapper mapper;

    @Autowired
    private AccommodationPackageDTOMapper packageDTOMapper;


    @Override
    public AccommodationPackageRoomCategoryDTO
    createPackageRoomCategory(AccommodationPackageRoomCategoryDTO packageRoomCategoryDTO) {

        /* convert AccommodationPackageRoomCategoryDTO to entity. */
        AccommodationPackageRoomCategory packageRoomCategory = this.mapper.
                getAccommodationPackageRoomCategory(packageRoomCategoryDTO);

        /* save AccommodationPackageRoomCategory. */
        AccommodationPackageRoomCategory packageRC = this.packageRoomCategoryDAO.save(packageRoomCategory);

        /* convert entity to DTO. */
        AccommodationPackageRoomCategoryDTO savedPackageRoomCategoryDTO = this.mapper.
                getAccommodationPackageRoomCategoryDTO(packageRC);

        return savedPackageRoomCategoryDTO;
    }

    @Override
    public void deletePackageRoomCategory(AccommodationPackageRoomCategoryDTO packageRoomCategoryDTO) {

        /* convert AccommodationPackageRoomCategoryDTO to entity. */
        AccommodationPackageRoomCategory packageRoomCategory = this.mapper.
                getAccommodationPackageRoomCategory(packageRoomCategoryDTO);

        /* delete the AccommodationPackageRoomCategory By ID. */
        this.packageRoomCategoryDAO.deleteById(packageRoomCategory.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccommodationPackageRoomCategoryDTO>
    getAllRoomCategoriesForAccommodationPackage(AccommodationPackageDTO packageDTO) {

        /* convert AccommodationPackageDTO to entity. */
        AccommodationPackage accommodationPackage = this.packageDTOMapper.getAccommodationPackage(packageDTO);

        /* get all room categories for an AccommodationPackage. */
        List<AccommodationPackageRoomCategory> allRoomCategoriesForAccommodationPackage = this.packageRoomCategoryDAO.
                getAllRoomCategoriesForAccommodationPackage(accommodationPackage);

        /* convert allRoomCategoriesForAccommodationPackage to DTOList. */
        List<AccommodationPackageRoomCategoryDTO> accommodationPackageRoomCategoryDTOList = this.mapper.
                getAccommodationPackageRoomCategoryDTOList(allRoomCategoriesForAccommodationPackage);

        return accommodationPackageRoomCategoryDTOList;
    }
}
