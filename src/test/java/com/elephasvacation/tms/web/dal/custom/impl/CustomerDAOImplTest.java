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

import com.elephasvacation.tms.web.WebAppConfig;
import com.elephasvacation.tms.web.WebRootConfig;
import com.elephasvacation.tms.web.dal.CustomerDAO;
import com.elephasvacation.tms.web.entity.Customer;
import com.elephasvacation.tms.web.entity.enumeration.GenderTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebRootConfig.class, WebAppConfig.class})
public class CustomerDAOImplTest {

    @Autowired
    private CustomerDAO customerDAO;

    @Test
    public void checkCustomerDAO() {
        assertNotNull(customerDAO);
    }

    @Transactional
    @Test
    public void getCustomerByID() throws Exception {
        assertNotNull(customerDAO);

        Customer customer = this.customerDAO.getById(10);
        System.out.println("GET Customer Entity: " + customer);
        assertNotNull(customer);
    }

    @Test
    public void getAllCustomers() throws Exception {
        List<Customer> customersList = this.customerDAO.findAll();
        assertEquals(3, customersList.size());
    }

    @Transactional
    @Test
    public void save() {
        assertNotNull(this.customerDAO);

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

        System.out.println("Customer Entity: " + customer);

        try {
            Customer savedCustomer = this.customerDAO.save(customer);
            assertNotNull(savedCustomer);
            System.out.println("SavedCustomer: " + savedCustomer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Test
    public void deleteCustomer() throws Exception {
        assertNotNull(this.customerDAO);

        /* delete */
        this.customerDAO.deleteById(3);

        Customer customerFromDB = this.customerDAO.getById(10);
        assertNull(customerFromDB);
    }
}
