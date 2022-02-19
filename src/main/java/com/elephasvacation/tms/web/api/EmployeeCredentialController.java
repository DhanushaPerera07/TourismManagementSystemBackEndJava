/*
 * MIT License
 *
 * Copyright (c) 2022 Dhanusha Perera
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

import com.elephasvacation.tms.web.api.util.ApiUtil;
import com.elephasvacation.tms.web.business.custom.EmployeeCredentialBO;
import com.elephasvacation.tms.web.commonconstant.API;
import com.elephasvacation.tms.web.dto.CredentialDataDTO;
import com.elephasvacation.tms.web.dto.EmployeeCredentialDTO;
import com.elephasvacation.tms.web.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = API.HTTP_LOCALHOST_8080)
@RequestMapping("/api/v1/employees")
@RestController
public class EmployeeCredentialController {

    @Autowired
    private EmployeeCredentialBO employeeCredentialBO;

//    @GetMapping(value = {"/{employeeId}/credentials"}, produces = MediaType.APPLICATION_JSON_VALUE)
//    public EmployeeCredentialDTO getEmployeeCredential(@PathVariable(name = "employeeId") String employeeIdStr) {
//        /* get the Employee Integer ID. */
//        Integer employeeId = ApiUtil.getIntegerId(employeeIdStr);
//
//        return this.employeeCredentialBO.getEmployeeCredentialByID(employeeId);
//    }

    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    @GetMapping(value = {"/{employeeId}/credentials"})
    public void getEmployeeCredential(@PathVariable(name = "employeeId") String employeeIdStr) {
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(value = "/{employeeId}/credentials")
    public void createEmployeeCredential(@PathVariable(name = "employeeId") String employeeIdStr,
                                         @RequestBody EmployeeCredentialDTO employeeCredentialDTO) {
        /* get the Employee Integer ID. */
        Integer employeeId = ApiUtil.getIntegerId(employeeIdStr);

        /* validation. */
        if (employeeCredentialDTO.getEmployeeId() != null)
            throw new BadRequestException("Invalid field \"EmployeeId\"");

        employeeCredentialDTO.setEmployeeId(employeeId);

        this.employeeCredentialBO.createEmployeeCredential(employeeCredentialDTO);
    }


    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping(value = {"/{employeeId}/credentials"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void changePassword(@PathVariable(name = "employeeId") String employeeIdStr,
                               @RequestBody CredentialDataDTO credentials) {
        /* get the Employee Integer ID. */
        Integer employeeId = ApiUtil.getIntegerId(employeeIdStr);

        this.employeeCredentialBO.updateEmployeeCredential(employeeId, credentials);
    }

}
