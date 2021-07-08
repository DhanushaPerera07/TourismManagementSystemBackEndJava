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
import com.elephasvacation.tms.web.dal.custom.EmployeeDAO;
import com.elephasvacation.tms.web.entity.Employee;
import com.elephasvacation.tms.web.entity.enumeration.GenderTypes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    private static final String COLUMN_NAMES = "(name, address, dob, nic, contact, email, gender, position, status, password)";
    private Connection connection;

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public Integer save(Employee employee) throws Exception {
        return CrudUtil.executeAndReturnGeneratedKey(this.connection,
                "INSERT INTO employee " + COLUMN_NAMES + " VALUES (?,?,?,?,?,?,?,?,?,?)",
                employee.getName(),
                employee.getAddress(),
                employee.getDateOfBirth(),
                employee.getNic(),
                employee.getContact(),
                employee.getEmail(),
                employee.getGender(),
                employee.getPosition(),
                employee.getStatus(),
                employee.getPassword()
        );
    }

    @Override
    public boolean update(Employee employee) throws Exception {
        return CrudUtil.execute(this.connection,
                "UPDATE employee SET name=?, address=?, dob=?, nic=?, contact=?, email=?, gender=?, position=?, status=?, password=? WHERE id=?",
                employee.getName(),
                employee.getAddress(),
                employee.getDateOfBirth(),
                employee.getNic(),
                employee.getContact(),
                employee.getEmail(),
                employee.getGender(),
                employee.getPosition(),
                employee.getStatus(),
                employee.getPassword(),
                employee.getId()
        );
    }

    @Override
    public boolean delete(Integer key) throws Exception {
        return CrudUtil.execute(connection,
                "DELETE FROM employee WHERE id=?",
                key);
    }

    @Override
    public Employee get(Integer key) throws Exception {
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM employee WHERE id=?",
                key);

        if (resultSet.next()) {
            return getEmployeeObject(resultSet);
        } else {
            return null;
        }
    }


    @Override
    public List<Employee> getAll() throws Exception {
        List<Employee> employees = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM employee");
        while (resultSet.next()) {
            employees.add(getEmployeeObject(resultSet));
        }
        return employees;
    }

    private Employee getEmployeeObject(ResultSet resultSet) throws SQLException {
        return new Employee(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("address"),
                resultSet.getDate("dob"),
                resultSet.getString("nic"),
                resultSet.getString("contact"),
                resultSet.getString("email"),
                GenderTypes.valueOf(resultSet.getString("gender")),
                resultSet.getString("position"),
                resultSet.getString("status"),
                resultSet.getString("password"),
                resultSet.getDate("created"),
                resultSet.getDate("last_updated")
        );
    }
}
