/*
@author : Dhanusha Perera
@since : 21/04/2021
*/
package com.elephasvacation.tms.web.api;

import com.elephasvacation.tms.web.commonConstant.Commons;
import com.elephasvacation.tms.web.commonConstant.FailedMessages;
import com.elephasvacation.tms.web.commonConstant.MimeTypes;
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

@WebServlet(name = "EmployeeServlet", urlPatterns = "/v1/api/employees")
public class EmployeeServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServlet.class);

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(MimeTypes.Text.HTML);
        PrintWriter out = response.getWriter();

        BasicDataSource basicDataSource = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

/*        try {
            try (Connection connection = basicDataSource.getConnection()){
                *//* get all customers in the database. *//*

            }
        } catch (SQLException throwables) {
            logger.error(FailedMessages.FAILED_OPENING_CONNECTION, throwables);
            throwables.printStackTrace();
        }*/


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
