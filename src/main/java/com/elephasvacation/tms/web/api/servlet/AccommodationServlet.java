package com.elephasvacation.tms.web.api.servlet;/*
@author : Dhanusha Perera
@date : 10/07/2021
*/

import com.elephasvacation.tms.web.api.APIFactory;
import com.elephasvacation.tms.web.api.APITypes;
import com.elephasvacation.tms.web.api.accommodation.AccommodationAPI;
import com.elephasvacation.tms.web.api.accommodationPackage.AccommodationPackageAPI;
import com.elephasvacation.tms.web.api.util.IDUtil;
import com.elephasvacation.tms.web.api.validation.CommonValidation;
import com.elephasvacation.tms.web.commonConstant.Number;
import com.elephasvacation.tms.web.commonConstant.*;
import com.elephasvacation.tms.web.dto.AccommodationDTO;
import com.elephasvacation.tms.web.dto.AccommodationPackageDTO;
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
        int accommodationPackageID;
        AccommodationAPI accommodationAPI = APIFactory.getInstance().getAPI(APITypes.ACCOMMODATION);
        AccommodationPackageAPI accommodationPackageAPI = APIFactory.getInstance()
                .getAPI(APITypes.ACCOMMODATION_PACKAGE);

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

        /* get Accommodation Packages */
        else if (request.getPathInfo() != null &&
                request.getPathInfo().toLowerCase().matches("^/a\\d{3}/packages/|/a\\d{3}/packages$")) {

            String[] splitURIArray = IDUtil.getSplitArray(request.getPathInfo());

            /* extracting accommodation ID from URL. */
            accommodationID = IDUtil.extractIDFrom(splitURIArray,
                    Number.ONE,
                    "a",
                    "Invalid Accommodation ID");

            try (Connection connection = bds.getConnection();) {

                accommodationPackageAPI.setConnection(connection);
                List<AccommodationPackageDTO> accommodationPackageDTOList = accommodationPackageAPI
                        .getAllAccommodationPackagesByAccommodationID(accommodationID);
                out.println(jsonb.toJson(accommodationPackageDTOList));
            } catch (SQLException sqlException) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION, sqlException);
                sqlException.printStackTrace();
            } catch (Exception exception) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG, exception);
                exception.printStackTrace();
            }

        }

        /* get Accommodation Package by ID */
        else if (request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .matches("^/a\\d{3}/packages/ap\\d{3}/|/a\\d{3}/packages/ap\\d{3}$")) {

            String[] splitURIArray = IDUtil.getSplitArray(request.getPathInfo());

            /* extracting accommodation ID from URL. */
            accommodationID = IDUtil.extractIDFrom(splitURIArray,
                    Number.ONE,
                    "a",
                    "Invalid Accommodation ID");

            /* extracting Accommodation Package ID from URL. */
            accommodationPackageID = IDUtil.extractIDFrom(splitURIArray,
                    Number.THREE,
                    "ap",
                    "Invalid Accommodation Package ID");

            try (Connection connection = bds.getConnection();) {

                accommodationPackageAPI.setConnection(connection);
                AccommodationPackageDTO accommodationPackageDTO = accommodationPackageAPI
                        .getAccommodationPackageByID(accommodationPackageID);
                if (accommodationPackageDTO == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    out.println(jsonb.toJson(accommodationPackageDTO));
                }
            } catch (SQLException sqlException) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION, sqlException);
                sqlException.printStackTrace();
            } catch (Exception exception) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG, exception);
                exception.printStackTrace();
            }


        } else {
            /* request.getPathInfo not matched with any if condition. */
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            IOException {

        int accommodationID;
        AccommodationAPI accommodationAPI = APIFactory.getInstance().getAPI(APITypes.ACCOMMODATION);
        AccommodationPackageAPI accommodationPackageAPI = APIFactory.getInstance()
                .getAPI(APITypes.ACCOMMODATION_PACKAGE);

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

        /* CREATE an Accommodation Package */
        else if (request.getPathInfo() != null &&
                request.getPathInfo().toLowerCase().matches("^/a\\d{3}/packages/|/a\\d{3}/packages$")) {

            String[] splitURIArray = IDUtil.getSplitArray(request.getPathInfo());

            /* extracting accommodation ID from URL. */
            accommodationID = IDUtil.extractIDFrom(splitURIArray,
                    Number.ONE,
                    "a",
                    "Invalid Accommodation ID");

            /* read the request body. */
            AccommodationPackageDTO accommodationPackageDTO = null;
            try {
                accommodationPackageDTO = jsonb.fromJson(request.getReader(), AccommodationPackageDTO.class);
                accommodationPackageDTO.setAccommodationId(accommodationID);
            } catch (JsonbException jsonbException) {
                /* cannot parse the request header to an Accommodation Package object. */
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
                accommodationPackageAPI.setConnection(connection);
                Integer generatedAccommodationPackageID = accommodationPackageAPI.createAccommodationPackage(accommodationPackageDTO);
                if (generatedAccommodationPackageID != null && generatedAccommodationPackageID > 0) {
                    /* Accommodation Package created successfully. */
                    /* response-content-type. */
                    response.setContentType(MimeTypes.Application.JSON);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    out.println(jsonb.toJson(new CreatedOutputDTO(String.format("AP%03d", generatedAccommodationPackageID))));
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
                        MessageFormat.format(ValidationMessages.DUPLICATE_RECORD_FOUND,
                                Commons.ACCOMMODATION_PACKAGE));
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                logger.error(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION, sqlException);
            } catch (Exception exception) {
                exception.printStackTrace();
                logger.error(FailedMessages.SOMETHING_WENT_WRONG, exception);
            }


        } else {
            /* request.getPathInfo not matched with any if condition. */
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int accommodationID = Number.ZERO;
        int accommodationPackageID = Number.ZERO;
        AccommodationDTO accommodationDTO = null;
        AccommodationPackageDTO accommodationPackageDTO = null;

        boolean isUpdateAccommodation = request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .replace("/", "")
                        .matches("^a\\d{3}$");

        boolean isUpdateAccommodationPackage = request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .matches("^/a\\d{3}/packages/ap\\d{3}/|/a\\d{3}/packages/ap\\d{3}$");

        AccommodationAPI accommodationAPI = APIFactory.getInstance()
                .getAPI(APITypes.ACCOMMODATION);
        AccommodationPackageAPI accommodationPackageAPI = APIFactory.getInstance()
                .getAPI(APITypes.ACCOMMODATION_PACKAGE);

        /* check the request content type.
         * content type should be application/json. otherwise send bad request. */
        if (CommonValidation.isContentTypeNotJSON(request)) {
            CommonValidation.sendBadRequest(response, logger);
            return;
        }

        String[] splitURIArray = IDUtil.getSplitArray(request.getPathInfo());

        if (isUpdateAccommodation) {
            /* extracting Accommodation ID from URL. */
            accommodationID = IDUtil.extractIDFrom(splitURIArray,
                    Number.ONE,
                    "a",
                    "Invalid Accommodation ID");
        }

        if (isUpdateAccommodationPackage) {
            /* extracting Accommodation Package ID from URL. */
            accommodationPackageID = IDUtil.extractIDFrom(splitURIArray,
                    Number.THREE,
                    "ap",
                    "Invalid Accommodation Package ID");
        }

        /* get datasource. */
        BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        if (!(isUpdateAccommodation || isUpdateAccommodationPackage)) {
            /* request.getPathInfo not matched with any if condition. */
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        } else {

            /* UPDATE an accommodation. */
            try (Connection connection = bds.getConnection();) {

                /* initialize the connection. */
                accommodationAPI.setConnection(connection);
                accommodationPackageAPI.setConnection(connection);

                /* check matching record in DB. */
                if (isUpdateAccommodation && accommodationAPI.getAccommodationByID(accommodationID) == null) {
                    /* no matching record found. */
                    throw new HttpResponseException(HttpServletResponse.SC_BAD_REQUEST,
                            MessageFormat.format(Commons.NO_MATCHING_RECORDS_FOUND, Commons.ACCOMMODATION),
                            null);
                }

                /* check matching record in DB. */
                if (isUpdateAccommodationPackage &&
                        accommodationPackageAPI.getAccommodationPackageByID(accommodationPackageID) == null) {
                    /* no matching record found. */
                    throw new HttpResponseException(HttpServletResponse.SC_BAD_REQUEST,
                            MessageFormat.format(Commons.NO_MATCHING_RECORDS_FOUND, Commons.ACCOMMODATION_PACKAGE),
                            null);
                }

                /* read the request body. */
                if (isUpdateAccommodation) {
                    accommodationDTO = jsonb.fromJson(request.getReader(), AccommodationDTO.class);
                    accommodationDTO.setId(accommodationID);

                    if (accommodationAPI.updateAccommodation(accommodationDTO)) {
                        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    } else {
                        logger.error(FailedMessages.SOMETHING_WENT_WRONG);
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }
                }

                if (isUpdateAccommodationPackage) {
                    accommodationPackageDTO = jsonb.fromJson(request.getReader(), AccommodationPackageDTO.class);
                    accommodationPackageDTO.setAccommodationId(accommodationID);

                    if (accommodationPackageAPI.updateAccommodationPackage(accommodationPackageDTO)) {
                        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    } else {
                        logger.error(FailedMessages.SOMETHING_WENT_WRONG);
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }
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

        }

    } // end-doPut


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int accommodationID = Number.ZERO;
        int accommodationPackageID = Number.ZERO;
        AccommodationAPI accommodationAPI = APIFactory.getInstance()
                .getAPI(APITypes.ACCOMMODATION);
        AccommodationPackageAPI accommodationPackageAPI = APIFactory.getInstance()
                .getAPI(APITypes.ACCOMMODATION_PACKAGE);

        boolean isDeleteAccommodation = request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .replace("/", "")
                        .matches("^a\\d{3}$");

        boolean isDeleteAccommodationPackage = request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .matches("^/a\\d{3}/packages/ap\\d{3}/|/a\\d{3}/packages/ap\\d{3}$");

        String[] splitURIArray = IDUtil.getSplitArray(request.getPathInfo());

        if (isDeleteAccommodation) {
            /* extracting Accommodation ID from URL. */
            accommodationID = IDUtil.extractIDFrom(splitURIArray,
                    Number.ONE,
                    "a",
                    "Invalid Accommodation ID");
        }

        if (isDeleteAccommodationPackage) {
            /* extracting Accommodation Package ID from URL. */
            accommodationPackageID = IDUtil.extractIDFrom(splitURIArray,
                    Number.THREE,
                    "ap",
                    "Invalid Accommodation Package ID");
        }


        /* get datasource. */
        BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        if (!(isDeleteAccommodation || isDeleteAccommodationPackage)) {
            /* request.getPathInfo not matched with any if condition. */
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
        /* DELETE an accommodation. */
        else {

            try (Connection connection = bds.getConnection();) {
                /* initialize the connection. */
                accommodationAPI.setConnection(connection);
                accommodationPackageAPI.setConnection(connection);

                /* check matching record in DB. */
                if (!isDeleteAccommodation && accommodationAPI.getAccommodationByID(accommodationID) == null) {
                    /* no matching record found. */
                    throw new HttpResponseException(HttpServletResponse.SC_BAD_REQUEST,
                            MessageFormat.format(Commons.NO_MATCHING_RECORDS_FOUND,
                                    Commons.ACCOMMODATION),
                            null);
                } else {
                    if (isDeleteAccommodation) {
                        if (accommodationAPI.deleteAccommodation(accommodationID)) {
                            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                        } else {
                            logger.error(FailedMessages.SOMETHING_WENT_WRONG);
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                    }
                }

                /* check matching record in DB. */
                if (!isDeleteAccommodationPackage && accommodationPackageAPI
                        .getAccommodationPackageByID(accommodationPackageID) == null) {
                    /* no matching record found. */
                    throw new HttpResponseException(HttpServletResponse.SC_BAD_REQUEST,
                            MessageFormat.format(Commons.NO_MATCHING_RECORDS_FOUND,
                                    Commons.ACCOMMODATION_PACKAGE),
                            null);
                } else {
                    if (isDeleteAccommodationPackage) {
                        if (accommodationPackageAPI.deleteAccommodationPackage(accommodationPackageID)) {
                            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                        } else {
                            logger.error(FailedMessages.SOMETHING_WENT_WRONG);
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                    }
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
