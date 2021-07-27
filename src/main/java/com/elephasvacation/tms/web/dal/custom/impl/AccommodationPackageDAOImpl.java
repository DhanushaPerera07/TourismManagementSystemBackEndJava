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
import com.elephasvacation.tms.web.dal.custom.AccommodationPackageDAO;
import com.elephasvacation.tms.web.entity.AccommodationPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccommodationPackageDAOImpl implements AccommodationPackageDAO {

    private static final String TABLE_NAME = "accommodation_package";
    private static String COLUMN_NAMES = "(year, season, valid_period, accommodation_id)";
    private Connection connection;

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public Integer save(AccommodationPackage accommodationPackage) throws Exception {
        return CrudUtil.executeAndReturnGeneratedKey(this.connection,
                "INSERT INTO " + TABLE_NAME + " " + COLUMN_NAMES + " VALUES (?,?,?,?)",
                accommodationPackage.getYear(),
                accommodationPackage.getSeason(),
                accommodationPackage.getValidPeriod(),
                accommodationPackage.getAccommodationId()
        );
    }

    @Override
    public boolean update(AccommodationPackage accommodationPackage) throws Exception {
        return CrudUtil.execute(this.connection,
                "UPDATE " + TABLE_NAME + " SET year=?, season=?, valid_period=?, accommodation_id=? WHERE id=?",
                accommodationPackage.getYear(),
                accommodationPackage.getSeason(),
                accommodationPackage.getValidPeriod(),
                accommodationPackage.getAccommodationId(),
                accommodationPackage.getId()
        );
    }

    @Override
    public boolean delete(Integer accommodationPackageID) throws Exception {
        return CrudUtil.execute(connection,
                "DELETE FROM " + TABLE_NAME + " WHERE id=?",
                accommodationPackageID);
    }

    @Override
    public AccommodationPackage get(Integer accommodationPackageID) throws Exception {
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM " + TABLE_NAME + " WHERE id=?",
                accommodationPackageID);

        if (resultSet.next()) {
            return getAccommodationPackageObject(resultSet);
        } else {
            return null;
        }
    }

    @Override
    public List<AccommodationPackage> getAll() throws Exception {
        List<AccommodationPackage> accommodationPackageList = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM " + TABLE_NAME);
        while (resultSet.next()) {
            accommodationPackageList.add(getAccommodationPackageObject(resultSet));
        }
        return accommodationPackageList;
    }

    private AccommodationPackage getAccommodationPackageObject(ResultSet resultSet)
            throws SQLException {
        return new AccommodationPackage(
                resultSet.getInt("id"),
                resultSet.getInt("year"),
                resultSet.getString("season"),
                resultSet.getString("valid_period"),
                resultSet.getInt("accommodation_id"),
                resultSet.getTimestamp("created"),
                resultSet.getTimestamp("last_updated")
        );
    }
}
