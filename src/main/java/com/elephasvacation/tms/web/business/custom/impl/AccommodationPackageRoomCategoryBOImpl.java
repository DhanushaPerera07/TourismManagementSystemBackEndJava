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
package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.AccommodationPackageRoomCategoryBO;
import com.elephasvacation.tms.web.business.custom.util.AccommodationPackageDTOMapper;
import com.elephasvacation.tms.web.business.custom.util.AccommodationPackageRoomCategoryDTOMapper;
import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.AccommodationPackageRoomCategoryDAO;
import com.elephasvacation.tms.web.dto.AccommodationPackageDTO;
import com.elephasvacation.tms.web.dto.AccommodationPackageRoomCategoryDTO;
import com.elephasvacation.tms.web.entity.AccommodationPackage;
import com.elephasvacation.tms.web.entity.AccommodationPackageRoomCategory;

import javax.persistence.EntityManager;
import java.util.List;

public class AccommodationPackageRoomCategoryBOImpl implements AccommodationPackageRoomCategoryBO {

//    private RoomCategoryDAO roomCategoryDAO = DAOFactory.getInstance()
//            .getDAO(DAOTypes.ROOM_CATEGORY);
//    RoomCategoryDTOMapper roomCategoryDTOMapper = RoomCategoryDTOMapper.instance;

    AccommodationPackageRoomCategoryDTOMapper mapper = AccommodationPackageRoomCategoryDTOMapper.instance;
    AccommodationPackageDTOMapper packageDTOMapper = AccommodationPackageDTOMapper.instance;
    private AccommodationPackageRoomCategoryDAO packageRoomCategoryDAO = DAOFactory.getInstance()
            .getDAO(DAOTypes.ROOM_CATEGORY_FOR_ACCOMMODATION_PACKAGE);
    private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
//        this.roomCategoryDAO.setEntityManager(this.entityManager);
        this.packageRoomCategoryDAO.setEntityManager(this.entityManager);
    }

    @Override
    public Integer createPackageRoomCategory(AccommodationPackageRoomCategoryDTO packageRoomCategoryDTO)
            throws Exception {
        AccommodationPackageRoomCategory packageRoomCategory = this.mapper
                .getAccommodationPackageRoomCategory(packageRoomCategoryDTO);

        return this.packageRoomCategoryDAO.save(packageRoomCategory);
    }

    @Override
    public void deletePackageRoomCategory(AccommodationPackageRoomCategoryDTO packageRoomCategoryDTO)
            throws Exception {
        AccommodationPackageRoomCategory packageRoomCategory =
                this.mapper.getAccommodationPackageRoomCategory(packageRoomCategoryDTO);
        this.packageRoomCategoryDAO.delete(packageRoomCategory.getId());
    }

    @Override
    public List<AccommodationPackageRoomCategoryDTO>
    getAllRoomCategoriesForAccommodationPackage(AccommodationPackageDTO packageDTO) {
        AccommodationPackage accommodationPackage = this.packageDTOMapper.getAccommodationPackage(packageDTO);
        return this.mapper.getAccommodationPackageRoomCategoryDTOList(
                this.packageRoomCategoryDAO.getAllRoomCategoriesForAccommodationPackage(accommodationPackage));
    }
}
