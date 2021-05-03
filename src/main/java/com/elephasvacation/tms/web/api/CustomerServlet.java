/*
@author : Dhanusha Perera
@date : 03/05/2021
*/
package com.elephasvacation.tms.web.api;

import com.elephasvacation.tms.web.commonConstant.Number;
import com.elephasvacation.tms.web.commonConstant.*;
import com.elephasvacation.tms.web.entity.Customer;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CustomerServlet", urlPatterns = "/api/v1/customers")
public class CustomerServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServlet.class);

    Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /* get id param from the query string. */
        String id = request.getParameter(Commons.ID);

        /* validation part -
        if id is not empty that means there is an id and it should be an integer greater than zero. */
        if (id != null && !(id.matches("^[1-9]+$"))) {
            /* send the client that it is a bad request. */
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            /* Error: id should be an integer. */
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    MessageFormat.format(ValidationMessages.INVALID_ID,
                            Commons.CUSTOMER));
            return;
        }

        /* set respond type. */
        response.setContentType(MimeTypes.Application.JSON);
        /* get the print-writer. */
        PrintWriter out = response.getWriter();

        /* get reference of the basic datasource from the servlet context. */
        BasicDataSource basicDataSource = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        /* holds the record(s). */
        List<Customer> customerList = new ArrayList<>();
        Customer customer;
        try {
            try (Connection connection = basicDataSource.getConnection()) {
                // get all customers in the database.
                PreparedStatement preparedStatement =
                        connection.prepareStatement("SELECT * FROM customer e" +
                                ((id != null) ? " WHERE e.id=?" : ";"));

                if (id != null) {
                    preparedStatement.setInt(Number.ONE, Integer.parseInt(id));
                }

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    /* creates a new customer object. */
                    customer = new Customer();

                    /* set customer details */
                    customer.setId(resultSet.getInt(Number.ONE));
                    customer.setName(resultSet.getString(Number.TWO));
                    customer.setNationality(resultSet.getString(Number.THREE));
                    customer.setPassportNo(resultSet.getString(Number.FOUR));
                    customer.setEmail(resultSet.getString(Number.FIVE));
                    customer.setCountryCallingCode(resultSet.getString(Number.SIX));
                    customer.setCountry(resultSet.getString(Number.SEVEN));
                    customer.setDescription(resultSet.getString(Number.EIGHT));
                    customer.setAdditionalNotes(resultSet.getString(Number.NINE));
                    customer.setAddedDate(resultSet.getDate(Number.TEN));
                    customer.setLastUpdated(resultSet.getDate(Number.ELEVEN));

                    /* add customer to the arraylist. */
                    customerList.add(customer);
                } // end of the while

            }

            if (id != null && customerList.isEmpty()) {
                /* no records found. */
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                logger.info(MessageFormat.format(
                        Commons.NO_RECORDS_FOUND,
                        Commons.CUSTOMER));
            } else if (id != null && customerList.size() == Number.ONE) {
                /* if found a record for the given ID, return the result. */
                out.print(jsonb.toJson(customerList.get(Number.ZERO)));
                logger.info(MessageFormat.format(
                        SuccessfulMessages.SEND_LIST_SUCCESSFULLY,
                        Commons.CUSTOMER,
                        customerList.size()), customerList.get(Number.ZERO));
            } else {
                /* write to the print-writer. */
                out.println(jsonb.toJson(customerList));
                logger.info(MessageFormat.format(
                        SuccessfulMessages.SEND_LIST_SUCCESSFULLY,
                        Commons.CUSTOMER,
                        customerList.size()), customerList);
            }
        } catch (SQLException sqlException) {
            logger.error(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION, sqlException);
            sqlException.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
