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

import com.elephasvacation.tms.web.dal.custom.EmployeeCredentialDAO;
import com.elephasvacation.tms.web.dal.custom.EmployeeDAO;
import com.elephasvacation.tms.web.entity.Employee;
import com.elephasvacation.tms.web.entity.EmployeeCredential;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.Assert.assertNotNull;

public class EmployeeCredentialDAOImplTest {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private EmployeeCredentialDAO employeeCredentialDAO;

    private EntityManagerFactory emf;

    private EntityManager em;

//    @Before
//    public void setUp() {
//        try {
//            /* get EntityManagerFactory. */
//            this.emf = HibernateUtil.getEntityManagerFactory();
//            /* creates EntityManager. */
//            this.em = emf.createEntityManager();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @After
//    public void tearDown() {
//        /* close the EntityManagerFactory and EntityManager. */
//        if (em != null) {
//            em.close();
//            emf.close();
//        }
//    }

    @Test
    public void save() {
        try {
            /* begins the transaction. */
            this.em.getTransaction().begin();

            /* set EntityManager. */
            this.employeeDAO.setEntityManager(this.em);
            this.employeeCredentialDAO.setEntityManager(this.em);

            Employee john = this.employeeDAO.get(new Integer("1"));

            assertNotNull(john);

            /* creates a new Employee Credential object. */
            EmployeeCredential johnsCredential = new EmployeeCredential(john.getId(), "Test@123");

            /* saving the Employee. */
            Integer employeeCredentialId = this.employeeCredentialDAO.save(johnsCredential).getId();

            /* assert */
            assertNotNull(employeeCredentialId);

            /* print the generated ID on the terminal. */
            System.out.println("Generated Employee Credential ID: " + employeeCredentialId);

            /* committing the transaction. */
            this.em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}