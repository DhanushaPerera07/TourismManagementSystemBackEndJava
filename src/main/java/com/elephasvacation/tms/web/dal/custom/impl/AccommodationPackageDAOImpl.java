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
/*
 * @author : Dhanusha Perera
 * @date : 26/07/2021
 */
package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.commonConstant.Number;
import com.elephasvacation.tms.web.dal.CrudDAOImpl;
import com.elephasvacation.tms.web.dal.custom.AccommodationPackageDAO;
import com.elephasvacation.tms.web.entity.AccommodationPackage;

import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

public class AccommodationPackageDAOImpl extends CrudDAOImpl<AccommodationPackage, Integer> implements AccommodationPackageDAO {

    //    private EntityManager entityManager;
//
//    @Override
//    public void setEntityManager(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
//
//
//    // CrudDAO ======================================================================================================
//
//    @Override
//    public AccommodationPackage save(AccommodationPackage accommodationPackage) throws Exception {
//        this.entityManager.persist(accommodationPackage);
//        //  call the flush method on EntityManager manually, because we need to get the Generated ID
//        this.entityManager.flush();
////        return accommodationPackage.getId();
//        return accommodationPackage;
//    }
//
//    @Override
//    public void update(AccommodationPackage accommodationPackage) throws Exception {
//        this.entityManager.merge(accommodationPackage);
//    }
//
//    @Override
//    public void delete(Integer key) throws Exception {
//        this.entityManager.remove(this.entityManager.find(AccommodationPackage.class, key));
//    }
//
//    @Override
//    public AccommodationPackage get(Integer key) throws Exception {
//        return this.entityManager.find(AccommodationPackage.class, key);
//    }
//
//    @Override
//    public List<AccommodationPackage> getAll() throws Exception {
//        Query allAccommodationPackage = this.entityManager.createQuery("SELECT pkg FROM AccommodationPackage pkg");
//        return (List<AccommodationPackage>) allAccommodationPackage.getResultList();
//    }

    // AccommodationPackageDAO ======================================================================================

    @Override
    public List<AccommodationPackage> getAllAccommodationPackagesByAccommodationID(Integer accommodationID)
            throws SQLException {
        // TODO: Tried to use JPA Query Parameters
        Query allAccommodationPackage = this.getEntityManager().
                createQuery("SELECT pkg FROM AccommodationPackage pkg WHERE pkg.accommodation.id=?1");
        allAccommodationPackage.setParameter(Number.ONE, accommodationID);
        return (List<AccommodationPackage>) allAccommodationPackage.getResultList();
    }
}
