/*
@author : Dhanusha Perera
@date : 03/05/2021
*/
package com.elephasvacation.tms.web.api;

import com.elephasvacation.tms.web.commonConstant.Number;
import com.elephasvacation.tms.web.commonConstant.*;
import com.elephasvacation.tms.web.entity.Customer;
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

@WebServlet(name = "CustomerServlet", urlPatterns = "/api/v1/customers")
public class CustomerServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServlet.class);

    Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /* get id param from the query string. */
        String id = request.getParameter(Commons.ID);

        /* validation part -
        if id is not empty that means there is an id and it should be an integer greater than zero. */
        if (id != null && !(id.matches("^[1-9]+$"))) {
            /* send the client that it is a bad request. */
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            /* Error: id should be an integer. */
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    MessageFormat.format(ValidationMessages.INVALID_ID,
                            Commons.CUSTOMER));
            return;
        }

        /* set respond type. */
        response.setContentType(MimeTypes.Application.JSON);
        /* get the print-writer. */
        PrintWriter out = response.getWriter();

        /* get reference of the basic datasource from the servlet context. */
        BasicDataSource basicDataSource = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        /* holds the record(s). */
        List<Customer> customerList = new ArrayList<>();
        Customer customer;
        try {
            try (Connection connection = basicDataSource.getConnection()) {
                // get all customers in the database.
                PreparedStatement preparedStatement =
                        connection.prepareStatement("SELECT * FROM customer e" +
                                ((id != null) ? " WHERE e.id=?" : ";"));

                if (id != null) {
                    preparedStatement.setInt(Number.ONE, Integer.parseInt(id));
                }

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    /* creates a new customer object. */
                    customer = new Customer();

                    /* set customer details */
                    customer.setId(resultSet.getInt(Number.ONE));
                    customer.setName(resultSet.getString(Number.TWO));
                    customer.setNationality(resultSet.getString(Number.THREE));
                    customer.setPassportNo(resultSet.getString(Number.FOUR));
                    customer.setEmail(resultSet.getString(Number.FIVE));
                    customer.setCountryCallingCode(resultSet.getString(Number.SIX));
                    customer.setCountry(resultSet.getString(Number.SEVEN));
                    customer.setDescription(resultSet.getString(Number.EIGHT));
                    customer.setAdditionalNotes(resultSet.getString(Number.NINE));
                    customer.setAddedDate(resultSet.getDate(Number.TEN));
                    customer.setLastUpdated(resultSet.getDate(Number.ELEVEN));

                    /* add customer to the arraylist. */
                    customerList.add(customer);
                } // end of the while

            }

            if (id != null && customerList.isEmpty()) {
                /* no records found. */
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                logger.info(MessageFormat.format(
                        Commons.NO_RECORDS_FOUND,
                        Commons.CUSTOMER));
            } else if (id != null && customerList.size() == Number.ONE) {
                /* if found a record for the given ID, return the result. */
                out.print(jsonb.toJson(customerList.get(Number.ZERO)));
                logger.info(MessageFormat.format(
                        SuccessfulMessages.SEND_LIST_SUCCESSFULLY,
                        Commons.CUSTOMER,
                        customerList.size()), customerList.get(Number.ZERO));
            } else {
                /* write to the print-writer. */
                out.println(jsonb.toJson(customerList));
                logger.info(MessageFormat.format(
                        SuccessfulMessages.SEND_LIST_SUCCESSFULLY,
                        Commons.CUSTOMER,
                        customerList.size()), customerList);
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

        logger.info("Request Content Type: " + request.getContentType());

        /* check the request content type.
         * content type should be application/json. otherwise send bad request. */
        if (!request.getContentType().equals(MimeTypes.Application.JSON)) {
            String errorMessage = MessageFormat.format(ValidationMessages.REQUEST_CONTENT_TYPE_INVALID,
                    MimeTypes.Application.JSON);
            logger.info(errorMessage);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMessage);
            return;
        }

        try {

            /* read the request body. */
            Customer customer = jsonb.fromJson(request.getReader(), Customer.class);

            /* Validation error - customer id should be set to zero or
             * customer id should not be included to in the request header.
             * */
            if (customer.getId() != 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        ValidationMessages.INVALID_DATA_INPUT_ID_SHOULD_NOT_BE_INCLUDED);
                return;
            }

            /* validate user input. */
            String errors = validateUserInput(customer);
            if (errors != null) {
                /* there are errors. */
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, errors);
                return;
            }

            /* Check whether the email is already taken or not. */
            if (isValueInUse(Commons.CUSTOMER.toLowerCase(),
                    DatabaseColumnNames.EMAIL,
                    customer.getEmail(),
                    response)) {
                logger.info(ValidationMessages.EMAIL_IS_ALREADY_TAKEN,
                        customer.getEmail());
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
                                    "INSERT INTO customer " +
                                            "(name, nationality, passport_no, email, country_calling_code,country, " +
                                            "description, additional_notes) " +
                                            "VALUES(?,?,?,?,?,?,?,?)",
                                    Statement.RETURN_GENERATED_KEYS);

                    /* set customer related information with the insert query. */
                    preparedStatement.setString(Number.ONE, customer.getName());
                    preparedStatement.setString(Number.TWO, customer.getNationality());
                    preparedStatement.setString(Number.THREE, customer.getPassportNo());
                    preparedStatement.setString(Number.FOUR, customer.getEmail());
                    preparedStatement.setString(Number.FIVE, customer.getCountryCallingCode());
                    preparedStatement.setString(Number.SIX, customer.getCountry());
                    preparedStatement.setString(Number.SEVEN, customer.getDescription());
                    preparedStatement.setString(Number.EIGHT, customer.getAdditionalNotes());

                    preparedStatement.executeUpdate();
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                    if (generatedKeys.next()) {
                        /* insertion succeed. */
                        int generatedId = generatedKeys.getInt(Number.ONE);
                        response.setStatus(HttpServletResponse.SC_CREATED);
                        out.println(jsonb.toJson(generatedId));
                        logger.info(MessageFormat.format(
                                SuccessfulMessages.CREATED_RECORD_SUCCESSFUL,
                                Commons.CUSTOMER, generatedId));
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
            /* cannot parse the request header to an customer object. */
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
     * check the user input are valid.
     *
     * @returns true if valid,
     * otherwise false.
     */
    private String validateUserInput(Customer customer) {
        String errors = Commons.EMPTY_STRING;

        if (!isEmailValid(customer.getEmail())) {
            String emailInvalidMessage = MessageFormat.format(
                    ValidationMessages.INVALID_DATA_INPUT_CUSTOMIZED,
                    EntityFields.EMAIL);

            errors += emailInvalidMessage;
        }

        if (!isNationalityValid(customer.getNationality())) {
            String nationalityInvalidMessage = MessageFormat.format(
                    ValidationMessages.INVALID_DATA_INPUT_CUSTOMIZED,
                    EntityFields.NATIONALITY);

            errors += nationalityInvalidMessage;
        }

        if (!isPassportNoValid(customer.getPassportNo())) {
            String passportNoInvalidMessage = MessageFormat.format(
                    ValidationMessages.INVALID_DATA_INPUT_CUSTOMIZED,
                    EntityFields.PASSPORT_NO);

            errors += passportNoInvalidMessage;
        }


        if (!isCountryCallingCodeValid(customer.getCountryCallingCode())) {
            String countryCallingCodeInvalidMessage = MessageFormat.format(
                    ValidationMessages.INVALID_DATA_INPUT_CUSTOMIZED,
                    EntityFields.COUNTRY_CALLING_CODE);

            errors += countryCallingCodeInvalidMessage;
        }

        if (!isCountryValid(customer.getCountry())) {
            String countryInvalidMessage = MessageFormat.format(
                    ValidationMessages.INVALID_DATA_INPUT_CUSTOMIZED,
                    EntityFields.COUNTRY);

            errors += countryInvalidMessage;
        }

        return (errors.isEmpty()) ? null : errors;

    }

    /**
     * Check the given inputValue is already in use.
     *
     * @param inputValue              the input value.
     * @param databaseTableName       database table name that the record should be search on.
     * @param databaseTableColumnName database table column that the record should be search on.
     * @param response                if any error occurs; used to send the status code.
     * @return true, If the given input value is already in use.
     * Otherwise false.
     */
    private boolean isValueInUse(String databaseTableName,
                                 String databaseTableColumnName,
                                 String inputValue,
                                 HttpServletResponse response) throws IOException {

        boolean result = false;

        /* get reference of the basic datasource from the servlet context. */
        BasicDataSource basicDataSource = (BasicDataSource) getServletContext().getAttribute(Commons.CP);
        try {
            try (Connection connection = basicDataSource.getConnection()) {

                String query = "SELECT t.{0} FROM {1} t WHERE t.{2}=?";

                query = MessageFormat.format(query,
                        databaseTableColumnName, databaseTableName, databaseTableColumnName);

                PreparedStatement preparedStatement =
                        connection.prepareStatement(query);

                preparedStatement.setString(Number.ONE, inputValue);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    result = true;
                    break;
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION, sqlException);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return result;
    }


    /**
     * validate the given input.
     *
     * @returns true: if valid,
     * otherwise, false.
     */
    private boolean isNationalityValid(String nationality) {
        return nationality.matches("^[A-Za-z][A-Za-z ]+$");
    }

    /**
     * validate the given input.
     *
     * @returns true: if valid,
     * otherwise, false.
     */
    private boolean isPassportNoValid(String passportNo) {
        return passportNo.matches("^\\w+$");
    }

    /**
     * validate the given input.
     *
     * @returns true: if valid,
     * otherwise, false.
     */
    private boolean isEmailValid(String email) {
        return EmailValidator
                .getInstance().isValid(email);
    }

    /**
     * validate the given input.
     * starting with plus character,
     * and two or three digit should be there to a valid input.
     *
     * @returns true: if valid (ex: +94 OR +205),
     * otherwise, false.
     */
    private boolean isCountryCallingCodeValid(String countryCallingCode) {
        return countryCallingCode.matches("^[+]\\d{2,3}$");
    }

    /**
     * validate the given input.
     *
     * @returns true: if valid,
     * otherwise, false.
     */
    private boolean isCountryValid(String passportNo) {
        return passportNo.matches("^[A-Za-z][A-Za-z ]+$");
    }
}
