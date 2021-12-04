/*
@author : Dhanusha Perera
@since : 21/04/2021
*/
package com.elephasvacation.tms.web.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "EmployeeServlet", urlPatterns = "/api/v1/employees/*")
public class EmployeeServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServlet.class);

    Jsonb jsonb = JsonbBuilder.create();

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
