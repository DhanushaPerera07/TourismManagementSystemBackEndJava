/*
@author : Dhanusha Perera
@date : 04/05/2021
*/
package com.elephasvacation.tms.web.api;

import com.elephasvacation.tms.web.commonConstant.Number;
import com.elephasvacation.tms.web.commonConstant.*;
import com.elephasvacation.tms.web.entity.TourDetail;
import com.elephasvacation.tms.web.entity.enumeration.TourDetailStatusTypes;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
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
import java.math.BigDecimal;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@WebServlet(name = "TourDetailServlet", urlPatterns = "/api/v1/tour-details")
public class TourDetailServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(TourDetailServlet.class);

    Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        /* get id param from the query string. */
        String id = request.getParameter(QueryParameters.ID);
        String customerId = request.getParameter(QueryParameters.TourDetail.CUSTOMER_ID);

        /* if client provided both tourDetail ID and customer id, bad request should be sent. */
        if (id != null && customerId != null) {
            /* send the client that it is a bad request. */
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    MessageFormat.format(ValidationMessages.ID_AND_FOREIGN_KEY_ID_BOTH_NOT_ALLOWED,
                            Commons.TOUR_DETAIL,
                            Commons.CUSTOMER));

            return;
        }

        /* validation part - if id is not empty that means
        there is an id and it should be an integer greater than zero. */
        if (id != null && !(id.matches("^[1-9]+$"))) {
            /* send the client that it is a bad request. */
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            /* Error: id should be an integer. */
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    MessageFormat.format(ValidationMessages.INVALID_ID,
                            Commons.TOUR_DETAIL));
            return;
        }

        /* validation part for customerId - if id is not empty that means
        there is an id and it should be an integer greater than zero. */
        if (customerId != null && !(customerId.matches("^[1-9]+$"))) {
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
        List<TourDetail> tourDetailList = new ArrayList<>();
        TourDetail tourDetail;

        try {

            try (Connection connection = basicDataSource.getConnection()) {
                // get all tourDetails in the database.
                PreparedStatement preparedStatement =
                        connection.prepareStatement("SELECT * FROM tour_detail td" +
                                ((id == null && customerId != null) ?
                                        " WHERE td.customer_id=?" :
                                        " WHERE td.id=?"));

                try {
                    /* set input to the query. */
                    if (id != null) {
                        preparedStatement.setInt(Number.ONE, Integer.parseInt(id));
                    } else {
                        preparedStatement.setInt(Number.ONE, Integer.parseInt(customerId));
                    }
                } catch (NumberFormatException numberFormatException) {
                    /* error: cannot be converted to an integer. */
                    numberFormatException.printStackTrace();
                    logger.error(FailedMessages.CANNOT_GET_TO_BE_CONVERT_TO_AN_INTEGER,
                            numberFormatException);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }

                /* execute the query. */
                ResultSet resultSet = preparedStatement.executeQuery();

                /* go through result set. */
                while (resultSet.next()) {
                    /* creates a new tourDetail object. */
                    tourDetail = new TourDetail();

                    /* set tourDetail details */
                    tourDetail.setId(resultSet.getInt(Number.ONE));
                    tourDetail.setNoOfDays(resultSet.getInt(Number.TWO));
                    tourDetail.setNoOfPeople(resultSet.getInt(Number.THREE));
                    tourDetail.setNoOfChildren(resultSet.getInt(Number.FOUR));
                    tourDetail.setStarCategory(resultSet.getInt(Number.FIVE));
                    tourDetail.setArrivalDate(resultSet.getDate(Number.SIX));
                    tourDetail.setDepartureDate(resultSet.getDate(Number.SEVEN));
                    tourDetail.setStatus(TourDetailStatusTypes.valueOf(resultSet.getString(Number.EIGHT)));
                    tourDetail.setExchangeRate(new BigDecimal(resultSet.getString(Number.NINE)));
                    tourDetail.setTourAgent(resultSet.getString(Number.TEN));
                    tourDetail.setAgentProfit(new BigDecimal(resultSet.getString(Number.ELEVEN)));
                    tourDetail.setCustomerId(resultSet.getInt(Number.TWELVE));
                    tourDetail.setCreated(resultSet.getDate(Number.THIRTEEN));
                    tourDetail.setLastUpdated(resultSet.getDate(Number.FOURTEEN));

                    /* add tourDetail to the arraylist. */
                    tourDetailList.add(tourDetail);
                } // end of the while
            }

            /* if client gave customer id only. */
            if (id == null && customerId != null) {
                if (tourDetailList.size() != Number.ZERO) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(jsonb.toJson(tourDetailList));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            }

            /* if client gave tour id only. */
            if (id != null && customerId == null) {
                if (tourDetailList.size() > 0) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(jsonb.toJson(tourDetailList.get(Number.ZERO)));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
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
            TourDetail tourDetail = jsonb.fromJson(request.getReader(), TourDetail.class);

            /* Validation error - tourDetail id should be set to zero or
             * tourDetail id should not be included to in the request header.
             * */
            if (tourDetail.getId() != 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        ValidationMessages.INVALID_DATA_INPUT_ID_SHOULD_NOT_BE_INCLUDED);
                return;
            }

            /* validate user input. */
            String errors = validateUserInput(tourDetail);
            if (errors != null) {
                /* there are errors. */
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, errors);
                return;
            }

            /* get reference of the basic datasource from the servlet context. */
            BasicDataSource basicDataSource = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

            try {
                try (Connection connection = basicDataSource.getConnection()) {

                    PreparedStatement preparedStatement = connection
                            .prepareStatement(
                                    "INSERT INTO tour_detail " +
                                            "(no_of_days, no_of_people, no_of_children, star_category, arrival_date, departure_date, " +
                                            "status, exchange_rate, tour_agent, agent_profit, customer_id) " +
                                            "VALUES(?,?,?,?,?,?,?,?,?,?,?)",
                                    Statement.RETURN_GENERATED_KEYS);

                    /* set tourDetail related information with the insert query. */
                    preparedStatement.setInt(Number.ONE, tourDetail.getNoOfDays());
                    preparedStatement.setInt(Number.TWO, tourDetail.getNoOfPeople());
                    preparedStatement.setInt(Number.THREE, tourDetail.getNoOfChildren());
                    preparedStatement.setInt(Number.FOUR, tourDetail.getStarCategory());
                    preparedStatement.setDate(Number.FIVE, tourDetail.getArrivalDate());
                    preparedStatement.setDate(Number.SIX, tourDetail.getDepartureDate());
                    preparedStatement.setString(Number.SEVEN, tourDetail.getStatus().toString());
                    preparedStatement.setBigDecimal(Number.EIGHT, tourDetail.getExchangeRate());
                    preparedStatement.setString(Number.NINE, tourDetail.getTourAgent());
                    preparedStatement.setBigDecimal(Number.TEN, tourDetail.getAgentProfit());
                    preparedStatement.setInt(Number.ELEVEN, tourDetail.getCustomerId());

                    preparedStatement.executeUpdate();
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                    if (generatedKeys.next()) {
                        /* insertion succeed. */
                        int generatedId = generatedKeys.getInt(Number.ONE);
                        response.setStatus(HttpServletResponse.SC_CREATED);
                        out.println(jsonb.toJson(generatedId));
                        logger.info(MessageFormat.format(
                                SuccessfulMessages.CREATED_RECORD_SUCCESSFUL,
                                Commons.TOUR_DETAIL, generatedId));
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
            /* cannot parse the request header to an tourDetail object. */
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

    private String validateUserInput(TourDetail tourDetail) {
        String errors = Commons.EMPTY_STRING;

        if (isIntegerNotGreaterThanZero(tourDetail.getNoOfDays())) {
            String noOfDaysInvalidMessage = MessageFormat.format(
                    ValidationMessages.INVALID_DATA_INPUT_CUSTOMIZED,
                    EntityFields.NO_OF_DAYS);

            errors += noOfDaysInvalidMessage;
        }

        if (isIntegerNotGreaterThanZero(tourDetail.getNoOfPeople())) {
            String noOfPeopleInvalidMessage = MessageFormat.format(
                    ValidationMessages.INVALID_DATA_INPUT_CUSTOMIZED,
                    EntityFields.NO_OF_PEOPLE);

            errors += noOfPeopleInvalidMessage;
        }

        if (isIntegerNotGreaterThanZero(tourDetail.getNoOfChildren())) {
            String noOfChildrenInvalidMessage = MessageFormat.format(
                    ValidationMessages.INVALID_DATA_INPUT_CUSTOMIZED,
                    EntityFields.NO_OF_CHILDREN);

            errors += noOfChildrenInvalidMessage;
        }

        if (isStarCategoryNotValid(tourDetail.getStarCategory())) {
            String starCategoryInvalidMessage = MessageFormat.format(
                    ValidationMessages.INVALID_DATA_INPUT_CUSTOMIZED,
                    EntityFields.STAR_CATEGORY);

            errors += starCategoryInvalidMessage;
        }

        if (isArrivalDateAndDepartureDateNotValid(tourDetail.getNoOfDays(),
                tourDetail.getArrivalDate(),
                tourDetail.getDepartureDate())) {
            errors += ValidationMessages.DATES_NOT_ACCEPTABLE;
        }

        if (isIntegerNotGreaterThanZero(tourDetail.getCustomerId())) {
            String starCategoryInvalidMessage = MessageFormat.format(
                    ValidationMessages.INVALID_DATA_INPUT_CUSTOMIZED,
                    EntityFields.CUSTOMER_ID);

            errors += starCategoryInvalidMessage;
        } else {
            if (checkCustomerIsNotExists(tourDetail.getCustomerId())) {
                String customerNotExistsInvalidMessage = MessageFormat.format(
                        ValidationMessages.RECORD_NOT_FOUND_FOR_THE_ID,
                        Commons.CUSTOMER);

                errors += customerNotExistsInvalidMessage;
            }
        }

        return (errors.isEmpty()) ? null : errors;
    }


    /**
     * checks the string can be converted to an integer.
     *
     * @return true if able,
     * otherwise false.
     */
    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    /**
     * Checks whether the string contains an integer which is greater than zero.
     *
     * @param input the string to be checked.
     * @return true if contains,
     * otherwise false.
     */
    private boolean isIntegerGreaterThanZero(String input) {
        return input.matches("^[1-9]+$");
    }

    /**
     * Checks whether the string contains an integer which is NOT greater than zero.
     *
     * @param input the string to be checked.
     * @return true if contains,
     * otherwise false.
     */
    private boolean isIntegerNotGreaterThanZero(int input) {
        return !(input > 0);
    }

    /**
     * Checks the validity of the no of days, arrival date and departure date.
     * Logically, the difference between DepartureDate and ArrivalDate should at least a day.
     *
     * @param noOfDays      user given input as noOfDays.
     * @param arrivalDate   customer arrival date.
     * @param departureDate customer departure date.
     * @return true if not the difference between DepartureDate and ArrivalDate is at least a day and
     * the difference between DepartureDate and ArrivalDate is equal to @param noOfDays. otherwise false
     */
    private boolean isArrivalDateAndDepartureDateNotValid(int noOfDays, Date arrivalDate, Date departureDate) {
        long numberOfDaysL = noOfDays;
        long dateRange = DAYS.between(new java.sql.Date(arrivalDate.getTime()).toLocalDate(),
                new java.sql.Date(departureDate.getTime()).toLocalDate());

        return !(dateRange >= Number.ONE && (numberOfDaysL == dateRange) && arrivalDate.compareTo(departureDate) < 0);
    }

    /**
     * Checks the star category is between -1 and 7.
     *
     * @return true if it is between -1 and 7.
     * otherwise false.
     */
    private boolean isStarCategoryNotValid(int starCategory) {
        return !(starCategory >= Number.MINUS_ONE && starCategory <= Number.SEVEN);
    }


    /** Check for a customer by the given customer ID.
     * @return true if NO customer found, otherwise false.*/
    private boolean checkCustomerIsNotExists(int customerId) {
        boolean result = true;

        /* get reference of the basic datasource from the servlet context. */
        BasicDataSource basicDataSource = (BasicDataSource) getServletContext().getAttribute(Commons.CP);
        try {
            try (Connection connection = basicDataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT c.id FROM customer c WHERE c.id=?");

                preparedStatement.setInt(Number.ONE, customerId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    result = false;
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return result;
    }

    /**
     * Checks whether the given string matches with the enums.
     *
     * @return true if not matches,
     * otherwise false.
     */
    private boolean isTourStatusNotValid(String status) {
        try {
            TourDetailStatusTypes.valueOf(status);
            return false;
        } catch (IllegalArgumentException illegalArgumentException) {
            return true;
        }
    }
}
