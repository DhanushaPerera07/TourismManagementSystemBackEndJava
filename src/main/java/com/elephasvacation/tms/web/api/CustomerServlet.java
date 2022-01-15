/*
 * MIT License
 *
 * Copyright (c) 2021 Dhanusha Perera
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.elephasvacation.tms.web.api;

import com.elephasvacation.tms.web.AppInitializer;
import com.elephasvacation.tms.web.business.custom.CustomerBO;
import com.elephasvacation.tms.web.dto.CustomerDTO;
import com.elephasvacation.tms.web.exception.HttpResponseException;
import com.elephasvacation.tms.web.exception.ResponseExceptionUtil;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String stringId = null;
        Integer id = null;

        if (request.getPathInfo() != null)
            stringId = request.getPathInfo().replace("/", "");

        if (stringId != null && stringId.length() > 0) {
            try {
                id = new Integer(stringId);
            } catch (NumberFormatException numberFormatException) {
                throw new HttpResponseException(400, "ID is not valid.", numberFormatException);
            }

            if (id <= 0) {
                throw new HttpResponseException(400, "ID is not valid.", null);
            }
        }

        try {
            response.setContentType("application/json");
            CustomerBO customerBO = AppInitializer.getContext().getBean(CustomerBO.class);

            if (id == null)
                response.getWriter().println(jsonb.toJson(customerBO.getAllCustomers()));
            else {
                CustomerDTO customerByID = customerBO.getCustomerByID(id);
                if (customerByID == null) {
                    /* No customer found for the given ID.
                     * Set HTTP Status code - 404 NOT FOUND. */
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    response.getWriter().println(jsonb.toJson(customerByID));
                }
            }

        } catch (Throwable t) {
            ResponseExceptionUtil.handle(t, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        Jsonb jsonb = JsonbBuilder.create();

        try {
            CustomerDTO dto = jsonb.fromJson(request.getReader(), CustomerDTO.class);

            /* Simple data validation. */
            if (dto.getId() != 0) {
                throw new HttpResponseException(400, "Invalid customer details", null);
            }

            CustomerBO customerBO = AppInitializer.getContext().getBean(CustomerBO.class);
            customerBO.createCustomer(dto);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/json");
            response.getWriter().println(jsonb.toJson(dto));
        } catch (SQLIntegrityConstraintViolationException exp) {
            throw new HttpResponseException(400, "Duplicate entry", exp);
        } catch (JsonbException exp) {
            throw new HttpResponseException(400, "Failed to read the JSON", exp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        try {

            if (request.getPathInfo() == null || request.getPathInfo().replace("/", "").trim().isEmpty()) {
                throw new HttpResponseException(400, "Invalid customer id", null);
            }

            String stringId = request.getPathInfo().replace("/", "");

            Integer id = null;
            try {
                id = new Integer(stringId);
            } catch (NumberFormatException numberFormatException) {
                throw new HttpResponseException(400, "ID is not valid.", numberFormatException);
            }

            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO dto = jsonb.fromJson(request.getReader(), CustomerDTO.class);

            /* Simple data validation. */
            if (dto.getId() > 0) {
                throw new HttpResponseException(400, "Invalid details", null);
            }

            CustomerBO customerBO = AppInitializer.getContext().getBean(CustomerBO.class);
            dto.setId(id);
            customerBO.updateCustomer(dto);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);

        } catch (JsonbException exp) {
            throw new HttpResponseException(400, "Failed to read the JSON", exp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {

            if (request.getPathInfo() == null ||
                    request.getPathInfo().replace("/", "").trim().isEmpty()) {
                throw new HttpResponseException(400, "Invalid customer id", null);
            }

            String stringId = request.getPathInfo().replace("/", "");

            Integer id = null;
            try {
                id = new Integer(stringId);
            } catch (NumberFormatException numberFormatException) {
                throw new HttpResponseException(400, "ID is not valid.", numberFormatException);
            }

            CustomerBO customerBO = AppInitializer.getContext().getBean(CustomerBO.class);
            customerBO.deleteCustomer(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
