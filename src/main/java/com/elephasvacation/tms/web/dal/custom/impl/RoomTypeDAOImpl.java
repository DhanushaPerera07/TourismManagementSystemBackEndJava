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
 * @date : 13/07/2021
 */
package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.dal.CrudUtil;
import com.elephasvacation.tms.web.dal.custom.RoomTypeDAO;
import com.elephasvacation.tms.web.entity.RoomType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeDAOImpl implements RoomTypeDAO {

    private static final String COLUMN_NAMES = "(type)";
    private static final String TABLE_NAME = "room_type";
    private Connection connection;

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public Integer save(RoomType roomType) throws Exception {
        return CrudUtil.executeAndReturnGeneratedKey(connection,
                "INSERT INTO " + TABLE_NAME + " " + COLUMN_NAMES + " VALUES (?)",
                roomType.getType());
    }

    @Override
    public boolean update(RoomType roomType) throws Exception {
        return CrudUtil.execute(this.connection,
                "UPDATE " + TABLE_NAME + " SET type=? WHERE id=?",
                roomType.getType(),
                roomType.getId()
        );
    }

    @Override
    public boolean delete(Integer roomTypeID) throws Exception {
        return CrudUtil.execute(connection,
                "DELETE FROM " + TABLE_NAME + " WHERE id=?",
                roomTypeID);
    }

    @Override
    public RoomType get(Integer roomTypeID) throws Exception {
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM " + TABLE_NAME + " WHERE id=?",
                roomTypeID);

        if (resultSet.next()) {
            return getRoomTypeObject(resultSet);
        } else {
            return null;
        }
    }

    @Override
    public List<RoomType> getAll() throws Exception {
        List<RoomType> roomTypeList = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM " + TABLE_NAME);
        while (resultSet.next()) {
            roomTypeList.add(getRoomTypeObject(resultSet));
        }
        return roomTypeList;
    }

    private RoomType getRoomTypeObject(ResultSet resultSet) throws SQLException {
        return new RoomType(
                resultSet.getInt("id"),
                resultSet.getString("type"),
                resultSet.getTimestamp("created"),
                resultSet.getTimestamp("last_updated")
        );
    }

}
