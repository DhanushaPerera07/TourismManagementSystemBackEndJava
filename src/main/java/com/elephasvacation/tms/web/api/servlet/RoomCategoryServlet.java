package com.elephasvacation.tms.web.api.servlet;

/*
@author : Dhanusha Perera
@date : 16/07/2021
*/

import com.elephasvacation.tms.web.api.APIFactory;
import com.elephasvacation.tms.web.api.APITypes;
import com.elephasvacation.tms.web.api.roomCategory.RoomCategoryAPI;
import com.elephasvacation.tms.web.api.util.IDUtil;
import com.elephasvacation.tms.web.api.validation.CommonValidation;
import com.elephasvacation.tms.web.commonConstant.Number;
import com.elephasvacation.tms.web.commonConstant.*;
import com.elephasvacation.tms.web.dto.CreatedOutputDTO;
import com.elephasvacation.tms.web.dto.RoomCategoryDTO;
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

@WebServlet(name = "RoomCategoryServlet", urlPatterns = "/api/v1/room-categories/*")
public class RoomCategoryServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(RoomCategoryServlet.class);

    Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            super.service(request, response);
        } catch (Throwable t) {
            ResponseExceptionUtil.handle(t, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int roomCategoryID;
        RoomCategoryAPI roomCategoryAPI = APIFactory.getInstance().getAPI(APITypes.ROOM_CATEGORY);
        PrintWriter out = response.getWriter();

        /* set content type. */
        response.setContentType(MimeTypes.Application.JSON);

        BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        /* get all roomCategories. */
        if (request.getPathInfo() == null || request.getPathInfo()
                .replace("/", "")
                .trim()
                .isEmpty()) {
            try (Connection connection = bds.getConnection();) {
                roomCategoryAPI.setConnection(connection);
                List<RoomCategoryDTO> allRoomCategories = roomCategoryAPI.getAllRoomCategories();
                out.println(jsonb.toJson(allRoomCategories));
            } catch (SQLException sqlException) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION, sqlException);
                sqlException.printStackTrace();
                throw new RuntimeException(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION);
            } catch (Exception exception) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG, exception);
                exception.printStackTrace();
                throw new RuntimeException(FailedMessages.SOMETHING_WENT_WRONG);
            }
        }

        /* get roomCategory by RoomCategoryID. */
        else if (request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .replace("/", "")
                        .matches("^[A-Za-z]{2}\\d{3}$")) {
            String[] splitURIArray = IDUtil.getSplitArray(request.getPathInfo());

            /* extracting RoomCategoryID from URL. */
            roomCategoryID = IDUtil.extractIDFrom(splitURIArray,
                    Number.ONE,
                    "rc",
                    "Invalid RoomCategoryID");

            try (Connection connection = bds.getConnection();) {

                roomCategoryAPI.setConnection(connection);
                RoomCategoryDTO roomCategoryDTO = roomCategoryAPI.getRoomCategoryByID(roomCategoryID);
                if (roomCategoryDTO == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    out.println(jsonb.toJson(roomCategoryDTO));
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RoomCategoryAPI roomCategoryAPI = APIFactory.getInstance().getAPI(APITypes.ROOM_CATEGORY);

        /* get the print-writer. */
        PrintWriter out = response.getWriter();

        /* check the request content type.
         * content type should be application/json. otherwise send bad request. */
        if (CommonValidation.isContentTypeNotJSON(request)) {
            CommonValidation.sendBadRequest(response, logger);
            return;
        }

        BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        /* CREATE an RoomCategory. */
        if (request.getPathInfo() == null || request.getPathInfo()
                .replace("/", "")
                .trim().isEmpty()) {

            /* read the request body. */
            RoomCategoryDTO roomCategoryDTO = null;
            try {
                roomCategoryDTO = jsonb.fromJson(request.getReader(), RoomCategoryDTO.class);
            } catch (JsonbException jsonbException) {
                /* cannot parse the request header to an RoomCategory object. */
                jsonbException.printStackTrace();
                logger.error(ValidationMessages.INVALID_DATA_INPUT, jsonbException);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            } catch (IOException ioException) {
                /* inputStream error when reading the request object. */
                ioException.printStackTrace();
                logger.error(FailedMessages.SOMETHING_WENT_WRONG_READING_REQUEST_BODY, ioException);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }

            try (Connection connection = bds.getConnection();) {
                roomCategoryAPI.setConnection(connection);
                Integer generatedRoomCategoryID = roomCategoryAPI.createRoomCategoryDTO(roomCategoryDTO);
                if (generatedRoomCategoryID != null && generatedRoomCategoryID > 0) {
                    /* RoomCategory created successfully. */
                    /* response-content-type. */
                    response.setContentType(MimeTypes.Application.JSON);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    out.println(jsonb.toJson(new CreatedOutputDTO(String.format("RC%03d", generatedRoomCategoryID))));
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

        } else {
            /* request.getPathInfo not matched with any if condition. */
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int roomCategoryID;
        RoomCategoryAPI roomCategoryAPI = APIFactory.getInstance().getAPI(APITypes.ROOM_CATEGORY);

        /* check the request content type.
         * content type should be application/json. otherwise send bad request. */
        if (CommonValidation.isContentTypeNotJSON(request)) {
            CommonValidation.sendBadRequest(response, logger);
            return;
        }

        /* get datasource. */
        BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        /* UPDATE a roomCategory. */
        if (request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .replace("/", "")
                        .matches("^[A-Za-z]{2}\\d{3}$")) {

            String[] splitURIArray = IDUtil.getSplitArray(request.getPathInfo());

            /* extracting RoomCategoryID from URL. */
            roomCategoryID = IDUtil.extractIDFrom(splitURIArray,
                    Number.ONE,
                    "rc",
                    "Invalid RoomCategoryID");

            try (Connection connection = bds.getConnection();) {

                /* initialize the connection. */
                roomCategoryAPI.setConnection(connection);

                /* check matching record in DB. */
                if (roomCategoryAPI.getRoomCategoryByID(roomCategoryID) == null) {
                    /* no matching record found. */
                    Throwable t = new HttpResponseException(404,
                            MessageFormat.format(Commons.NO_MATCHING_RECORDS_FOUND,
                                    Commons.ROOM_CATEGORY),
                            null);
                    ResponseExceptionUtil.handle(t, response);
                    return;
                }

                /* read the request body. */
                RoomCategoryDTO roomCategoryDTO = jsonb.fromJson(request.getReader(), RoomCategoryDTO.class);
                roomCategoryDTO.setId(roomCategoryID);

                if (roomCategoryAPI.updateRoomCategoryDTO(roomCategoryDTO)) {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    logger.error(FailedMessages.SOMETHING_WENT_WRONG);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }

            } catch (JsonbException jsonbException) {
                /* cannot parse the request header to an RoomCategory object. */
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
    }
}
