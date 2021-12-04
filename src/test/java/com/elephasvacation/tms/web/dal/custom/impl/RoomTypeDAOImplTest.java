package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.dal.custom.RoomTypeDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
@author : Dhanusha Perera
@date : 13/07/2021
*/public class RoomTypeDAOImplTest {

//    RoomTypeDAO roomTypeDAO = new RoomTypeDAOImpl();
//    Connection connection;
//
//    @Before
//    public void setConnection() throws Exception {
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms",
//                "root", "root");
//        this.connection.setAutoCommit(false);
//        roomTypeDAO.setConnection(this.connection);
//    }
//
//    @After
//    public void closeConnection() throws SQLException {
//        this.connection.rollback();
//        this.connection.close();
//    }
//
//    @Test
//    public void get() throws Exception {
//        RoomType roomTypeDB = this.roomTypeDAO.get(4);
//        Assert.assertTrue("Luxury".equals(roomTypeDB.getType()));
//    }
//
//    @Test
//    public void getAll() throws Exception {
//        this.roomTypeDAO.getAll().forEach(System.out::println);
//        Assert.assertEquals(4, this.roomTypeDAO.getAll().size());
//    }
}
