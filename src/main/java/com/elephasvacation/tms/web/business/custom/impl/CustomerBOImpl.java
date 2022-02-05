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
 * @date : 05/07/2021
 */
package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.CustomerBO;
import com.elephasvacation.tms.web.business.custom.util.mapper.CustomerDTOMapper;
import com.elephasvacation.tms.web.dal.CustomerDAO;
import com.elephasvacation.tms.web.dto.CustomerDTO;
import com.elephasvacation.tms.web.entity.Customer;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NoArgsConstructor
@Transactional
@Service
public class CustomerBOImpl implements CustomerBO {

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private CustomerDTOMapper mapper;


    @Override
    public Integer createCustomer(CustomerDTO customerDTO) {
        /* save. */
        Customer customer = this.mapper.getCustomer(customerDTO);

        /* Set customer id to null, since CustomerID should be null in the DAO layer.
         * If customerID is set to an Integer, then detached error will be prompted */
        if (customer != null && customer.getId() != null) {
            customer.setId(null);
        }

        return this.customerDAO.save(customer).getId();
    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO) {
        /* update. */
        Customer customer = this.mapper.getCustomer(customerDTO);
        System.out.println("CustomerDTO ---> Customer: " + customer);
        this.customerDAO.save(customer);
    }

    @Override
    public void deleteCustomer(Integer customerID) {
        /* delete. */
        this.customerDAO.deleteById(customerID);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerDTO getCustomerByID(Integer customerID) {
        /* get customer by customer ID. */
        return this.mapper.getCustomerDTO(this.customerDAO.getById(customerID));
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerDTO> getAllCustomers() {
        /* get all customers. */
        return this.mapper.getCustomerDTOs(this.customerDAO.findAll());
    }
}
