/*
@author : Dhanusha Perera
@date : 03/05/2021
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            else
                response.getWriter().println(jsonb.toJson(customerBO.getCustomerByID(id)));

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
