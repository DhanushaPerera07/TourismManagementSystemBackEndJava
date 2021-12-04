package com.elephasvacation.tms.web.api;/*
@author : Dhanusha Perera
@date : 10/07/2021
*/

import com.elephasvacation.tms.web.exception.ResponseExceptionUtil;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {

    } // end-doPut


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {

    } // end-doDelete

}
