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
/*
 * @author : Dhanusha Perera
 * @date : 27/07/2021
 */
package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.commonConstant.Number;
import com.elephasvacation.tms.web.dal.custom.AccommodationRateDAO;
import com.elephasvacation.tms.web.entity.AccommodationRate;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

public class AccommodationRateDAOImpl implements AccommodationRateDAO {

    private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // CrudDAO ===================================================================================

    @Override
    public Integer save(AccommodationRate accommodationRate) throws Exception {
        this.entityManager.persist(accommodationRate);
        //  call the flush method on EntityManager manually, because we need to get the Generated ID
        this.entityManager.flush();
        // TODO: Check the returning: getAccommodationPackageId
        return accommodationRate.getId().getAccommodationPackageId();
    }

    @Override
    public void update(AccommodationRate accommodationRate) throws Exception {
        this.entityManager.merge(accommodationRate);
    }

    @Override
    public void delete(Integer key) throws Exception {
        this.entityManager.remove(this.entityManager.find(AccommodationRate.class, key));
    }

    @Override
    public AccommodationRate get(Integer key) throws Exception {
        return this.entityManager.find(AccommodationRate.class, key);
    }

    @Override
    public List<AccommodationRate> getAll() throws Exception {
        TypedQuery<AccommodationRate> selectAccommodationRateTypedQuery =
                this.entityManager.createQuery("SELECT ar FROM AccommodationRate ar", AccommodationRate.class);

        return selectAccommodationRateTypedQuery.getResultList();
    }

    // AccommodationRateDAO ===================================================================================

    @Override
    public List<AccommodationRate> getAllAccommodationRatesByAccommodationPackageID(Integer accommodationPackageID)
            throws SQLException {

        TypedQuery<AccommodationRate> selectAccommodationRateByAccommodationPackageTypedQuery =
                this.entityManager
                        .createQuery("SELECT ar FROM AccommodationRate ar WHERE ar.id.accommodationPackageId=?1",
                                AccommodationRate.class);

        selectAccommodationRateByAccommodationPackageTypedQuery.setParameter(Number.ONE, accommodationPackageID);

        return selectAccommodationRateByAccommodationPackageTypedQuery.getResultList();
    }
}
