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
 * @date : 18/07/2021
 */
package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.dal.CrudUtil;
import com.elephasvacation.tms.web.dal.custom.MealPlanDAO;
import com.elephasvacation.tms.web.entity.MealPlan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealPlanDAOImpl implements MealPlanDAO {

    private static final String TABLE_NAME = "meal_plan";
    private static String COLUMN_NAMES = "(meal_plan)";
    private Connection connection;

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public Integer save(MealPlan mealPlan) throws Exception {
        return CrudUtil.executeAndReturnGeneratedKey(this.connection,
                "INSERT INTO " + TABLE_NAME + " " + COLUMN_NAMES + " VALUES (?)",
                mealPlan.getMealPlan());
    }

    @Override
    public boolean update(MealPlan mealPlan) throws Exception {
        return CrudUtil.execute(this.connection,
                "UPDATE " + TABLE_NAME + " SET meal_plan=? WHERE id=?",
                mealPlan.getMealPlan(),
                mealPlan.getId()
        );
    }

    @Override
    public boolean delete(Integer mealPlanID) throws Exception {
        return CrudUtil.execute(connection,
                "DELETE FROM " + TABLE_NAME + " WHERE id=?",
                mealPlanID);
    }

    @Override
    public MealPlan get(Integer mealPlanID) throws Exception {
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM " + TABLE_NAME + " WHERE id=?",
                mealPlanID);

        if (resultSet.next()) {
            return getMealPlanObject(resultSet);
        } else {
            return null;
        }
    }

    @Override
    public List<MealPlan> getAll() throws Exception {
        List<MealPlan> mealPlanList = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM " + TABLE_NAME);
        while (resultSet.next()) {
            mealPlanList.add(getMealPlanObject(resultSet));
        }
        return mealPlanList;
    }

    private MealPlan getMealPlanObject(ResultSet resultSet) throws SQLException {
        return new MealPlan(
                resultSet.getInt("id"),
                resultSet.getString("meal_plan"),
                resultSet.getTimestamp("created"),
                resultSet.getTimestamp("last_updated")
        );
    }
}
