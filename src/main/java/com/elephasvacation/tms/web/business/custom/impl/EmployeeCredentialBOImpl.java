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
package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.EmployeeCredentialBO;
import com.elephasvacation.tms.web.business.custom.util.mapper.EmployeeCredentialDTOMapper;
import com.elephasvacation.tms.web.dal.EmployeeCredentialDAO;
import com.elephasvacation.tms.web.dal.EmployeeDAO;
import com.elephasvacation.tms.web.dto.CredentialDataDTO;
import com.elephasvacation.tms.web.dto.EmployeeCredentialDTO;
import com.elephasvacation.tms.web.entity.EmployeeCredential;
import com.elephasvacation.tms.web.exception.BadRequestException;
import com.elephasvacation.tms.web.exception.RecordNotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@NoArgsConstructor
@Transactional
@Service
public class EmployeeCredentialBOImpl implements EmployeeCredentialBO {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private EmployeeCredentialDAO employeeCredentialDAO;

    @Autowired
    private EmployeeCredentialDTOMapper mapper;

    @Override
    public Integer createEmployeeCredential(EmployeeCredentialDTO employeeCredentialDTO) {

        /* Check for a matching employee. */
        if (!this.employeeDAO.existsById(employeeCredentialDTO.getEmployeeId()))
            throw new RecordNotFoundException("No matching Employee record found for ID: " +
                    employeeCredentialDTO.getEmployeeId());

        /* Check whether there is a record in employee credentials table. */
        if (this.employeeCredentialDAO.existsById(employeeCredentialDTO.getEmployeeId()))
            throw new BadRequestException("Record already exists");

        /* convert employeeCredentialDTO to entity. */
        EmployeeCredential employeeCredential = this.mapper.getEmployeeCredential(employeeCredentialDTO);

        /* save. */
        this.employeeCredentialDAO.save(employeeCredential);

        /* Employee Credential entity does not maintain a separate ID column,
        so no need to generate an ID for it. */
        return employeeCredentialDTO.getEmployeeId();
    }

/*    @Override
    public void updateEmployeeCredential(EmployeeCredentialDTO employeeCredentialDTO) {

        *//* convert DTO to entity. *//*
        EmployeeCredential employeeCredential = this.mapper.getEmployeeCredential(employeeCredentialDTO);

        *//* update. *//*
        this.employeeCredentialDAO.save(employeeCredential);
    }*/

    @Override
    public void updateEmployeeCredential(Integer employeeId, CredentialDataDTO credentials) {
        EmployeeCredential employeeCredential = this.employeeCredentialDAO.findById(employeeId).orElse(null);

        if (employeeCredential == null)
            throw new RecordNotFoundException("No matching Employee record found for ID: " + employeeId);

        /* TODO: password change logic. */
        if (!employeeCredential.getPassword().equals(credentials.getOldPassword())) {
            /* No update. */
            throw new BadRequestException("Given credentials are invalid.");
        } else {
            /* set the new password. */
            /* TODO: new password should be hashed. */
            employeeCredential.setPassword(credentials.getNewPassword());
            /* update. */
            this.employeeCredentialDAO.save(employeeCredential);
        }

    }

/*    @Override
    public void deleteEmployeeCredential(Integer employeeCredentialID) {
        this.employeeCredentialDAO.deleteById(employeeCredentialID);
    }*/

 /*   @Transactional(readOnly = true)
    @Override
    public EmployeeCredentialDTO getEmployeeCredentialByID(Integer employeeId) {
        EmployeeCredential employeeCredential = this.employeeCredentialDAO.findById(employeeId)
                .orElseThrow(() -> new RecordNotFoundException("No matching Employee found for ID: " + employeeId));

        *//* convert entity to DTO. *//*
        return this.mapper.getEmployeeCredentialDTO(employeeCredential);
    }*/

/*    @Transactional(readOnly = true)
    @Override
    public List<EmployeeCredentialDTO> getAllEmployeeCredentials(Integer employeeCredentialID) {

        *//* get all employee credentials. *//*
        List<EmployeeCredential> employeeCredentialList = this.employeeCredentialDAO.findAll();

        *//* convert employeeCredentialList to DTOList. *//*
        return this.mapper.getEmployeeCredentialDTOList(employeeCredentialList);
    }*/
}
