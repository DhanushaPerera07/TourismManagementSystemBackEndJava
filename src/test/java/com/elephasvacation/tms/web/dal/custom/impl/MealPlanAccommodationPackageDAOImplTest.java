package com.elephasvacation.tms.web.dal.custom.impl;

/*
@author : Dhanusha Perera
@date : 26/07/2021
*/

import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.MealPlanAccommodationPackageDAO;
import com.elephasvacation.tms.web.entity.MealPlanAccommodationPackage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MealPlanAccommodationPackageDAOImplTest {

    MealPlanAccommodationPackageDAO mealPlanAccommodationPackageDAO =
            DAOFactory.getInstance().getDAO(DAOTypes.MEAL_PLAN_FOR_ACCOMMODATION_PACKAGE);
    Connection connection;


    @Before
    public void setConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms",
                "root", "root");
        this.connection.setAutoCommit(false);
        mealPlanAccommodationPackageDAO.setConnection(this.connection);
    }

    @After
    public void closeConnection() throws SQLException {
        this.connection.rollback();
        this.connection.close();
    }


//    @Test
//    public void save() {
//
//    }
//
//    @Test()
//    public void update() {
//    }
//
//    @Test
//    public void delete() {
//    }
//
//    @Test
//    public void get() {
//    }
//
//    @Test
//    public void getAllMealPlansByAccommodationPackageID() {
//    }

    @Test
    public void getAll() throws Exception {
        List<MealPlanAccommodationPackage> mealPlanAccommodationPackageDAOList =
                this.mealPlanAccommodationPackageDAO.getAll();

        Assert.assertEquals(0 ,mealPlanAccommodationPackageDAOList.size());
    }
}
