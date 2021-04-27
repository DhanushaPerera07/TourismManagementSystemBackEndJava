/*
@author : Dhanusha Perera
@since : 21/04/2021
*/
package com.elephasvacation.tms.web.api;

import com.elephasvacation.tms.web.commonConstant.Number;
import com.elephasvacation.tms.web.commonConstant.*;
import com.elephasvacation.tms.web.model.Employee;
import com.elephasvacation.tms.web.model.enumeration.GenderTypes;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "EmployeeServlet", urlPatterns = "/v1/api/employees")
public class EmployeeServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServlet.class);

    Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* get id param from the query string. */
        String id = request.getParameter(Commons.ID);

        if (id != null && !(id.matches("^[1-9]+$"))) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            /* Error: employee id should be an integer. */
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    MessageFormat.format(ValidationMessages.INVALID_ID,
                            Employee.class.getSimpleName()));
            return;
        }

        /* set respond type. */
        response.setContentType(MimeTypes.Application.JSON);
        /* get the print-writer. */
        PrintWriter out = response.getWriter();

        /* get reference of the basic datasource from the servlet context. */
        BasicDataSource basicDataSource = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        /* holds the record(s). */
        List<Employee> employeeList = new ArrayList<>();
        Employee employee;
        try {
            try (Connection connection = basicDataSource.getConnection()) {
                // get all customers in the database.
                PreparedStatement preparedStatement =
                        connection.prepareStatement("SELECT * FROM employee e" +
                                ((id != null) ? " WHERE e.id=?" : ";"));

                if (id != null) {
                    preparedStatement.setInt(Number.ONE, Integer.parseInt(id));
                }

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    /* creates a new employee object. */
                    employee = new Employee();

                    /* set employee details */
                    employee.setId(resultSet.getInt(Number.ONE));
                    employee.setName(resultSet.getString(Number.TWO));
                    employee.setAddress(resultSet.getString(Number.THREE));
                    employee.setDateOfBirth(resultSet.getDate(Number.FOUR));
                    employee.setNic(resultSet.getString(Number.FIVE));
                    employee.setContact(resultSet.getString(Number.SIX));
                    employee.setEmail(resultSet.getString(Number.SEVEN));
                    employee.setGender(GenderTypes.valueOf(resultSet.getString(Number.EIGHT)));
                    employee.setPosition(resultSet.getString(Number.NINE));
                    employee.setStatus(resultSet.getString(Number.TEN));
                    employee.setPassword(resultSet.getString(Number.ELEVEN));

                    /* add employee to the arraylist. */
                    employeeList.add(employee);
                } // while

            }

            if (id != null && employeeList.isEmpty()) {
                /* no records found. */
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else if (id != null && employeeList.size() == Number.ONE) {
                /* if found a record for the given ID, return the result. */
                out.print(jsonb.toJson(employeeList.get(Number.ZERO)));
            } else {
                /* write to the print-writer. */
                out.println(jsonb.toJson(employeeList));
            }
        } catch (SQLException sqlException) {
            logger.error(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION, sqlException);
            sqlException.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
