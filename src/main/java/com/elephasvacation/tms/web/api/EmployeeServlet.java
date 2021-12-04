/*
@author : Dhanusha Perera
@since : 21/04/2021
*/
package com.elephasvacation.tms.web.api;

import com.elephasvacation.tms.web.commonConstant.Commons;
import com.elephasvacation.tms.web.commonConstant.EntityFields;
import com.elephasvacation.tms.web.commonConstant.ValidationMessages;
import com.elephasvacation.tms.web.entity.Employee;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;

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
     * @return true if email is valid.
     * otherwise, false.
     */
    private boolean isEmailValid(String email) {
        return EmailValidator
                .getInstance().isValid(email);
    }

    /**
     * Check whether the nic is in valid format.
     *
     * @return true if valid.
     * otherwise, false.
     */
    private boolean isNICValid(String nic) {
        return nic.matches("^\\d{12}$|^\\d{9}[V|v]$");
    }

    /**
     * Check whether the contact number is in valid format.
     *
     * @return true if valid.
     * otherwise, false.
     */
    private boolean isContactNumberValid(String contactNo) {
        return contactNo.matches("^\\d{10}$|^[+]94\\d{9}$");
    }
}
