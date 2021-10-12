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
 * @date : 05/07/2021
 */
package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.CustomerBO;
import com.elephasvacation.tms.web.business.custom.util.EntityDTOMapper;
import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.CustomerDAO;
import com.elephasvacation.tms.web.dto.CustomerDTO;

import java.sql.Connection;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    private final CustomerDAO customerDAO;
    private final EntityDTOMapper mapper = EntityDTOMapper.instance;
    private Connection connection;

    public CustomerBOImpl() {
        this.customerDAO = DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);
    }

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.connection = connection;
        this.customerDAO.setConnection(connection);
    }

    @Override
    public Integer createCustomer(CustomerDTO customerDTO) throws Exception {
        return this.customerDAO.save(mapper.getCustomer(customerDTO));
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws Exception {
        return this.customerDAO.update(mapper.getCustomer(customerDTO));
    }

    @Override
    public boolean deleteCustomer(int customerID) throws Exception {
        return this.customerDAO.delete(customerID);
    }

    @Override
    public CustomerDTO getCustomerByID(int customerID) throws Exception {
        return mapper.getCustomerDTO(this.customerDAO.get(customerID));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() throws Exception {
        return mapper.getCustomerDTOs(this.customerDAO.getAll());
    }
}
