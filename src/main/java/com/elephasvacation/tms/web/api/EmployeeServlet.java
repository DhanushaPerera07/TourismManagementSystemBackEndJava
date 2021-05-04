/*
@author : Dhanusha Perera
@since : 21/04/2021
*/
package com.elephasvacation.tms.web.api;

import com.elephasvacation.tms.web.commonConstant.Number;
import com.elephasvacation.tms.web.commonConstant.*;
import com.elephasvacation.tms.web.entity.Employee;
import com.elephasvacation.tms.web.entity.enumeration.GenderTypes;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "EmployeeServlet", urlPatterns = "/api/v1/employees")
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

        /* get the print-writer. */
        PrintWriter out = response.getWriter();

        try {
            /* read the request body. */
            Employee employee = jsonb.fromJson(request.getReader(), Employee.class);

            /* employee id should be set to zero or
             * employee id should not be included to in the request header.
             * */
            if (employee.getId() != 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        ValidationMessages.INVALID_DATA_INPUT_ID_SHOULD_NOT_BE_INCLUDED);
                return;
            }

            /* validate user input. */
            String errors = validateUserInput(employee);
            if (errors != null) {
                /* there are errors. */
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, errors);
                return;
            }

            /* Check whether the email is already taken or not. */
            if (isEmailUsed(employee.getEmail())) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        ValidationMessages.EMAIL_IS_ALREADY_TAKEN);
                return;
            }

            /* get reference of the basic datasource from the servlet context. */
            BasicDataSource basicDataSource = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

            try {
                try (Connection connection = basicDataSource.getConnection()) {

                    PreparedStatement preparedStatement = connection
                            .prepareStatement(
                                    "INSERT INTO employee " +
                                            "(name, address, dob, nic, contact,email, " +
                                            "gender, position, status, password) " +
                                            "VALUES(?,?,?,?,?,?,?,?,?,?)",
                                    Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(Number.ONE, employee.getName());
                    preparedStatement.setString(Number.TWO, employee.getAddress());
                    preparedStatement.setDate(Number.THREE, employee.getDateOfBirth());
                    preparedStatement.setString(Number.FOUR, employee.getNic());
                    preparedStatement.setString(Number.FIVE, employee.getContact());
                    preparedStatement.setString(Number.SIX, employee.getEmail());
                    preparedStatement.setString(Number.SEVEN, employee.getGender().toString());
                    preparedStatement.setString(Number.EIGHT, employee.getPosition());
                    preparedStatement.setString(Number.NINE, employee.getStatus());
                    preparedStatement.setString(Number.TEN, employee.getPassword());

                    preparedStatement.executeUpdate();
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                    if (generatedKeys.next()) {
                        /* insertion succeed. */
                        int generatedId = generatedKeys.getInt(Number.ONE);
                        response.setStatus(HttpServletResponse.SC_CREATED);
                        out.println(jsonb.toJson(generatedId));
                        logger.info(MessageFormat.format(
                                SuccessfulMessages.CREATED_RECORD_SUCCESSFUL,
                                Employee.class.getSimpleName(), generatedId));
                    } else {
                        /* insertion failed. */
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        logger.error(FailedMessages.SOMETHING_WENT_WRONG);
                    }
                }
            } catch (SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
                /* duplicate key found (integrity constraint violation). */
                logger.error(ValidationMessages.SQL_INTEGRITY_CONSTRAINT_VIOLATION,
                        sqlIntegrityConstraintViolationException);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ValidationMessages.SQL_INTEGRITY_CONSTRAINT_VIOLATION);
            } catch (SQLException sqlException) {
                /* sql error. */
                sqlException.printStackTrace();
                logger.error(FailedMessages.SOMETHING_WENT_WRONG, sqlException);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } catch (JsonbException jsonbException) {
            /* cannot parse the request header to an employee object. */
            jsonbException.printStackTrace();
            logger.error(ValidationMessages.INVALID_DATA_INPUT, jsonbException);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException ioException) {
            /* inputStream error when reading the request object. */
            ioException.printStackTrace();
            logger.error(FailedMessages.SOMETHING_WENT_WRONG_READING_REQUEST_BODY, ioException);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception exception) {
            /* something went wrong. */
            exception.printStackTrace();
            logger.error(FailedMessages.SOMETHING_WENT_WRONG, exception);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            /* read the request body. */
            Employee employee = jsonb.fromJson(request.getReader(), Employee.class);

            /* Client should specify the id in the request body only.*/
            if (!isIdValid(Integer.toString(employee.getId()))) {
                /* send error - id is not an integer. */
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        MessageFormat.format(
                                ValidationMessages.INVALID_ID,
                                Employee.class.getSimpleName()
                        ));

                return;
            }

            /* validate user input. */
            String errors = validateUserInput(employee);
            if (errors != null) {
                /* there are errors. */
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, errors);
                return;
            }

            /* perform update operation */
            /* get reference of the basic datasource from the servlet context. */
            BasicDataSource basicDataSource = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

            try (Connection connection = basicDataSource.getConnection()) {
                /* check whether there is a matching record. */
                PreparedStatement preparedStatement = connection
                        .prepareStatement("SELECT e.id FROM employee e WHERE e.id=?");

                preparedStatement.setInt(Number.ONE, employee.getId());

                /* check whether there is a matching record for the given id. */
                if (preparedStatement.executeQuery().next()) {
                    /* record is available for the given id.
                     * then, perform update. */
                    preparedStatement =
                            connection.prepareStatement("UPDATE employee e " +
                                    "SET name=?, address=?, dob=?, nic=?, " +
                                    "contact=?,email=?, gender=?, position=?, " +
                                    "status=?, password=? WHERE e.id=?");

                    preparedStatement.setString(Number.ONE, employee.getName());
                    preparedStatement.setString(Number.TWO, employee.getAddress());
                    preparedStatement.setDate(Number.THREE, employee.getDateOfBirth());
                    preparedStatement.setString(Number.FOUR, employee.getNic());
                    preparedStatement.setString(Number.FIVE, employee.getContact());
                    preparedStatement.setString(Number.SIX, employee.getEmail());
                    preparedStatement.setString(Number.SEVEN, employee.getGender().toString());
                    preparedStatement.setString(Number.EIGHT, employee.getPosition());
                    preparedStatement.setString(Number.NINE, employee.getStatus());
                    preparedStatement.setString(Number.TEN, employee.getPassword());
                    preparedStatement.setInt(Number.ELEVEN, employee.getId());

                    if (preparedStatement.executeUpdate() > 0) {
                        /* update successful. */
                        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    } else {
                        /* update not succeed. */
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }

                } else {
                    /* no record found for that given id. */
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }

            }


        } catch (JsonbException jsonbException) {
            /* cannot parse the request header to an employee object. */
            jsonbException.printStackTrace();
            logger.error(ValidationMessages.INVALID_DATA_INPUT, jsonbException);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException ioException) {
            /* inputStream error when reading the request object. */
            ioException.printStackTrace();
            logger.error(FailedMessages.SOMETHING_WENT_WRONG_READING_REQUEST_BODY, ioException);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
            /* duplicate key found (integrity constraint violation). */
            logger.error(sqlIntegrityConstraintViolationException.getMessage() +
                            ValidationMessages.SQL_INTEGRITY_CONSTRAINT_VIOLATION,
                    sqlIntegrityConstraintViolationException);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    ValidationMessages.EMAIL_IS_ALREADY_TAKEN);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error(FailedMessages.SOMETHING_WENT_WRONG, sqlException);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception exception) {
            /* something went wrong. */
            exception.printStackTrace();
            logger.error(FailedMessages.SOMETHING_WENT_WRONG, exception);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter(Commons.ID);
        if (id == null) {
            /* send error - id is required. */
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    MessageFormat.format(
                            ValidationMessages.ID_IS_REQUIRED +
                                    Commons.EMPTY_SPACE +
                                    ValidationMessages.INTEGERS_ARE_ONLY_ACCEPTED_EXCEPT_ZERO,
                            Employee.class.getSimpleName()
                    ));
            return;
        }

        if (!isIdValid(id)) {
            /* send error - id is not an integer. */
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    MessageFormat.format(
                            ValidationMessages.INVALID_ID,
                            Employee.class.getSimpleName()
                    ));

            return;
        }

        /* get reference of the basic datasource from the servlet context. */
        BasicDataSource basicDataSource = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        try {
            try (Connection connection = basicDataSource.getConnection()) {

                PreparedStatement preparedStatement = connection
                        .prepareStatement("DELETE FROM employee e WHERE e.id=?");
                preparedStatement.setInt(Number.ONE, Integer.parseInt(id));

                if (preparedStatement.executeUpdate() > 0) {
                    /* deleted successfully. */
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    /* not found matching record to delete. */
                    logger.info(MessageFormat.format(
                            ValidationMessages.RECORD_IS_NOT_FOUND,
                            Employee.class.getSimpleName(),
                            id,
                            ValidationMessages.TO_DELETE
                    ));
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            }

        } catch (SQLException sqlException) {
            /* sql error. */
            sqlException.printStackTrace();
            logger.error(FailedMessages.SOMETHING_WENT_WRONG, sqlException);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
            logger.error(FailedMessages.FAILED_PARSING_TO_INTEGER, numberFormatException);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Check the id is valid.
     *
     * @returns true if valid.
     * otherwise, false.
     */
    private boolean isIdValid(String id) {
        return id.matches("^[1-9]$|^[1-9]\\d+$");
    }

    /**
     * Check the given email is already in use.
     *
     * @return true, If the given email is already in use.
     * Otherwise false.
     */
    private boolean isEmailUsed(String email) {

        boolean result = false;

        /* get reference of the basic datasource from the servlet context. */
        BasicDataSource basicDataSource = (BasicDataSource) getServletContext().getAttribute(Commons.CP);
        try {
            try (Connection connection = basicDataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT e.email FROM employee e WHERE e.email=?");

                preparedStatement.setString(Number.ONE, email);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    result = true;
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return result;
    }


    /**
     * check the user input are valid.
     *
     * @returns true if valid,
     * otherwise false.
     */
    private String validateUserInput(Employee employee) {

        String errors = Commons.EMPTY_STRING;

        if (!isEmailValid(employee.getEmail())) {
            String emailInvalidMessage = MessageFormat.format(
                    ValidationMessages.INVALID_DATA_INPUT_CUSTOMIZED,
                    EntityFields.EMAIL);

            errors += emailInvalidMessage;
        }

        if (!isNICValid(employee.getNic())) {
            String nicInvalidMessage = MessageFormat.format(
                    ValidationMessages.INVALID_DATA_INPUT_CUSTOMIZED,
                    EntityFields.NIC);

            errors += nicInvalidMessage;
        }

        if (!isContactNumberValid(employee.getContact())) {
            String contactNoInvalidMessage = MessageFormat.format(
                    ValidationMessages.INVALID_DATA_INPUT_CUSTOMIZED,
                    EntityFields.CONTACT_NO);

            errors += contactNoInvalidMessage;
        }

        return (errors.isEmpty()) ? null : errors;
    }


    /**
     * Check whether the email is in valid format.
     *
     * @returns true if email is valid.
     * otherwise false.
     */
    private boolean isEmailValid(String email) {
        return EmailValidator
                .getInstance().isValid(email);
    }

    /**
     * Check whether the nic is in valid format.
     *
     * @returns true if valid.
     * otherwise false.
     */
    private boolean isNICValid(String nic) {
        return nic.matches("^\\d{12}$|^\\d{9}[V|v]$");
    }

    /**
     * Check whether the contact number is in valid format.
     *
     * @returns true if valid.
     * otherwise false.
     */
    private boolean isContactNumberValid(String contactNo) {
        return contactNo.matches("^\\d{10}$|^[+]94\\d{9}$");
    }
}
