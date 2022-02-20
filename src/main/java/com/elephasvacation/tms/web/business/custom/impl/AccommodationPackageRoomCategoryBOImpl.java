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
import com.elephasvacation.tms.web.business.custom.util.mapper.AccommodationPackageRoomCategoryDTOMapper;
import com.elephasvacation.tms.web.business.custom.util.mapper.RoomCategoryDTOMapper;
import com.elephasvacation.tms.web.dal.AccommodationDAO;
import com.elephasvacation.tms.web.dal.AccommodationPackageDAO;
import com.elephasvacation.tms.web.dal.AccommodationPackageRoomCategoryDAO;
import com.elephasvacation.tms.web.dto.AccommodationPackageRoomCategoryDTO;
import com.elephasvacation.tms.web.dto.RoomCategoryDTO;
import com.elephasvacation.tms.web.entity.AccommodationPackageRoomCategory;
import com.elephasvacation.tms.web.entity.RoomCategory;
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
public class AccommodationPackageRoomCategoryBOImpl implements AccommodationPackageRoomCategoryBO {

    @Autowired
    private AccommodationDAO accommodationDAO;

    @Autowired
    private AccommodationPackageDAO accommodationPackageDAO;

    @Autowired
    private AccommodationPackageRoomCategoryDAO packageRoomCategoryDAO;

    @Autowired
    private AccommodationPackageRoomCategoryDTOMapper mapper;

//    @Autowired
//    private AccommodationPackageDTOMapper packageDTOMapper;

    @Autowired
    private RoomCategoryDTOMapper roomCategoryDTOMapper;


    @Override
    public AccommodationPackageRoomCategoryDTO
    addPackageRoomCategory(Integer accommodationId, AccommodationPackageRoomCategoryDTO packageRoomCategoryDTO) {

        /* AccommodationId. */
        checkAccommodationId(accommodationId);

        /* AccommodationPackage. */
        checkAccommodationPackageId(packageRoomCategoryDTO.getAccommodationPackageId());

        /* Let's check whether an AccommodationPackageRoomCategory record already exists or not. */
        checkForAccommodationPackageRoomCategoryRecord(packageRoomCategoryDTO);

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
    public void deletePackageRoomCategory(Integer accommodationId, AccommodationPackageRoomCategoryDTO packageRoomCategoryDTO) {

        /* AccommodationId. */
        checkAccommodationId(accommodationId);

        /* AccommodationPackage. */
        checkAccommodationPackageId(packageRoomCategoryDTO.getAccommodationPackageId());

        /* convert AccommodationPackageRoomCategoryDTO to entity. */
        AccommodationPackageRoomCategory packageRoomCategory = this.mapper.getAccommodationPackageRoomCategory(packageRoomCategoryDTO);
        if (!this.packageRoomCategoryDAO.existsById(packageRoomCategory.getId()))
            throw new BadRequestException("No matching Room Category record found under given accommodation package. AccommodationPackageRoomCategory ID: " + packageRoomCategory.getId());

        /* delete the AccommodationPackageRoomCategory By ID. */
        this.packageRoomCategoryDAO.deleteById(packageRoomCategory.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomCategoryDTO> getAllRoomCategoriesForAccommodationPackage(Integer accommodationId, Integer accommodationPackageId) {

        checkAccommodationId(accommodationId);

        checkAccommodationPackageId(accommodationPackageId);

        /* Get all room categories for an accommodation package. */
        List<RoomCategory> entityList = this.packageRoomCategoryDAO.
                getAllRoomCategoriesForAccommodationPackage(accommodationPackageId);

        return this.roomCategoryDTOMapper.getRoomCategoryDTOs(entityList);
    }


    private void checkAccommodationId(Integer accommodationId) {
        /* Check Accommodation ID. If Accommodation is not found. */
        if (!this.accommodationDAO.existsById(accommodationId))
            throw new RecordNotFoundException("No matching Accommodation record found for ID: " + accommodationId);
    }

    private void checkAccommodationPackageId(Integer accommodationPackageId) {
        /* Check Accommodation Package ID. If not found. */
        if (!this.accommodationPackageDAO.existsById(accommodationPackageId))
            throw new RecordNotFoundException("No matching Accommodation Package record found for ID: " + accommodationPackageId);

    }

    private void checkForAccommodationPackageRoomCategoryRecord(AccommodationPackageRoomCategoryDTO accommodationPackageRoomCategoryDto) {
        AccommodationPackageRoomCategory packageRoomCategory = this.mapper.getAccommodationPackageRoomCategory(accommodationPackageRoomCategoryDto);
        if (this.packageRoomCategoryDAO.existsById(packageRoomCategory.getId()))
            throw new BadRequestException("Record already exists for ID: " + packageRoomCategory.getId());
    }
}
