package com.elephasvacation.tms.web.dal.custom.impl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
@author : Dhanusha Perera
@date : 04/07/2021
*/public class TourDetailDAOImplTest {

//    TourDetailDAOImpl tourDetailDAO = new TourDetailDAOImpl();
//    Connection connection;
//
//    @Before
//    public void setConnection() throws Exception {
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms",
//                "root", "root");
//        this.connection.setAutoCommit(false);
//        tourDetailDAO.setConnection(this.connection);
//    }
//
//    @After
//    public void closeConnection() throws SQLException {
//        this.connection.rollback();
//        this.connection.close();
//    }
//
////    @Test
////    public void save() {
////
////    }
//
//    @Test
//    public void get() throws Exception {
//        TourDetail tourDetail = new TourDetail();
//        tourDetail.setId(3);
//        tourDetail.setArrivalDate(Date.valueOf("2021-05-20"));
//
//        TourDetail tourDetailDB = this.tourDetailDAO.get(3);
//        Assert.assertEquals(tourDetail.getId(), tourDetailDB.getId());
//        Assert.assertEquals(tourDetail.getArrivalDate(), tourDetailDB.getArrivalDate());
//
//    }
}
