/*
@author : Dhanusha Perera
@date : 04/07/2021
*/
package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.util.HibernateUtil;

import javax.persistence.EntityManagerFactory;


public class CustomerDAOImplTest {

    CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    EntityManagerFactory entityManagerFactory = HibernateUtil.getEntityManagerFactory();;


//    @Test
//    public void getAll() throws Exception {
//        Customer customerObj = new Customer();
//        customerObj.setId(3);
//        customerObj.setName("tharanga");
//        customerObj.setNationality("Sri Lankan");
//
//        List<Customer> customersDB = this.customerDAO.getAll();
//        customersDB.forEach(System.out::println);
//        Assert.assertEquals(2, customersDB.size());
//    }

//    @Test
//    public void get() throws Exception {
//        Customer customerObj = new Customer();
//        customerObj.setId(3);
//        customerObj.setName("tharanga");
//        customerObj.setNationality("Sri Lankan");
//
//        Customer customerDB = this.customerDAO.get(3);
//        Assert.assertEquals(customerObj.getId(), customerDB.getId());
//        Assert.assertEquals(customerObj.getName(), customerDB.getName());
//    }


//    @Test(expected = NullPointerException.class)
//    public void getNegativeTest() throws Exception {
//        Customer customerObj = new Customer();
//        customerObj.setId(3);
//        customerObj.setName("tharanga");
//        customerObj.setNationality("Sri Lankan");
//
//        Customer customerDB = this.customerDAO.get(1);
//        Assert.assertEquals(customerObj.getId(), customerDB.getId());
//        Assert.assertEquals(customerObj.getName(), customerDB.getName());
//    }

//    @Test
//    public void save() {
//        Customer customer = new Customer();
//        customerDAO.save(customer);
//    }
}
