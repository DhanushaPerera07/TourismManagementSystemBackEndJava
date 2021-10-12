package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.entity.Customer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/*
@author : Dhanusha Perera
@date : 04/07/2021
*/public class CustomerDAOImplTest {

    CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    Connection connection;

    @Before
    public void setConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms",
                "root", "root");
        this.connection.setAutoCommit(false);
        customerDAO.setConnection(this.connection);
    }

    @After
    public void closeConnection() throws SQLException {
        this.connection.rollback();
        this.connection.close();
    }

    @Test
    public void getAll() throws Exception {
        Customer customerObj = new Customer();
        customerObj.setId(3);
        customerObj.setName("tharanga");
        customerObj.setNationality("Sri Lankan");

        List<Customer> customersDB = this.customerDAO.getAll();
        customersDB.forEach(System.out::println);
        Assert.assertEquals(2, customersDB.size());
    }

    @Test
    public void get() throws Exception {
        Customer customerObj = new Customer();
        customerObj.setId(3);
        customerObj.setName("tharanga");
        customerObj.setNationality("Sri Lankan");

        Customer customerDB = this.customerDAO.get(3);
        Assert.assertEquals(customerObj.getId(), customerDB.getId());
        Assert.assertEquals(customerObj.getName(), customerDB.getName());
    }


    @Test(expected = NullPointerException.class)
    public void getNegativeTest() throws Exception {
        Customer customerObj = new Customer();
        customerObj.setId(3);
        customerObj.setName("tharanga");
        customerObj.setNationality("Sri Lankan");

        Customer customerDB = this.customerDAO.get(1);
        Assert.assertEquals(customerObj.getId(), customerDB.getId());
        Assert.assertEquals(customerObj.getName(), customerDB.getName());
    }

}
