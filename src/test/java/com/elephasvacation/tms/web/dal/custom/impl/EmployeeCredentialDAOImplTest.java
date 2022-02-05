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

import com.elephasvacation.tms.web.dal.EmployeeCredentialDAO;
import com.elephasvacation.tms.web.dal.EmployeeDAO;
import com.elephasvacation.tms.web.entity.Employee;
import com.elephasvacation.tms.web.entity.EmployeeCredential;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

public class EmployeeCredentialDAOImplTest {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private EmployeeCredentialDAO employeeCredentialDAO;

    @Test
    public void save() {
        try {
            Employee john = this.employeeDAO.getById(1);

            assertNotNull(john);

            /* creates a new Employee Credential object. */
            EmployeeCredential johnsCredential = new EmployeeCredential(john.getId(), "Test@123");

            /* saving the Employee. */
            Integer employeeCredentialId = this.employeeCredentialDAO.save(johnsCredential).getId();

            /* assert */
            assertNotNull(employeeCredentialId);

            /* print the generated ID on the terminal. */
            System.out.println("Generated Employee Credential ID: " + employeeCredentialId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}