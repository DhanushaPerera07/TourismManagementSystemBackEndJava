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
 * @date : 27/07/2021
 */
package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.dal.CrudUtil;
import com.elephasvacation.tms.web.dal.custom.AccommodationRateDAO;
import com.elephasvacation.tms.web.entity.AccommodationRate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccommodationRateDAOImpl implements AccommodationRateDAO {

    private static final String TABLE_NAME = "accommodation_rate";
    private static String COLUMN_NAMES = "(accommodation_package_id, room_type_accommodation_package_id, room_category_accommodation_package_id, meal_plan_accommodation_package_id)";
    private static String ACCOMMODATION_PACKAGE_ID = "accommodation_package_id";
    private Connection connection;


    @Override
    public void setConnection(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public Integer save(AccommodationRate accommodationRate) throws Exception {
        return CrudUtil.executeAndReturnGeneratedKey(this.connection,
                "INSERT INTO " + TABLE_NAME + " " + COLUMN_NAMES + " VALUES (?,?,?,?)",
                accommodationRate.getAccommodationRatePK().getAccommodationPackageID(),
                accommodationRate.getAccommodationRatePK().getRoomTypeAccommodationPackageID(),
                accommodationRate.getAccommodationRatePK().getRoomCategoryAccommodationPackageID(),
                accommodationRate.getAccommodationRatePK().getMealPlanAccommodationPackageID()
        );
    }

    @Override
    public boolean update(AccommodationRate accommodationRate) throws Exception {
        return CrudUtil.execute(this.connection,
                "UPDATE " + TABLE_NAME + " SET accommodation_package_id=?, room_type_accommodation_package_id=?, room_category_accommodation_package_id=?, meal_plan_accommodation_package_id=?  WHERE id=?",
                accommodationRate.getAccommodationRatePK().getAccommodationPackageID(),
                accommodationRate.getAccommodationRatePK().getRoomTypeAccommodationPackageID(),
                accommodationRate.getAccommodationRatePK().getRoomCategoryAccommodationPackageID(),
                accommodationRate.getAccommodationRatePK().getMealPlanAccommodationPackageID(),
                accommodationRate.getAccommodationRatePK().getId()
        );
    }

    @Override
    public boolean delete(Integer accommodationRateID) throws Exception {
        return CrudUtil.execute(connection,
                "DELETE FROM " + TABLE_NAME + " WHERE id=?",
                accommodationRateID);
    }

    @Override
    public AccommodationRate get(Integer accommodationRateID) throws Exception {
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM " + TABLE_NAME + " WHERE id=?",
                accommodationRateID);

        return (resultSet.next()) ? getAccommodationRateObject(resultSet) : null;
    }

    @Override
    public List<AccommodationRate> getAll() throws Exception {
        List<AccommodationRate> accommodationRateList = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM " + TABLE_NAME);
        while (resultSet.next()) {
            accommodationRateList.add(getAccommodationRateObject(resultSet));
        }
        return accommodationRateList;
    }

    @Override
    public List<AccommodationRate> getAllAccommodationRatesByAccommodationPackageID(Integer accommodationPackageID) throws SQLException {
        List<AccommodationRate> accommodationRateList = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM " + TABLE_NAME + " WHERE " + ACCOMMODATION_PACKAGE_ID + "=?",
                accommodationPackageID);
        while (resultSet.next()) {
            accommodationRateList.add(getAccommodationRateObject(resultSet));
        }
        return accommodationRateList;
    }

    private AccommodationRate getAccommodationRateObject(ResultSet resultSet) throws SQLException {
        return new AccommodationRate(
                resultSet.getInt("id"),
                resultSet.getInt("accommodation_package_id"),
                resultSet.getInt("room_type_accommodation_package_id"),
                resultSet.getInt("room_category_accommodation_package_id"),
                resultSet.getInt("meal_plan_accommodation_package_id"),
                resultSet.getTimestamp("created"),
                resultSet.getTimestamp("last_updated")
        );
    }
}
