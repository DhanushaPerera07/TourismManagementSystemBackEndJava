package com.elephasvacation.tms.web.api;
/*
@author : Dhanusha Perera
@date : 18/07/2021
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

@WebServlet(name = "MealPlanServlet", urlPatterns = "/api/v1/meal-plans/*")
public class MealPlanServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(MealPlanServlet.class);

    Jsonb jsonb = JsonbBuilder.create();


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            super.service(request, response);
        } catch (Throwable t) {
            ResponseExceptionUtil.handle(t, response);
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
    }
}
