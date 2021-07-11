/*
@author : Dhanusha Perera
@since : 21/04/2021
*/
package com.elephasvacation.tms.web.api.servlet;

import com.elephasvacation.tms.web.api.APIFactory;
import com.elephasvacation.tms.web.api.APITypes;
import com.elephasvacation.tms.web.api.employee.EmployeeAPI;
import com.elephasvacation.tms.web.api.util.IDUtil;
import com.elephasvacation.tms.web.api.validation.CommonValidation;
import com.elephasvacation.tms.web.commonConstant.Number;
import com.elephasvacation.tms.web.commonConstant.*;
import com.elephasvacation.tms.web.dto.CreatedOutputDTO;
import com.elephasvacation.tms.web.dto.EmployeeDTO;
import com.elephasvacation.tms.web.entity.Employee;
import com.elephasvacation.tms.web.exception.HttpResponseException;
import com.elephasvacation.tms.web.exception.ResponseExceptionUtil;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

@WebServlet(name = "EmployeeServlet", urlPatterns = "/api/v1/employees/*")
public class EmployeeServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServlet.class);

    Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int employeeID;
        EmployeeAPI employeeAPI = APIFactory.getInstance().getAPI(APITypes.EMPLOYEE);
        PrintWriter out = response.getWriter();

        /* set content type. */
        response.setContentType(MimeTypes.Application.JSON);

        BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        /* get all employees. */
        if (request.getPathInfo() == null || request.getPathInfo()
                .replace("/", "")
                .trim()
                .isEmpty()) {
            try (Connection connection = bds.getConnection();) {
                employeeAPI.setConnection(connection);
                List<EmployeeDTO> allEmployees = employeeAPI.getAllEmployees();
                out.println(jsonb.toJson(allEmployees));
            } catch (SQLException sqlException) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG_DATABASE_OPERATION, sqlException);
                sqlException.printStackTrace();
            } catch (Exception exception) {
                logger.error(FailedMessages.SOMETHING_WENT_WRONG, exception);
                exception.printStackTrace();
            }
        }

        /* get employee by employeeID. */
        else if (request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .replace("/", "")
                        .matches("^e\\d{3}$")) {
            String[] splitURIArray = IDUtil.getSplitArray(request.getPathInfo());

            /* extracting Employee ID from URL. */
            employeeID = IDUtil.extractIDFrom(splitURIArray,
                    Number.ONE,
                    "e",
                    "Invalid Employee ID");

            try (Connection connection = bds.getConnection();) {

                employeeAPI.setConnection(connection);
                EmployeeDTO employeeDTO = employeeAPI.getEmployeeByID(employeeID);
                if (employeeDTO == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    out.println(jsonb.toJson(employeeDTO));
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        EmployeeAPI employeeAPI = APIFactory.getInstance().getAPI(APITypes.EMPLOYEE);

        /* get the print-writer. */
        PrintWriter out = response.getWriter();

        /* check the request content type.
         * content type should be application/json. otherwise send bad request. */
        if (CommonValidation.isContentTypeNotJSON(request)) {
            CommonValidation.sendBadRequest(response, logger);
            return;
        }

        BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        /* CREATE an Employee. */
        if (request.getPathInfo() == null || request.getPathInfo()
                .replace("/", "")
                .trim().isEmpty()) {

            /* read the request body. */
            EmployeeDTO employeeDTO = null;
            try {
                employeeDTO = jsonb.fromJson(request.getReader(), EmployeeDTO.class);
            } catch (JsonbException jsonbException) {
                /* cannot parse the request header to an Employee object. */
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
                employeeAPI.setConnection(connection);
                Integer generatedEmployeeID = employeeAPI.createEmployee(employeeDTO);
                if (generatedEmployeeID != null && generatedEmployeeID > 0) {
                    /* Employee created successfully. */
                    /* response-content-type. */
                    response.setContentType(MimeTypes.Application.JSON);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    out.println(jsonb.toJson(new CreatedOutputDTO(String.format("E%03d", generatedEmployeeID))));
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
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int employeeID;
        EmployeeAPI employeeAPI = APIFactory.getInstance().getAPI(APITypes.EMPLOYEE);

        /* check the request content type.
         * content type should be application/json. otherwise send bad request. */
        if (CommonValidation.isContentTypeNotJSON(request)) {
            CommonValidation.sendBadRequest(response, logger);
            return;
        }

        /* get datasource. */
        BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        /* UPDATE an Employee. */
        if (request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .replace("/", "")
                        .matches("^e\\d{3}$")) {

            String[] splitURIArray = IDUtil.getSplitArray(request.getPathInfo());

            /* extracting employee ID from URL. */
            employeeID = IDUtil.extractIDFrom(splitURIArray,
                    Number.ONE,
                    "e",
                    "Invalid Employee ID");

            try (Connection connection = bds.getConnection();) {

                /* initialize the connection. */
                employeeAPI.setConnection(connection);

                /* check matching record in DB. */
                if (employeeAPI.getEmployeeByID(employeeID) == null) {
                    /* no matching record found. */
                    Throwable t = new HttpResponseException(404,
                            MessageFormat.format(Commons.NO_MATCHING_RECORDS_FOUND,
                                    Commons.EMPLOYEE),
                            null);
                    ResponseExceptionUtil.handle(t, response);
                    return;
                }

                /* read the request body. */
                EmployeeDTO employeeDTO = jsonb.fromJson(request.getReader(), EmployeeDTO.class);
                employeeDTO.setId(employeeID);

                if (employeeAPI.updateEmployee(employeeDTO)) {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    logger.error(FailedMessages.SOMETHING_WENT_WRONG);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }

            } catch (JsonbException jsonbException) {
                /* cannot parse the request header to an employee object. */
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


        } // end-update-employee

        else {
            /* request.getPathInfo not matched with any if condition. */
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int employeeID;
        EmployeeAPI employeeAPI = APIFactory.getInstance().getAPI(APITypes.EMPLOYEE);

        /* get datasource. */
        BasicDataSource bds = (BasicDataSource) getServletContext().getAttribute(Commons.CP);

        /* DELETE an Employee. */
        if (request.getPathInfo() != null &&
                request.getPathInfo()
                        .toLowerCase()
                        .replace("/", "")
                        .matches("^e\\d{3}$")) {

            String[] splitURIArray = IDUtil.getSplitArray(request.getPathInfo());

            /* extracting employee ID from URL. */
            employeeID = IDUtil.extractIDFrom(splitURIArray,
                    Number.ONE,
                    "e",
                    "Invalid Employee ID");

            try (Connection connection = bds.getConnection();) {

                /* initialize the connection. */
                employeeAPI.setConnection(connection);

                /* check matching record in DB. */
                if (employeeAPI.getEmployeeByID(employeeID) == null) {
                    /* no matching record found. */
                    Throwable t = new HttpResponseException(404,
                            MessageFormat.format(Commons.NO_MATCHING_RECORDS_FOUND,
                                    Commons.EMPLOYEE),
                            null);
                    ResponseExceptionUtil.handle(t, response);
                    return;
                }

                /* delete record by ID. */
                if (employeeAPI.deleteEmployee(employeeID)) {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    logger.error(FailedMessages.SOMETHING_WENT_WRONG);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }

            } catch (JsonbException jsonbException) {
                /* cannot parse the request header to an employee object. */
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


        } // end-delete-employee

        else {
            /* request.getPathInfo not matched with any if condition. */
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }


    /**
     * Check the id is valid.
     *
     * @returns true if not valid.
     * otherwise, false.
     */
    private boolean isIdNotValid(String id) {
        return !id.matches("^[1-9]$|^[1-9]\\d+$");
    }


    /**
     * check the user input are valid.
     *
     * @returns true if valid,
     * otherwise false.
     */
    private String validateUserInput(Employee employee) {

        String errors = Commons.EMPTY_STRING;

        if (!isEmailValid(employee.getEmail())) {
            String emailInvalidMessage = MessageFormat.format(
                    ValidationMessages.INVALID_DATA_INPUT_CUSTOMIZED,
                    EntityFields.EMAIL);

            errors += emailInvalidMessage;
        }

        if (!isNICValid(employee.getNic())) {
            String nicInvalidMessage = MessageFormat.format(
                    ValidationMessages.INVALID_DATA_INPUT_CUSTOMIZED,
                    EntityFields.NIC);

            errors += nicInvalidMessage;
        }

        if (!isContactNumberValid(employee.getContact())) {
            String contactNoInvalidMessage = MessageFormat.format(
                    ValidationMessages.INVALID_DATA_INPUT_CUSTOMIZED,
                    EntityFields.CONTACT_NO);

            errors += contactNoInvalidMessage;
        }

        return (errors.isEmpty()) ? null : errors;
    }


    /**
     * Check whether the email is in valid format.
     *
     * @returns true if email is valid.
     * otherwise false.
     */
    private boolean isEmailValid(String email) {
        return EmailValidator
                .getInstance().isValid(email);
    }

    /**
     * Check whether the nic is in valid format.
     *
     * @returns true if valid.
     * otherwise false.
     */
    private boolean isNICValid(String nic) {
        return nic.matches("^\\d{12}$|^\\d{9}[V|v]$");
    }

    /**
     * Check whether the contact number is in valid format.
     *
     * @returns true if valid.
     * otherwise false.
     */
    private boolean isContactNumberValid(String contactNo) {
        return contactNo.matches("^\\d{10}$|^[+]94\\d{9}$");
    }
}
