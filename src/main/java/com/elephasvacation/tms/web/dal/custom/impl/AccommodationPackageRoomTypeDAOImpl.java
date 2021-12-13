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

import javax.persistence.TypedQuery;
import java.util.List;

public class AccommodationPackageRoomTypeDAOImpl
        extends CrudDAOImpl<AccommodationPackageRoomType, AccommodationPackageRoomTypeId>
        implements AccommodationPackageRoomTypeDAO {

    /*private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public AccommodationPackageRoomType save(AccommodationPackageRoomType pkgRoomType) throws Exception {
        this.entityManager.persist(pkgRoomType);
        //  call the flush method on EntityManager manually, because we need to get the Generated ID
        this.entityManager.flush();
//        return pkgRoomType.getIndexId(); // TODO: Check the return of the indexID
        return pkgRoomType; // TODO: Check the return of the indexID
    }

    @Override
    public void update(AccommodationPackageRoomType pkgRoomType) throws Exception {
        this.entityManager.merge(pkgRoomType);
    }

    @Override
    public void delete(AccommodationPackageRoomTypeId key) throws Exception {
        this.entityManager.remove(this.entityManager.find(AccommodationPackageRoomType.class, key));
    }

    @Override
    public AccommodationPackageRoomType get(AccommodationPackageRoomTypeId key) throws Exception {
        return this.entityManager.find(AccommodationPackageRoomType.class, key);
    }

    @Override
    public List<AccommodationPackageRoomType> getAll() throws Exception {
        TypedQuery<AccommodationPackageRoomType> selectPkgRoomTypeQuery = this.entityManager
                .createQuery("SELECT accRt FROM AccommodationPackageRoomType accRt",
                        AccommodationPackageRoomType.class);

        return selectPkgRoomTypeQuery.getResultList();
    }*/

    @Override
    public List<AccommodationPackageRoomType> getAllPackageRoomTypesForAccommodationPackage(AccommodationPackage accommodationPackage) {
        TypedQuery<AccommodationPackageRoomType> query = this.getEntityManager().
                createQuery("SELECT accRt " +
                                "FROM AccommodationPackageRoomType accRt " +
                                "WHERE accRt.id.accommodationPackageId=?1",
                        AccommodationPackageRoomType.class);

        query.setParameter(Number.ONE, accommodationPackage.getId());

        return query.getResultList();
    }
}
