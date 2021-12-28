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
@author : Dhanusha Perera
@date : 04/07/2021
*/
package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.CustomerDAO;
import com.elephasvacation.tms.web.entity.Customer;
import com.elephasvacation.tms.web.entity.enumeration.GenderTypes;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.Assert.assertNotNull;

public class CustomerDAOImplTest {

    private final CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);
    EntityManagerFactory emf = null;
    EntityManager em = null;

//    @Before
//    public void setEntityManager() {
//        try {
//            this.emf = HibernateUtil.getEntityManagerFactory();
//            this.em = emf.createEntityManager();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @After
//    public void closeEntityManager() {
//        if (em != null) {
//            em.close();
//            emf.close();
//        }
//    }

    @Test
    public void save() {
        assertNotNull(this.customerDAO);
        this.em.getTransaction().begin();
        this.customerDAO.setEntityManager(this.em);

        Customer customer = new Customer(
                "John Doe",
                "UK",
                "112233445566",
                "john.doe@gmail.com",
                "+441632960417",
                "UK",
                "None",
                "None",
                GenderTypes.MALE
        );
        try {
            this.customerDAO.save(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.em.getTransaction().commit();
    }
}
