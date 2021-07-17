package com.elephasvacation.tms.web.api.servlet;/*
@author : Dhanusha Perera
@date : 16/07/2021
*/

import com.elephasvacation.tms.web.api.APIFactory;
import com.elephasvacation.tms.web.api.APITypes;
import com.elephasvacation.tms.web.api.roomCategory.RoomCategoryAPI;
import com.elephasvacation.tms.web.api.util.IDUtil;
import com.elephasvacation.tms.web.commonConstant.Commons;
import com.elephasvacation.tms.web.commonConstant.FailedMessages;
import com.elephasvacation.tms.web.commonConstant.MimeTypes;
import com.elephasvacation.tms.web.commonConstant.Number;
import com.elephasvacation.tms.web.dto.RoomCategoryDTO;
import com.elephasvacation.tms.web.exception.ResponseExceptionUtil;
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
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "RoomCategoryServlet", urlPatterns = "/api/v1/room-categories/*")
public class RoomCategoryServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(RoomTypeServlet.class);

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
