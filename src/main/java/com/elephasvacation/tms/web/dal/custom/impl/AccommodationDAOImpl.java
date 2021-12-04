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

import com.elephasvacation.tms.web.dal.custom.AccommodationDAO;
import com.elephasvacation.tms.web.entity.Accommodation;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class AccommodationDAOImpl implements AccommodationDAO {

    private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Integer save(Accommodation accommodation) throws Exception {
        this.entityManager.persist(accommodation);
        //  call the flush method on EntityManager manually, because we need to get the Generated ID
        this.entityManager.flush();
        return accommodation.getId();
    }

    @Override
    public void update(Accommodation accommodation) throws Exception {
        this.entityManager.merge(accommodation);
    }

    @Override
    public void delete(Integer key) throws Exception {
        this.entityManager.remove(this.entityManager.find(Accommodation.class, key));
    }

    @Override
    public Accommodation get(Integer key) throws Exception {
        return this.entityManager.find(Accommodation.class, key);
    }

    @Override
    public List<Accommodation> getAll() throws Exception {
        Query allAccommodationQuery = this.entityManager.createQuery("SELECT acc FROM Accommodation acc");
        return (List<Accommodation>) allAccommodationQuery.getResultList();
    }
}
