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
package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.commonConstant.Number;
import com.elephasvacation.tms.web.dal.CrudDAOImpl;
import com.elephasvacation.tms.web.dal.custom.AccommodationPackageRoomTypeDAO;
import com.elephasvacation.tms.web.entity.AccommodationPackage;
import com.elephasvacation.tms.web.entity.AccommodationPackageRoomType;
import com.elephasvacation.tms.web.entity.AccommodationPackageRoomTypeId;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AccommodationPackageRoomTypeDAOImpl
        extends CrudDAOImpl<AccommodationPackageRoomType, AccommodationPackageRoomTypeId>
        implements AccommodationPackageRoomTypeDAO {

    @Override
    public List<AccommodationPackageRoomType>
    getAllPackageRoomTypesForAccommodationPackage(AccommodationPackage accommodationPackage) {
        TypedQuery<AccommodationPackageRoomType> query = this.getEntityManager().
                createQuery("SELECT accRt " +
                                "FROM AccommodationPackageRoomType accRt " +
                                "WHERE accRt.id.accommodationPackageId=?1",
                        AccommodationPackageRoomType.class);

        query.setParameter(Number.ONE, accommodationPackage.getId());

        return query.getResultList();
    }
}
