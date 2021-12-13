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

import com.elephasvacation.tms.web.dal.CrudDAOImpl;
import com.elephasvacation.tms.web.dal.custom.EmployeeCredentialDAO;
import com.elephasvacation.tms.web.entity.EmployeeCredential;

public class EmployeeCredentialDAOImpl
        extends CrudDAOImpl<EmployeeCredential, Integer>
        implements EmployeeCredentialDAO {

//    private EntityManager entityManager;
//
//    @Override
//    public void setEntityManager(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
//
//    @Override
//    public Integer save(EmployeeCredential credential) throws Exception {
//        this.entityManager.persist(credential);
//        //  call the flush method on EntityManager manually, because we need to get the Generated ID
//        this.entityManager.flush();
//        return credential.getId();
//    }
//
//    @Override
//    public void update(EmployeeCredential credential) throws Exception {
//        this.entityManager.merge(credential);
//    }
//
//    @Override
//    public void delete(Integer key) throws Exception {
//        this.entityManager.remove(this.entityManager.find(EmployeeCredential.class, key));
//    }
//
//    @Override
//    public EmployeeCredential get(Integer key) throws Exception {
//        return this.entityManager.find(EmployeeCredential.class, key);
//    }
//
//    @Override
//    public List<EmployeeCredential> getAll() throws Exception {
////        Query allEmployeesQuery = this.entityManager.createQuery("SELECT ec FROM EmployeeCredential ec");
////        return (List<EmployeeCredential>) allEmployeesQuery.getResultList();
//
//        TypedQuery<EmployeeCredential> selectEmployeeCredentialTypedQuery =
//                this.entityManager.createQuery("SELECT ec FROM EmployeeCredential ec", EmployeeCredential.class);
//
//        return selectEmployeeCredentialTypedQuery.getResultList();
//    }
}
