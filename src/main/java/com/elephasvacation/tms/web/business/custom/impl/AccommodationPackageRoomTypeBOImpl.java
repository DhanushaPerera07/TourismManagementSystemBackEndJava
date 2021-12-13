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
import com.elephasvacation.tms.web.business.custom.util.AccommodationPackageDTOMapper;
import com.elephasvacation.tms.web.business.custom.util.AccommodationPackageRoomTypeDTOMapper;
import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.AccommodationPackageRoomTypeDAO;
import com.elephasvacation.tms.web.dto.AccommodationPackageDTO;
import com.elephasvacation.tms.web.dto.AccommodationPackageRoomTypeDTO;
import com.elephasvacation.tms.web.entity.AccommodationPackage;
import com.elephasvacation.tms.web.entity.AccommodationPackageRoomType;

import javax.persistence.EntityManager;
import java.util.List;

public class AccommodationPackageRoomTypeBOImpl implements AccommodationPackageRoomTypeBO {

    private final AccommodationPackageRoomTypeDAO packageRoomTypeDAO = DAOFactory.getInstance().
            getDAO(DAOTypes.ROOM_TYPE_FOR_ACCOMMODATION_PACKAGE);
    AccommodationPackageRoomTypeDTOMapper mapper = AccommodationPackageRoomTypeDTOMapper.instance;
    AccommodationPackageDTOMapper packageDTOMapper = AccommodationPackageDTOMapper.instance;
    private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;

        /* Set Entity Manager to the DAL. */
        this.packageRoomTypeDAO.setEntityManager(this.entityManager);
    }

    @Override
    public AccommodationPackageRoomTypeDTO
    createAccommodationPackageRoomType(AccommodationPackageRoomTypeDTO accommodationPackageRoomTypeDTO)
            throws Exception {
        AccommodationPackageRoomType packageRoomType = this.mapper.
                getAccommodationPackageRoomType(accommodationPackageRoomTypeDTO);
        AccommodationPackageRoomType packageRT = this.packageRoomTypeDAO.save(packageRoomType);
        return this.mapper.getAccommodationPackageRoomTypeDTO(packageRT);
    }

    @Override
    public void deleteAccommodationPackageRoomType(AccommodationPackageRoomTypeDTO accommodationPackageRoomTypeDTO)
            throws Exception {
        AccommodationPackageRoomType packageRoomType = this.mapper
                .getAccommodationPackageRoomType(accommodationPackageRoomTypeDTO);
        this.packageRoomTypeDAO.delete(packageRoomType.getId());
    }

    @Override
    public List<AccommodationPackageRoomTypeDTO>
    getAllPackageRoomTypesForAccommodationPackage(AccommodationPackageDTO packageDTO) {
        AccommodationPackage accommodationPackage = this.packageDTOMapper.getAccommodationPackage(packageDTO);
        return this.mapper.getAccommodationPackageRoomTypeDTOList(
                this.packageRoomTypeDAO.getAllPackageRoomTypesForAccommodationPackage(accommodationPackage));
    }
}
