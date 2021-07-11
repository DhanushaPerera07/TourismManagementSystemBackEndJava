package com.elephasvacation.tms.web.api.servlet;/*
@author : Dhanusha Perera
@date : 10/07/2021
*/

import com.elephasvacation.tms.web.api.APIFactory;
import com.elephasvacation.tms.web.api.APITypes;
import com.elephasvacation.tms.web.api.accommodation.AccommodationAPI;
import com.elephasvacation.tms.web.api.util.IDUtil;
import com.elephasvacation.tms.web.api.validation.CommonValidation;
import com.elephasvacation.tms.web.commonConstant.Number;
import com.elephasvacation.tms.web.commonConstant.*;
import com.elephasvacation.tms.web.dto.AccommodationDTO;
import com.elephasvacation.tms.web.dto.CreatedOutputDTO;
import com.elephasvacation.tms.web.exception.HttpResponseException;
import com.elephasvacation.tms.web.exception.ResponseExceptionUtil;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.MessageFormat;
import java.util.List;

@WebServlet(name = "AccommodationServlet", urlPatterns = {"/api/v1/accommodations/*"})
public class AccommodationServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(AccommodationServlet.class);

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        int accommodationID;
        AccommodationAPI accommodationAPI = APIFactory.getInstance().getAPI(APITypes.ACCOMMODATION);

        PrintWriter out = response.getWriter();
        response.setContentType(MimeTypes.Application.JSON);

        BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        /* get all accommodations. */
        if (request.getPathInfo() == null || request.getPathInfo()
                .replace("/", "")
                .trim()
                .isEmpty()) {
            try (Connection connection = bds.getConnection();) {
                accommodationAPI.setConnection(connection);
                List<AccommodationDTO> allAccommodations = accommodationAPI.getAllAccommodations();
                out.println(jsonb.toJson(allAccommodations));
            } catch (SQLException sqlException) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION, sqlException);
                sqlException.printStackTrace();
            } catch (Exception exception) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG, exception);
                exception.printStackTrace();
            }
        } // end-get-all-accommodations

        /* get Accommodation by AccommodationID. */
        else if (request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .replace("/", "")
                        .matches("^a\\d{3}$")) {

            String[] splitURIArray = IDUtil.getSplitArray(request.getPathInfo());

            /* extracting accommodation ID from URL. */
            accommodationID = IDUtil.extractIDFrom(splitURIArray,
                    Number.ONE,
                    "a",
                    "Invalid Accommodation ID");

            try (Connection connection = bds.getConnection();) {

                accommodationAPI.setConnection(connection);
                AccommodationDTO accommodationDTO = accommodationAPI.getAccommodationByID(accommodationID);
                if (accommodationDTO == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    out.println(jsonb.toJson(accommodationDTO));
                }
            } catch (SQLException sqlException) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION, sqlException);
                sqlException.printStackTrace();
            } catch (Exception exception) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG, exception);
                exception.printStackTrace();
            }


        } // end-get-by-ID-accommodation

        else {
            /* request.getPathInfo not matched with any if condition. */
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            IOException {
        AccommodationAPI accommodationAPI = APIFactory.getInstance().getAPI(APITypes.ACCOMMODATION);

        /* get the print-writer. */
        PrintWriter out = response.getWriter();

        /* check the request content type.
         * content type should be application/json. otherwise send bad request. */
        if (CommonValidation.isContentTypeNotJSON(request)) {
            CommonValidation.sendBadRequest(response, logger);
            return;
        }

        BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        /* CREATE an accommodation. */
        if (request.getPathInfo() == null || request.getPathInfo()
                .replace("/", "")
                .trim().isEmpty()) {

            /* read the request body. */
            AccommodationDTO accommodationDTO = null;
            try {
                accommodationDTO = jsonb.fromJson(request.getReader(), AccommodationDTO.class);
            } catch (JsonbException jsonbException) {
                /* cannot parse the request header to an Accommodation object. */
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
//                connection.setAutoCommit(false);
                accommodationAPI.setConnection(connection);
                Integer generatedAccommodationID = accommodationAPI.createAccommodation(accommodationDTO);
                if (generatedAccommodationID != null && generatedAccommodationID > 0) {
                    /* Accommodation created successfully. */
                    /* response-content-type. */
                    response.setContentType(MimeTypes.Application.JSON);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    out.println(jsonb.toJson(new CreatedOutputDTO(String.format("A%03d", generatedAccommodationID))));
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
//                connection.rollback();
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


        } // end-create-accommodation

        else {
            /* request.getPathInfo not matched with any if condition. */
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int accommodationID;

        AccommodationAPI accommodationAPI = APIFactory.getInstance().getAPI(APITypes.ACCOMMODATION);

        /* check the request content type.
         * content type should be application/json. otherwise send bad request. */
        if (CommonValidation.isContentTypeNotJSON(request)) {
            CommonValidation.sendBadRequest(response, logger);
            return;
        }

        /* get datasource. */
        BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        /* UPDATE an accommodation. */
        if (request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .replace("/", "")
                        .matches("^a\\d{3}$")) {

            String[] splitURIArray = IDUtil.getSplitArray(request.getPathInfo());

            /* extracting customer ID from URL. */
            accommodationID = IDUtil.extractIDFrom(splitURIArray,
                    Number.ONE,
                    "a",
                    "Invalid Accommodation ID");

            try (Connection connection = bds.getConnection();) {

                /* initialize the connection. */
                accommodationAPI.setConnection(connection);

                /* check matching record in DB. */
                if (accommodationAPI.getAccommodationByID(accommodationID) == null) {
                    /* no matching record found. */
                    Throwable t = new HttpResponseException(404,
                            MessageFormat.format(Commons.NO_MATCHING_RECORDS_FOUND,
                                    Commons.ACCOMMODATION),
                            null);
                    ResponseExceptionUtil.handle(t, response);
                    return;
                }

                /* read the request body. */
                AccommodationDTO accommodationDTO = jsonb.fromJson(request.getReader(), AccommodationDTO.class);
                accommodationDTO.setId(accommodationID);

                if (accommodationAPI.updateAccommodation(accommodationDTO)) {
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

        } // end-update-accommodation

        else {
            /* request.getPathInfo not matched with any if condition. */
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }

    } // end-doPut


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int accommodationID;
        AccommodationAPI accommodationAPI = APIFactory.getInstance().getAPI(APITypes.ACCOMMODATION);

        /* get datasource. */
        BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        /* DELETE an accommodation. */
        if (request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .replace("/", "")
                        .matches("^a\\d{3}$")) {


            String[] splitURIArray = IDUtil.getSplitArray(request.getPathInfo());

            /* extracting customer ID from URL. */
            accommodationID = IDUtil.extractIDFrom(splitURIArray,
                    Number.ONE,
                    "a",
                    "Invalid Accommodation ID");

            try (Connection connection = bds.getConnection();) {
                /* initialize the connection. */
                accommodationAPI.setConnection(connection);

                /* check matching record in DB. */
                if (accommodationAPI.getAccommodationByID(accommodationID) == null) {
                    /* no matching record found. */
                    Throwable t = new HttpResponseException(404,
                            MessageFormat.format(Commons.NO_MATCHING_RECORDS_FOUND,
                                    Commons.CUSTOMER),
                            null);
                    ResponseExceptionUtil.handle(t, response);
                    return;
                }


                if (accommodationAPI.deleteAccommodation(accommodationID)) {
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

        } // end-delete-accommodation

    } // end-doDelete

}
