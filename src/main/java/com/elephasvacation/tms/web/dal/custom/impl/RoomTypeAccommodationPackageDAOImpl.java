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
import com.elephasvacation.tms.web.dal.custom.RoomTypeAccommodationPackageDAO;
import com.elephasvacation.tms.web.entity.RoomTypeAccommodationPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeAccommodationPackageDAOImpl implements RoomTypeAccommodationPackageDAO {

    private static final String TABLE_NAME = "room_type_accommodation_package";
    private static String COLUMN_NAMES = "(room_type_id, accommodation_package_id)";
    private static String ACCOMMODATION_PACKAGE_ID = "accommodation_package_id";
    private Connection connection;

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public Integer save(RoomTypeAccommodationPackage roomTypeAccommodationPackage) throws Exception {
        return CrudUtil.executeAndReturnGeneratedKey(this.connection,
                "INSERT INTO " + TABLE_NAME + " " + COLUMN_NAMES + " VALUES (?,?)",
                roomTypeAccommodationPackage.getRoomTypeAccommodationPackagePK().getRoomTypeID(),
                roomTypeAccommodationPackage.getRoomTypeAccommodationPackagePK().getAccommodationPackageID()
        );
    }

    @Override
    public boolean update(RoomTypeAccommodationPackage roomTypeAccommodationPackage) throws Exception {
        return CrudUtil.execute(this.connection,
                "UPDATE " + TABLE_NAME + " SET room_category_id=?, accommodation_package_id=?  WHERE id=?",
                roomTypeAccommodationPackage.getRoomTypeAccommodationPackagePK().getRoomTypeID(),
                roomTypeAccommodationPackage.getRoomTypeAccommodationPackagePK().getAccommodationPackageID(),
                roomTypeAccommodationPackage.getRoomTypeAccommodationPackagePK().getId()
        );
    }

    @Override
    public boolean delete(Integer roomTypeAccommodationPackageID) throws Exception {
        return CrudUtil.execute(connection,
                "DELETE FROM " + TABLE_NAME + " WHERE id=?",
                roomTypeAccommodationPackageID);
    }

    @Override
    public RoomTypeAccommodationPackage get(Integer roomTypeAccommodationPackageID) throws Exception {
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM " + TABLE_NAME + " WHERE id=?",
                roomTypeAccommodationPackageID);

        if (resultSet.next()) {
            return getRoomTypeAccommodationPackageObject(resultSet);
        } else {
            return null;
        }
    }

    @Override
    public List<RoomTypeAccommodationPackage> getAllRoomTypesByAccommodationPackageID(Integer accommodationPackageID)
            throws SQLException {
        List<RoomTypeAccommodationPackage> roomTypeAccommodationPackageList = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM " + TABLE_NAME + " WHERE " + ACCOMMODATION_PACKAGE_ID + "=?",
                accommodationPackageID);
        while (resultSet.next()) {
            roomTypeAccommodationPackageList.add(getRoomTypeAccommodationPackageObject(resultSet));
        }
        return roomTypeAccommodationPackageList;
    }

    @Override
    public List<RoomTypeAccommodationPackage> getAll() throws Exception {
        List<RoomTypeAccommodationPackage> roomTypeAccommodationPackageList = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM " + TABLE_NAME);
        while (resultSet.next()) {
            roomTypeAccommodationPackageList.add(getRoomTypeAccommodationPackageObject(resultSet));
        }
        return roomTypeAccommodationPackageList;
    }

    private RoomTypeAccommodationPackage getRoomTypeAccommodationPackageObject(ResultSet resultSet)
            throws SQLException {
        return new RoomTypeAccommodationPackage(
                resultSet.getInt("id"),
                resultSet.getInt("room_type_id"),
                resultSet.getInt("accommodation_package_id"),
                resultSet.getTimestamp("created"),
                resultSet.getTimestamp("last_updated")
        );
    }
}
