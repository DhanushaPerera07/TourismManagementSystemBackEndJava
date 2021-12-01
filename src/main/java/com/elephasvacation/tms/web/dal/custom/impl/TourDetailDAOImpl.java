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
 * @date : 04/07/2021
 */
package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.commonConstant.Number;
import com.elephasvacation.tms.web.dal.custom.TourDetailDAO;
import com.elephasvacation.tms.web.entity.TourDetail;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class TourDetailDAOImpl implements TourDetailDAO {

    private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Integer save(TourDetail tourDetail) throws Exception {
        this.entityManager.persist(tourDetail);
        //  call the flush method on EntityManager manually, because we need to get the Generated ID
        this.entityManager.flush();
        return tourDetail.getId();
    }

    @Override
    public void update(TourDetail tourDetail) throws Exception {
        this.entityManager.merge(tourDetail);
    }

    @Override
    public void delete(Integer key) throws Exception {
        this.entityManager.remove(this.entityManager.find(TourDetail.class, key));
    }

    @Override
    public TourDetail get(Integer key) throws Exception {
        return this.entityManager.find(TourDetail.class, key);
    }

    @Override
    public List<TourDetail> getAll() throws Exception {
        Query allTourDetailsQuery = this.entityManager.createQuery("SELECT t FROM TourDetail t");
        return (List<TourDetail>) allTourDetailsQuery.getResultList();
    }

    @Override
    public List<TourDetail> getAllTourDetailsByCustomerID(int customerID) throws Exception {
        Query query = this.entityManager.createQuery("SELECT t FROM TourDetail t WHERE t.customer.id=?1");
        query.setParameter(Number.ONE, customerID);
        return (List<TourDetail>) query.getResultList();
    }

    @Override
    public TourDetail getTourDetailByCustomerIDAndTourDetailID(int customerID,
                                                               int tourDetailID) throws Exception {
        Query query = this.entityManager
                .createQuery("SELECT t FROM TourDetail t WHERE t.customer.id=?1 AND t.id=?2");
        query.setParameter(Number.ONE, customerID);
        query.setParameter(Number.TWO, tourDetailID);
        return (TourDetail) query.getSingleResult();
    }
}
