/*
@author : Dhanusha Perera
@date : 03/05/2021
*/
package com.elephasvacation.tms.web.api;

import com.elephasvacation.tms.web.commonConstant.Commons;
import com.elephasvacation.tms.web.commonConstant.EntityFields;
import com.elephasvacation.tms.web.commonConstant.ValidationMessages;
import com.elephasvacation.tms.web.entity.Customer;
import com.elephasvacation.tms.web.exception.ResponseExceptionUtil;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

@WebServlet(name = "CustomerServlet", urlPatterns = "/api/v1/customers/*")
public class CustomerServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServlet.class);

    Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            super.service(req, resp);
        } catch (Throwable t) {
            ResponseExceptionUtil.handle(t, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {

    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

    }


    /**
     * Check the id is valid.
     *
     * @return true if not valid.
     * otherwise, false.
     */
    private boolean isIdNotValid(String id) {
        return !id.matches("^[1-9]$|^[1-9]\\d+$");
    }

    /**
     * check the user input are valid.
     *
     * @return true if valid,
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


//        if (!isCountryCallingCodeValid(customer.getCountryCallingCode())) {
//            String countryCallingCodeInvalidMessage = MessageFormat.format(
//                    ValidationMessages.INVALID_DATA_INPUT_CUSTOMIZED,
//                    EntityFields.COUNTRY_CALLING_CODE);
//
//            errors += countryCallingCodeInvalidMessage;
//        }

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
//    private boolean isValueInUse(String databaseTableName,
//                                 String databaseTableColumnName,
//                                 String inputValue,
//                                 HttpServletResponse response) throws IOException {
//
//        boolean result = false;
//
//        /* get reference of the basic datasource from the servlet context. */
//        BasicDataSource basicDataSource = (BasicDataSource) getServletContext().getAttribute(Commons.CP);
//        try {
//            try (Connection connection = basicDataSource.getConnection()) {
//
//                String queryTemp = "SELECT t.{0} FROM {1} t WHERE t.{2}=?";
//
//                String query = MessageFormat.format(queryTemp,
//                        databaseTableColumnName, databaseTableName, databaseTableColumnName);
//
//                PreparedStatement preparedStatement =
//                        connection.prepareStatement(query);
//
//                preparedStatement.setString(Number.ONE, inputValue);
//                ResultSet resultSet = preparedStatement.executeQuery();
//
//                if (resultSet.next()) {
//                    result = true;
//                }
//            }
//        } catch (SQLException sqlException) {
//            sqlException.printStackTrace();
//            logger.error(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION, sqlException);
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        }
//
//        return result;
//    }


    /**
     * validate the given input.
     *
     * @return true: if valid,
     * otherwise, false.
     */
    private boolean isNationalityValid(String nationality) {
        return nationality.matches("^[A-Za-z][A-Za-z ]+$");
    }

    /**
     * validate the given input.
     *
     * @return true: if valid,
     * otherwise, false.
     */
    private boolean isPassportNoValid(String passportNo) {
        return passportNo.matches("^\\w+$");
    }

    /**
     * validate the given input.
     *
     * @return true: if valid,
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
     * @return true: if valid (ex: +94 OR +205),
     * otherwise, false.
     */
    private boolean isCountryCallingCodeValid(String countryCallingCode) {
        return countryCallingCode.matches("^[+]\\d{2,3}$");
    }

    /**
     * validate the given input.
     *
     * @return true: if valid,
     * otherwise, false.
     */
    private boolean isCountryValid(String passportNo) {
        return passportNo.matches("^[A-Za-z][A-Za-z ]+$");
    }
}
