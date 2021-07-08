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
 * @date : 04/07/2021
 */
package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.dal.CrudUtil;
import com.elephasvacation.tms.web.dal.custom.AccommodationDAO;
import com.elephasvacation.tms.web.entity.Accommodation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccommodationDAOImpl implements AccommodationDAO {

    private Connection connection;

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Integer save(Accommodation accommodation) throws Exception {
        return CrudUtil.executeAndReturnGeneratedKey(this.connection,
                "INSERT INTO accommodation VALUES (?,?,?,?,?,?,?,?,?,?)",
                accommodation.getName(),
                accommodation.getSituatedIn(),
                accommodation.getStarRating(),
                accommodation.getType(),
                accommodation.getContact(),
                accommodation.getEmail(),
                accommodation.getAddress(),
                accommodation.getWebsite(),
                accommodation.getSpecialDetails(),
                accommodation.getRemark()
        );
    }

    @Override
    public boolean update(Accommodation accommodation) throws Exception {
        return CrudUtil.execute(this.connection,
                "UPDATE accommodation SET name=?, situated_in=?, star_rating=?, type=?, contact=?, email=?, address=?, website=?, special_details=?, remark=? WHERE id=?",
                accommodation.getName(),
                accommodation.getSituatedIn(),
                accommodation.getStarRating(),
                accommodation.getType(),
                accommodation.getContact(),
                accommodation.getEmail(),
                accommodation.getAddress(),
                accommodation.getWebsite(),
                accommodation.getSpecialDetails(),
                accommodation.getRemark(),
                accommodation.getId()
        );
    }

    @Override
    public boolean delete(Integer key) throws Exception {
        return CrudUtil.execute(connection,
                "DELETE FROM accommodation WHERE id=?",
                key);
    }

    @Override
    public Accommodation get(Integer key) throws Exception {
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM accommodation WHERE id=?",
                key);

        if (resultSet.next()) {
            return getAccommodationObject(resultSet);
        } else {
            return null;
        }
    }

    @Override
    public List<Accommodation> getAll() throws Exception {
        List<Accommodation> accommodations = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM accommodation");
        while (resultSet.next()) {
            accommodations.add(getAccommodationObject(resultSet));
        }
        return accommodations;
    }

    private Accommodation getAccommodationObject(ResultSet resultSet) throws SQLException {
        return new Accommodation(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("situated_in"),
                resultSet.getInt("star_rating"),
                resultSet.getString("type"),
                resultSet.getString("contact"),
                resultSet.getString("email"),
                resultSet.getString("address"),
                resultSet.getString("website"),
                resultSet.getString("special_details"),
                resultSet.getString("remark"),
                resultSet.getDate("created"),
                resultSet.getDate("last_updated")
        );
    }
}
