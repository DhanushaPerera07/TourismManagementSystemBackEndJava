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

import com.elephasvacation.tms.web.dal.CrudUtil;
import com.elephasvacation.tms.web.dal.custom.CustomerDAO;
import com.elephasvacation.tms.web.entity.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    private static final String COLUMN_NAMES = "(name, nationality, passport_no,email, country_calling_code, contact_no, country, description, additional_notes)";
    private Connection connection;

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Integer save(Customer customer) throws Exception {
        return CrudUtil.executeAndReturnGeneratedKey(this.connection,
                "INSERT INTO customer " + COLUMN_NAMES + " VALUES (?,?,?,?,?,?,?,?,?)",
                customer.getName(),
                customer.getNationality(),
                customer.getPassportNo(),
                customer.getEmail(),
                customer.getCountryCallingCode(),
                customer.getContactNo(),
                customer.getCountry(),
                customer.getDescription(),
                customer.getAdditionalNotes()
        );
    }

    @Override
    public boolean update(Customer customer) throws Exception {
        return CrudUtil.execute(this.connection,
                "UPDATE customer SET name=?, nationality=?, passport_no=?, email=?, country_calling_code=?, contact_no=?, country=?, description=?, additional_notes=? WHERE id=?",
                customer.getName(),
                customer.getNationality(),
                customer.getPassportNo(),
                customer.getEmail(),
                customer.getCountryCallingCode(),
                customer.getContactNo(),
                customer.getCountry(),
                customer.getDescription(),
                customer.getAdditionalNotes(),
                customer.getId()
        );
    }

    @Override
    public boolean delete(Integer key) throws Exception {
        return CrudUtil.execute(connection,
                "DELETE FROM customer WHERE id=?",
                key);
    }

    @Override
    public Customer get(Integer key) throws Exception {
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM customer WHERE id=?",
                key);

        if (resultSet.next()) {
            return getCustomerObject(resultSet);
        } else {
            return null;
        }
    }

    @Override
    public List<Customer> getAll() throws Exception {
        List<Customer> customers = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM customer");
        while (resultSet.next()) {
            customers.add(getCustomerObject(resultSet));
        }
        return customers;
    }

    private Customer getCustomerObject(ResultSet resultSet) throws SQLException {
        return new Customer(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("nationality"),
                resultSet.getString("passport_no"),
                resultSet.getString("email"),
                resultSet.getString("country_calling_code"),
                resultSet.getString("contact_no"),
                resultSet.getString("country"),
                resultSet.getString("description"),
                resultSet.getString("additional_notes"),
                resultSet.getDate("added_date"),
                resultSet.getDate("last_updated")
        );
    }
}
