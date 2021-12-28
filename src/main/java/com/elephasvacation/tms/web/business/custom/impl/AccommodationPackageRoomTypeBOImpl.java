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
import com.elephasvacation.tms.web.dal.custom.AccommodationPackageRoomTypeDAO;
import com.elephasvacation.tms.web.dto.AccommodationPackageDTO;
import com.elephasvacation.tms.web.dto.AccommodationPackageRoomTypeDTO;
import com.elephasvacation.tms.web.entity.AccommodationPackage;
import com.elephasvacation.tms.web.entity.AccommodationPackageRoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class AccommodationPackageRoomTypeBOImpl implements AccommodationPackageRoomTypeBO {

    @Autowired
    private AccommodationPackageRoomTypeDAO packageRoomTypeDAO;

    @Autowired
    private AccommodationPackageRoomTypeDTOMapper mapper;

    @Autowired
    private AccommodationPackageDTOMapper packageDTOMapper;

    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

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
        this.entityManager.getTransaction().begin();

        /* convert AccommodationPackageRoomTypeDTO to AccommodationPackageRoomType entity. */
        AccommodationPackageRoomType packageRoomType = this.mapper.
                getAccommodationPackageRoomType(accommodationPackageRoomTypeDTO);

        /* save AccommodationPackageRoomType. */
        AccommodationPackageRoomType savedPackageRT = this.packageRoomTypeDAO.save(packageRoomType);

        /* convert AccommodationPackageRoomType entity to AccommodationPackageRoomTypeDTO. */
        AccommodationPackageRoomTypeDTO savedPackageRoomTypeDTO = this.mapper.
                getAccommodationPackageRoomTypeDTO(savedPackageRT);

        this.entityManager.getTransaction().commit();
        return savedPackageRoomTypeDTO;
    }

    @Override
    public void deleteAccommodationPackageRoomType(AccommodationPackageRoomTypeDTO accommodationPackageRoomTypeDTO)
            throws Exception {
        this.entityManager.getTransaction().begin();

        /* convert AccommodationPackageRoomTypeDTO to entity. */
        AccommodationPackageRoomType packageRoomType = this.mapper.
                getAccommodationPackageRoomType(accommodationPackageRoomTypeDTO);

        /* delete AccommodationPackageRoomType By ID. */
        this.packageRoomTypeDAO.delete(packageRoomType.getId());

        this.entityManager.getTransaction().commit();
    }

    /**
     * Get all room types for an Accommodation Package.
     *
     * @return List<AccommodationPackageRoomTypeDTO> accommodationPackageRoomTypeDTOList
     */
    @Override
    public List<AccommodationPackageRoomTypeDTO>
    getAllPackageRoomTypesForAccommodationPackage(AccommodationPackageDTO packageDTO) {
        this.entityManager.getTransaction().begin();

        /* convert AccommodationPackageDTO to entity. */
        AccommodationPackage accommodationPackage = this.packageDTOMapper.getAccommodationPackage(packageDTO);

        /* get all room types for an accommodation package. */
        List<AccommodationPackageRoomType> allPackageRoomTypesForAccommodationPackage = this.packageRoomTypeDAO.
                getAllPackageRoomTypesForAccommodationPackage(accommodationPackage);

        /* convert allPackageRoomTypesForAccommodationPackage to accommodationPackageRoomTypeDTOList. */
        List<AccommodationPackageRoomTypeDTO> accommodationPackageRoomTypeDTOList = this.mapper.
                getAccommodationPackageRoomTypeDTOList(allPackageRoomTypesForAccommodationPackage);

        this.entityManager.getTransaction().commit();
        return accommodationPackageRoomTypeDTOList;
    }
}
