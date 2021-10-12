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
 * @date : 26/07/2021
 */
package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.dal.CrudUtil;
import com.elephasvacation.tms.web.dal.custom.MealPlanAccommodationPackageDAO;
import com.elephasvacation.tms.web.entity.MealPlanAccommodationPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealPlanAccommodationPackageDAOImpl implements MealPlanAccommodationPackageDAO {

    private static final String TABLE_NAME = "meal_plan_accommodation_package";
    private static String COLUMN_NAMES = "(meal_plan_id, accommodation_package_id)";
    private static String ACCOMMODATION_PACKAGE_ID = "accommodation_package_id";
    private Connection connection;

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public Integer save(MealPlanAccommodationPackage mealPlanAccommodationPackage) throws Exception {
        return CrudUtil.executeAndReturnGeneratedKey(this.connection,
                "INSERT INTO " + TABLE_NAME + " " + COLUMN_NAMES + " VALUES (?,?)",
                mealPlanAccommodationPackage.getMealPlanAccommodationPackagePK().getMealPlanID(),
                mealPlanAccommodationPackage.getMealPlanAccommodationPackagePK().getAccommodationPackageID()
        );
    }

    @Override
    public boolean update(MealPlanAccommodationPackage mealPlanAccommodationPackage) throws Exception {
        return CrudUtil.execute(this.connection,
                "UPDATE " + TABLE_NAME + " SET meal_plan_id=?, accommodation_package_id=?  WHERE id=?",
                mealPlanAccommodationPackage.getMealPlanAccommodationPackagePK().getMealPlanID(),
                mealPlanAccommodationPackage.getMealPlanAccommodationPackagePK().getAccommodationPackageID(),
                mealPlanAccommodationPackage.getMealPlanAccommodationPackagePK().getId()
        );
    }

    @Override
    public boolean delete(Integer mealPlanAccommodationPackageID) throws Exception {
        return CrudUtil.execute(connection,
                "DELETE FROM " + TABLE_NAME + " WHERE id=?",
                mealPlanAccommodationPackageID);
    }

    @Override
    public MealPlanAccommodationPackage get(Integer mealPlanAccommodationPackageID) throws Exception {
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM " + TABLE_NAME + " WHERE id=?",
                mealPlanAccommodationPackageID);

        if (resultSet.next()) {
            return getMealPlanAccommodationPackageObject(resultSet);
        } else {
            return null;
        }
    }

    @Override
    public List<MealPlanAccommodationPackage> getAllMealPlansByAccommodationPackageID(Integer accommodationPackageID)
            throws SQLException {
        List<MealPlanAccommodationPackage> mealPlanAccommodationPackageList = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM " + TABLE_NAME + " WHERE " + ACCOMMODATION_PACKAGE_ID + "=?",
                accommodationPackageID);
        while (resultSet.next()) {
            mealPlanAccommodationPackageList.add(getMealPlanAccommodationPackageObject(resultSet));
        }
        return mealPlanAccommodationPackageList;
    }

    @Override
    public List<MealPlanAccommodationPackage> getAll() throws Exception {
        List<MealPlanAccommodationPackage> mealPlanAccommodationPackageList = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM " + TABLE_NAME);
        while (resultSet.next()) {
            mealPlanAccommodationPackageList.add(getMealPlanAccommodationPackageObject(resultSet));
        }
        return mealPlanAccommodationPackageList;
    }

    private MealPlanAccommodationPackage getMealPlanAccommodationPackageObject(ResultSet resultSet) throws SQLException {
        return new MealPlanAccommodationPackage(
                resultSet.getInt("id"),
                resultSet.getInt("meal_plan_id"),
                resultSet.getInt("accommodation_package_id"),
                resultSet.getTimestamp("created"),
                resultSet.getTimestamp("last_updated")
        );
    }
}
