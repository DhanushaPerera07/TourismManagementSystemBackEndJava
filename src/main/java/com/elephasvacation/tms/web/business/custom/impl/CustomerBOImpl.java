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
import com.elephasvacation.tms.web.business.custom.util.transaction.TMSTransaction;
import com.elephasvacation.tms.web.dal.custom.CustomerDAO;
import com.elephasvacation.tms.web.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class CustomerBOImpl implements CustomerBO {

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private CustomerDTOMapper mapper;

    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;

        /* Set Entity Manager to the DAL. */
        this.customerDAO.setEntityManager(this.entityManager);
    }

    @TMSTransaction
    @Override
    public Integer createCustomer(CustomerDTO customerDTO) throws Exception {
        /* save. */
        Integer generatedCustomerId = this.customerDAO.save(mapper.getCustomer(customerDTO)).getId();
        return generatedCustomerId;
    }

    @TMSTransaction
    @Override
    public void updateCustomer(CustomerDTO customerDTO) throws Exception {
        /* update. */
        this.customerDAO.update(mapper.getCustomer(customerDTO));
    }

    @TMSTransaction
    @Override
    public void deleteCustomer(int customerID) throws Exception {
        /* delete. */
        this.customerDAO.delete(customerID);
    }

    @TMSTransaction
    @Override
    public CustomerDTO getCustomerByID(int customerID) throws Exception {
        /* get customer by customer ID. */
        CustomerDTO customerDTO = this.mapper.getCustomerDTO(this.customerDAO.get(customerID));
        return customerDTO;
    }

    @TMSTransaction
    @Override
    public List<CustomerDTO> getAllCustomers() throws Exception {
        /* get all customers. */
        List<CustomerDTO> customerDTOList = this.mapper.getCustomerDTOs(this.customerDAO.getAll());
        return customerDTOList;
    }
}
