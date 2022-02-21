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

import com.elephasvacation.tms.web.business.custom.AccommodationPackageRoomTypeBO;
import com.elephasvacation.tms.web.business.custom.util.mapper.AccommodationPackageDTOMapper;
import com.elephasvacation.tms.web.business.custom.util.mapper.AccommodationPackageRoomTypeDTOMapper;
import com.elephasvacation.tms.web.business.custom.util.mapper.RoomTypeDTOMapper;
import com.elephasvacation.tms.web.dal.AccommodationDAO;
import com.elephasvacation.tms.web.dal.AccommodationPackageDAO;
import com.elephasvacation.tms.web.dal.AccommodationPackageRoomTypeDAO;
import com.elephasvacation.tms.web.dal.RoomTypeDAO;
import com.elephasvacation.tms.web.dto.AccommodationPackageDTO;
import com.elephasvacation.tms.web.dto.AccommodationPackageRoomTypeDTO;
import com.elephasvacation.tms.web.dto.RoomTypeDTO;
import com.elephasvacation.tms.web.entity.AccommodationPackage;
import com.elephasvacation.tms.web.entity.AccommodationPackageRoomType;
import com.elephasvacation.tms.web.entity.RoomType;
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
public class AccommodationPackageRoomTypeBOImpl implements AccommodationPackageRoomTypeBO {

    @Autowired
    private AccommodationDAO accommodationDAO;

    @Autowired
    private RoomTypeDAO roomTypeDAO;

    @Autowired
    private AccommodationPackageDAO accommodationPackageDAO;

    @Autowired
    private AccommodationPackageRoomTypeDAO packageRoomTypeDAO;

    @Autowired
    private AccommodationPackageRoomTypeDTOMapper mapper;

    @Autowired
    private AccommodationPackageDTOMapper packageDTOMapper;

    @Autowired
    private RoomTypeDTOMapper roomTypeDTOMapper;


    @Override
    public AccommodationPackageRoomTypeDTO
    addAccommodationPackageRoomType(Integer accommodationId,
                                    AccommodationPackageRoomTypeDTO packageRoomTypeDTO) {

        /* AccommodationId. */
        checkAccommodationId(accommodationId);

        /* AccommodationPackage. */
        checkAccommodationPackageId(packageRoomTypeDTO.getAccommodationPackageId());

        /* Check for a valid Room Type ID. */
        checkForRoomType(packageRoomTypeDTO.getRoomTypeId());

        /* Let's check whether an AccommodationPackageRoomType record already exists or not. */
        checkForAccommodationPackageRoomTypeRecord(packageRoomTypeDTO);

        /* convert AccommodationPackageRoomTypeDTO to AccommodationPackageRoomType entity. */
        AccommodationPackageRoomType packageRoomType = this.mapper.
                getAccommodationPackageRoomType(packageRoomTypeDTO);

        /* save AccommodationPackageRoomType. */
        AccommodationPackageRoomType savedPackageRT = this.packageRoomTypeDAO.save(packageRoomType);

        /* convert AccommodationPackageRoomType entity to AccommodationPackageRoomTypeDTO. */
        return this.mapper.
                getAccommodationPackageRoomTypeDTO(savedPackageRT);
    }

    @Override
    public void deleteAccommodationPackageRoomType(Integer accommodationId,
                                                   AccommodationPackageRoomTypeDTO packageRoomTypeDTO) {

        /* AccommodationId. */
        checkAccommodationId(accommodationId);

        /* AccommodationPackage. */
        checkAccommodationPackageId(packageRoomTypeDTO.getAccommodationPackageId());

        /* Check for a valid Room Type ID. */
        checkForRoomType(packageRoomTypeDTO.getRoomTypeId());

        /* convert AccommodationPackageRoomTypeDTO to entity. */
        AccommodationPackageRoomType packageRoomType = this.mapper.
                getAccommodationPackageRoomType(packageRoomTypeDTO);

        if (!this.packageRoomTypeDAO.existsById(packageRoomType.getId()))
            throw new BadRequestException("No matching Room Type record found under given accommodation package. AccommodationPackageRoomCategory ID: " + packageRoomType.getId());

        /* delete AccommodationPackageRoomType By ID. */
        this.packageRoomTypeDAO.deleteById(packageRoomType.getId());
    }

    /**
     * Get all room types for an Accommodation Package.
     *
     * @return List<AccommodationPackageRoomTypeDTO> accommodationPackageRoomTypeDTOList
     */
    @Transactional(readOnly = true)
    @Override
    public List<AccommodationPackageRoomTypeDTO>
    getAllPackageRoomTypesForAccommodationPackage(AccommodationPackageDTO packageDTO) {

        /* convert AccommodationPackageDTO to entity. */
        AccommodationPackage accommodationPackage = this.packageDTOMapper.getAccommodationPackage(packageDTO);

        /* get all room types for an accommodation package. */
        List<AccommodationPackageRoomType> allPackageRoomTypesForAccommodationPackage = this.packageRoomTypeDAO.
                getAllPackageRoomTypesForAccommodationPackage(accommodationPackage);

        /* convert allPackageRoomTypesForAccommodationPackage to accommodationPackageRoomTypeDTOList. */
        return this.mapper.
                getAccommodationPackageRoomTypeDTOList(allPackageRoomTypesForAccommodationPackage);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomTypeDTO> getAllPackageRoomTypesForAccommodationPackage(Integer accommodationId,
                                                                           Integer accommodationPackageId) {

        /* validation. */
        checkAccommodationId(accommodationId);

        checkAccommodationPackageId(accommodationPackageId);

        /* Get all package room types for an accommodation package. */
        List<RoomType> allPackageRoomTypesForAccommodationPackage = this.packageRoomTypeDAO.
                getAllPackageRoomTypesForAccommodationPackage(accommodationPackageId);

        return this.roomTypeDTOMapper.getRoomTypeDTOs(allPackageRoomTypesForAccommodationPackage);
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

    private void checkForRoomType(Integer roomTypeId) {
        if (!this.roomTypeDAO.existsById(roomTypeId))
            throw new RecordNotFoundException("No matching Room Type record found for ID: " + roomTypeId);
    }

    private void checkForAccommodationPackageRoomTypeRecord(AccommodationPackageRoomTypeDTO accommodationPackageRoomTypeDTO) {
        AccommodationPackageRoomType packageRoomType = this.mapper.getAccommodationPackageRoomType(accommodationPackageRoomTypeDTO);
        if (this.packageRoomTypeDAO.existsById(packageRoomType.getId()))
            throw new BadRequestException("Record already exists for ID: " + packageRoomType.getId());
    }
}
