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

import com.elephasvacation.tms.web.WebAppConfig;
import com.elephasvacation.tms.web.WebRootConfig;
import com.elephasvacation.tms.web.dal.EmployeeDAO;
import com.elephasvacation.tms.web.entity.Employee;
import com.elephasvacation.tms.web.entity.enumeration.GenderTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebRootConfig.class, WebAppConfig.class})
public class EmployeeDAOImplTest {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Test
    public void save() {
        try {
            /* creates a new Employee object. */
            Employee employee = new Employee("John Doe",
                    "New York",
                    ZonedDateTime.of(LocalDateTime.of(1991, 1, 1, 0, 0), ZoneId.of("Asia/Colombo")),
                    "112233445566",
                    "03321234567",
                    "john.test@gmail.com",
                    GenderTypes.MALE,
                    "Trainee",
                    "Active");

            /* saving the Employee. */
            Integer generatedEmployeeId = this.employeeDAO.save(employee).getId();

            /* assert */
            assertNotNull(generatedEmployeeId);

            /* print the generated ID on the terminal. */
            System.out.println("Generated Employee ID: " + generatedEmployeeId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}