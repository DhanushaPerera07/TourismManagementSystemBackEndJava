/*
@author : Dhanusha Perera
@date : 03/05/2021
*/
package com.elephasvacation.tms.web.api.servlet;

import com.elephasvacation.tms.web.api.APIFactory;
import com.elephasvacation.tms.web.api.APITypes;
import com.elephasvacation.tms.web.api.customer.customerAPI.CustomerAPI;
import com.elephasvacation.tms.web.api.customer.tourDetailsAPI.TourDetailsAPI;
import com.elephasvacation.tms.web.api.util.IDUtil;
import com.elephasvacation.tms.web.api.validation.CommonValidation;
import com.elephasvacation.tms.web.commonConstant.Number;
import com.elephasvacation.tms.web.commonConstant.*;
import com.elephasvacation.tms.web.dto.CreatedOutputDTO;
import com.elephasvacation.tms.web.dto.CustomerDTO;
import com.elephasvacation.tms.web.dto.TourDetailsDTO;
import com.elephasvacation.tms.web.entity.Customer;
import com.elephasvacation.tms.web.exception.HttpResponseException;
import com.elephasvacation.tms.web.exception.ResponseExceptionUtil;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.MessageFormat;
import java.util.List;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int customerID;
        int tourDetailID;
        CustomerAPI customerAPI = APIFactory.getInstance().getAPI(APITypes.CUSTOMER);
        TourDetailsAPI tourDetailAPI = APIFactory.getInstance().getAPI(APITypes.TOUR_DETAIL);
        PrintWriter out = response.getWriter();
        response.setContentType(MimeTypes.Application.JSON);

//        System.out.println("request.getContextPath() : " + request.getContextPath());
//        System.out.println("request.getServletPath() : " + request.getServletPath());
//        System.out.println("request.getQueryString() : " + request.getQueryString());
//        System.out.println("request.getPathInfo() : " + request.getPathInfo());
        BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        /* get all customers. */
        if (request.getPathInfo() == null || request.getPathInfo()
                .replace("/", "")
                .trim()
                .isEmpty()) {
            try (Connection connection = bds.getConnection();) {
                customerAPI.setConnection(connection);
                List<CustomerDTO> allCustomers = customerAPI.getAllCustomers();
                out.println(jsonb.toJson(allCustomers));
            } catch (SQLException sqlException) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION, sqlException);
                sqlException.printStackTrace();
            } catch (Exception exception) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG, exception);
                exception.printStackTrace();
            }
        }

        /* get customer by customerID. */
        else if (request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .replace("/", "")
                        .matches("^c\\d{3}$")) {

            String[] splitURIArray = IDUtil.getSplitArray(request.getPathInfo());

            /* extracting customer ID from URL. */
            customerID = IDUtil.extractIDFrom(splitURIArray,
                    Number.ONE,
                    "c",
                    "Invalid Customer ID");

            try (Connection connection = bds.getConnection();) {

                customerAPI.setConnection(connection);
                CustomerDTO customerDTO = customerAPI.getCustomerByID(customerID);
                if (customerDTO == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    out.println(jsonb.toJson(customerDTO));
                }
            } catch (SQLException sqlException) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION, sqlException);
                sqlException.printStackTrace();
            } catch (Exception exception) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG, exception);
                exception.printStackTrace();
            }

        }

        /* get all tour details of a customer. */
        else if (request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .matches("^/c\\d{3}/tour-details|/c\\d{3}/tour-details/$")) {

            String[] splitURIArray = IDUtil.getSplitArray(request.getPathInfo());

            /* extracting customer ID from URL. */
            customerID = IDUtil.extractIDFrom(splitURIArray,
                    Number.ONE,
                    "c",
                    "Invalid Customer ID");

            try (Connection connection = bds.getConnection();) {
                tourDetailAPI.setConnection(connection);
                List<TourDetailsDTO> tourDetailsDTOS = tourDetailAPI.getAllTourDetailByCustomerID(customerID);
                out.println(jsonb.toJson(tourDetailsDTOS));
            } catch (SQLException sqlException) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION, sqlException);
                sqlException.printStackTrace();
            } catch (Exception exception) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG, exception);
                exception.printStackTrace();
                throw new RuntimeException(exception);
            }

        }


        /* get a tour details(single tour-detail) of a customer. */
        else if (request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .matches("^/c\\d{3}/tour-details/td\\d{3}|/C\\d{3}/tour-details/td\\d{3}/$")) {

            String[] splitURIArray = IDUtil.getSplitArray(request.getPathInfo());

            /* extracting customer ID from URL. */
            customerID = IDUtil.extractIDFrom(splitURIArray,
                    Number.ONE,
                    "c",
                    "Invalid Customer ID");

            /* extracting tour-detail ID from URL. */
            tourDetailID = IDUtil.extractIDFrom(splitURIArray,
                    Number.THREE,
                    "td",
                    "Invalid TourDetail ID");

            try (Connection connection = bds.getConnection();) {
                tourDetailAPI.setConnection(connection);
                TourDetailsDTO tourDetailsDTO = tourDetailAPI.getTourDetailsByIDAndCustomerID(customerID,
                        tourDetailID);
                if (tourDetailsDTO == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    out.println(jsonb.toJson(tourDetailsDTO));
                }
            } catch (SQLException sqlException) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION, sqlException);
                sqlException.printStackTrace();
            } catch (Exception exception) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG, exception);
                exception.printStackTrace();
                throw new RuntimeException(exception);
            }
        } else {
            /* request.getPathInfo not matched with any if condition. */
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        CustomerAPI customerAPI = APIFactory.getInstance().getAPI(APITypes.CUSTOMER);
        TourDetailsAPI tourDetailAPI = APIFactory.getInstance().getAPI(APITypes.TOUR_DETAIL);
        /* get the print-writer. */
        PrintWriter out = response.getWriter();

        /* check the request content type.
         * content type should be application/json. otherwise send bad request. */
        if (CommonValidation.isContentTypeNotJSON(request)) {
            CommonValidation.sendBadRequest(response, logger);
            return;
        }

        BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        /* CREATE a customer. */
        if (request.getPathInfo() == null || request.getPathInfo()
                .replace("/", "")
                .trim().isEmpty()) {

            /* read the request body. */
            CustomerDTO customerDTO = null;
            try {
                customerDTO = jsonb.fromJson(request.getReader(), CustomerDTO.class);
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
            }

            try (Connection connection = bds.getConnection();) {
                customerAPI.setConnection(connection);
                Integer generatedCustomerID = customerAPI.createCustomer(customerDTO);
                if (generatedCustomerID != null && generatedCustomerID > 0) {
                    /* Customer created successfully. */
                    /* response-content-type. */
                    response.setContentType(MimeTypes.Application.JSON);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    out.println(jsonb.toJson(new CreatedOutputDTO(String.format("C%03d", generatedCustomerID))));
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } catch (SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
                /* duplicate key found (integrity constraint violation). */
                logger.error(sqlIntegrityConstraintViolationException.getMessage() +
                                ValidationMessages.SQL_INTEGRITY_CONSTRAINT_VIOLATION,
                        sqlIntegrityConstraintViolationException);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        ValidationMessages.EMAIL_IS_ALREADY_TAKEN);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                logger.error(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION, sqlException);
            } catch (Exception exception) {
                exception.printStackTrace();
                logger.error(FailedMessages.SOMETHING_WENT_WRONG, exception);
            }


        } // end-create-customer


        /* create a tour-detail. */
        else if (request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .matches("^/c\\d{3}/tour-details|/c\\d{3}/tour-details/$")) {

            /* read the request body. */
            TourDetailsDTO tourDetailsDTO = null;
            try {
                tourDetailsDTO = jsonb.fromJson(request.getReader(), TourDetailsDTO.class);
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
            }


            try (Connection connection = bds.getConnection();) {
                tourDetailAPI.setConnection(connection);
                Integer generatedTourDetailID = tourDetailAPI.createTourDetails(tourDetailsDTO);
                if (generatedTourDetailID != null && generatedTourDetailID > 0) {
                    /* TourDetail created successfully. */
                    /* response-content-type. */
                    response.setContentType(MimeTypes.Application.JSON);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    out.println(jsonb.toJson(new CreatedOutputDTO(String.format("TD%03d", generatedTourDetailID))));
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                logger.error(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION, sqlException);
            } catch (Exception exception) {
                exception.printStackTrace();
                logger.error(FailedMessages.SOMETHING_WENT_WRONG, exception);
            }


        } // end-create-tour-detail

        else {
            /* request.getPathInfo not matched with any if condition. */
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }


    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // variables
        int customerID;
        int tourDetailID;

        /* Define APIs. */
        CustomerAPI customerAPI = APIFactory.getInstance().getAPI(APITypes.CUSTOMER);
        TourDetailsAPI tourDetailAPI = APIFactory.getInstance().getAPI(APITypes.TOUR_DETAIL);

        /* check the request content type.
         * content type should be application/json. otherwise send bad request. */
        if (CommonValidation.isContentTypeNotJSON(request)) {
            CommonValidation.sendBadRequest(response, logger);
            return;
        }

        /* get datasource. */
        BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        /* UPDATE a customer. */
        if (request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .replace("/", "")
                        .matches("^c\\d{3}$")) {


            String[] splitURIArray = IDUtil.getSplitArray(request.getPathInfo());

            /* extracting customer ID from URL. */
            customerID = IDUtil.extractIDFrom(splitURIArray,
                    Number.ONE,
                    "c",
                    "Invalid Customer ID");


            try (Connection connection = bds.getConnection();) {

                /* initialize the connection. */
                customerAPI.setConnection(connection);

                /* check matching record in DB. */
                if (customerAPI.getCustomerByID(customerID) == null) {
                    /* no matching record found. */
                    Throwable t = new HttpResponseException(404,
                            MessageFormat.format(Commons.NO_MATCHING_RECORDS_FOUND,
                                    Commons.CUSTOMER),
                            null);
                    ResponseExceptionUtil.handle(t, response);
                    return;
                }

                /* read the request body. */
                CustomerDTO customerDTO = jsonb.fromJson(request.getReader(), CustomerDTO.class);
                customerDTO.setId(customerID);

                if (customerAPI.updateCustomer(customerDTO)) {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    logger.error(FailedMessages.SOMETHING_WENT_WRONG);
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
            } catch (SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
                /* duplicate key found (integrity constraint violation). */
                logger.error(sqlIntegrityConstraintViolationException.getMessage() +
                                ValidationMessages.SQL_INTEGRITY_CONSTRAINT_VIOLATION,
                        sqlIntegrityConstraintViolationException);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        ValidationMessages.EMAIL_IS_ALREADY_TAKEN);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                logger.error(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION, sqlException);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (Exception exception) {
                /* something went wrong. */
                exception.printStackTrace();
                logger.error(FailedMessages.SOMETHING_WENT_WRONG, exception);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } // end-update-customer



        /* UPDATE a tour-detail. */
        else if (request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .matches("^/c\\d{3}/tour-details/td\\d{3}|/C\\d{3}/tour-details/td\\d{3}/$")) {

            String[] splitURIArray = IDUtil.getSplitArray(request.getPathInfo());

            /* extracting customer ID from URL. */
            customerID = IDUtil.extractIDFrom(splitURIArray,
                    Number.ONE,
                    "c",
                    "Invalid Customer ID");

            /* extracting tour-detail ID from URL. */
            tourDetailID = IDUtil.extractIDFrom(splitURIArray,
                    Number.THREE,
                    "td",
                    "Invalid TourDetail ID");


            try (Connection connection = bds.getConnection();) {

                /* initialize the connection. */
                customerAPI.setConnection(connection);
                tourDetailAPI.setConnection(connection);

                /* check matching record in DB. */
                if (customerAPI.getCustomerByID(customerID) == null) {
                    /* no matching record found. */
                    Throwable t = new HttpResponseException(404,
                            MessageFormat.format(Commons.NO_MATCHING_RECORDS_FOUND,
                                    Commons.CUSTOMER),
                            null);
                    ResponseExceptionUtil.handle(t, response);
                    return;
                }

                /* check matching record in DB. */
                if (tourDetailAPI.getTourDetailsByIDAndCustomerID(customerID, tourDetailID) == null) {
                    /* no matching record found. */
                    Throwable t = new HttpResponseException(404,
                            MessageFormat.format(Commons.NO_MATCHING_RECORDS_FOUND,
                                    Commons.TOUR_DETAIL),
                            null);
                    ResponseExceptionUtil.handle(t, response);
                    return;
                }


                /* read the request body. */
                TourDetailsDTO tourDetailsDTO = jsonb.fromJson(request.getReader(), TourDetailsDTO.class);
                tourDetailsDTO.setId(tourDetailID); // set tourDetailID
                tourDetailsDTO.setCustomerId(customerID); // set customerID

                /* TODO: validate TourDetailsDTO. */

                if (tourDetailAPI.updateTourDetails(tourDetailsDTO)) {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    logger.error(FailedMessages.SOMETHING_WENT_WRONG);
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
            } catch (SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
                /* duplicate key found (integrity constraint violation). */
                logger.error(sqlIntegrityConstraintViolationException.getMessage() +
                                ValidationMessages.SQL_INTEGRITY_CONSTRAINT_VIOLATION,
                        sqlIntegrityConstraintViolationException);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        ValidationMessages.EMAIL_IS_ALREADY_TAKEN);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                logger.error(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION, sqlException);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (Exception exception) {
                /* something went wrong. */
                exception.printStackTrace();
                logger.error(FailedMessages.SOMETHING_WENT_WRONG, exception);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } // end-update-tour-detail

        else {
            /* request.getPathInfo not matched with any if condition. */
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }

    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // variables
        int customerID;
        int tourDetailID;

        /* Define APIs. */
        CustomerAPI customerAPI = APIFactory.getInstance().getAPI(APITypes.CUSTOMER);
        TourDetailsAPI tourDetailAPI = APIFactory.getInstance().getAPI(APITypes.TOUR_DETAIL);

        /* get datasource. */
        BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        /* DELETE a customer. */
        if (request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .replace("/", "")
                        .matches("^c\\d{3}$")) {

            String[] splitURIArray = IDUtil.getSplitArray(request.getPathInfo());

            /* extracting customer ID from URL. */
            customerID = IDUtil.extractIDFrom(splitURIArray,
                    Number.ONE,
                    "c",
                    "Invalid Customer ID");

            try (Connection connection = bds.getConnection();) {
                /* initialize the connection. */
                customerAPI.setConnection(connection);

                /* check matching record in DB. */
                if (customerAPI.getCustomerByID(customerID) == null) {
                    /* no matching record found. */
                    Throwable t = new HttpResponseException(404,
                            MessageFormat.format(Commons.NO_MATCHING_RECORDS_FOUND,
                                    Commons.CUSTOMER),
                            null);
                    ResponseExceptionUtil.handle(t, response);
                    return;
                }


                if (customerAPI.deleteCustomer(customerID)) {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    logger.error(FailedMessages.SOMETHING_WENT_WRONG);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }

            } catch (SQLException sqlException) {
                /* sql error. */
                sqlException.printStackTrace();
                logger.error(FailedMessages.SOMETHING_WENT_WRONG, sqlException);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (Exception exception) {
                exception.printStackTrace();
            }


        } // end-delete-customer

        /* DELETE a tour-detail. */
        else if (request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .matches("^/c\\d{3}/tour-details/td\\d{3}|/C\\d{3}/tour-details/td\\d{3}/$")) {

            String[] splitURIArray = IDUtil.getSplitArray(request.getPathInfo());

            /* extracting customer ID from URL. */
            customerID = IDUtil.extractIDFrom(splitURIArray,
                    Number.ONE,
                    "c",
                    "Invalid Customer ID");

            /* extracting tour-detail ID from URL. */
            tourDetailID = IDUtil.extractIDFrom(splitURIArray,
                    Number.THREE,
                    "td",
                    "Invalid TourDetail ID");

            try (Connection connection = bds.getConnection();) {
                /* initialize the connection. */
                customerAPI.setConnection(connection);
                tourDetailAPI.setConnection(connection);

                /* check matching record in DB. */
                if (customerAPI.getCustomerByID(customerID) == null) {
                    /* no matching record found. */
                    throw new HttpResponseException(404,
                            MessageFormat.format(Commons.NO_MATCHING_RECORDS_FOUND,
                                    Commons.CUSTOMER),
                            null);
                }


                /* check matching record in DB. */
                if (tourDetailAPI.getTourDetailsByIDAndCustomerID(customerID, tourDetailID) == null) {
                    /* no matching record found. */
                    Throwable t = new HttpResponseException(404,
                            MessageFormat.format(Commons.NO_MATCHING_RECORDS_FOUND,
                                    Commons.TOUR_DETAIL),
                            null);
                    ResponseExceptionUtil.handle(t, response);
                    return;
                }


                if (tourDetailAPI.deleteTourDetails(tourDetailID)) {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    logger.error(FailedMessages.SOMETHING_WENT_WRONG);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }

            } catch (SQLException sqlException) {
                /* sql error. */
                sqlException.printStackTrace();
                logger.error(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION, sqlException);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (Exception exception) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG, exception);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                exception.printStackTrace();
            }

        } // end-delete-tour-detail

        else {
            /* request.getPathInfo not matched with any if condition. */
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }

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

                String queryTemp = "SELECT t.{0} FROM {1} t WHERE t.{2}=?";

                String query = MessageFormat.format(queryTemp,
                        databaseTableColumnName, databaseTableName, databaseTableColumnName);

                PreparedStatement preparedStatement =
                        connection.prepareStatement(query);

                preparedStatement.setString(Number.ONE, inputValue);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    result = true;
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
