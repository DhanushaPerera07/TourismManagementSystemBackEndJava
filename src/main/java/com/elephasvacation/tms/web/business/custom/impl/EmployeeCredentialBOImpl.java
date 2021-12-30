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
import com.elephasvacation.tms.web.business.custom.util.transaction.TMSTransaction;
import com.elephasvacation.tms.web.dal.custom.EmployeeCredentialDAO;
import com.elephasvacation.tms.web.dto.EmployeeCredentialDTO;
import com.elephasvacation.tms.web.entity.EmployeeCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class EmployeeCredentialBOImpl implements EmployeeCredentialBO {

    @Autowired
    private EmployeeCredentialDAO employeeCredentialDAO;

    @Autowired
    private EmployeeCredentialDTOMapper mapper;

    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;

        /* Set Entity Manager to the DAL. */
        this.employeeCredentialDAO.setEntityManager(this.entityManager);
    }

    @TMSTransaction
    @Override
    public Integer createEmployeeCredential(EmployeeCredentialDTO employeeCredentialDTO) throws Exception {

        /* convert employeeCredentialDTO to entity. */
        EmployeeCredential employeeCredential = this.mapper.getEmployeeCredential(employeeCredentialDTO);

        /* save. */
        Integer generatedEmployeeCredentialID = this.employeeCredentialDAO.save(employeeCredential).getId();

        return generatedEmployeeCredentialID;
    }

    @TMSTransaction
    @Override
    public void updateEmployeeCredential(EmployeeCredentialDTO employeeCredentialDTO) throws Exception {

        /* convert DTO to entity. */
        EmployeeCredential employeeCredential = this.mapper.getEmployeeCredential(employeeCredentialDTO);

        /* update. */
        this.employeeCredentialDAO.update(employeeCredential);
    }

    @TMSTransaction
    @Override
    public void deleteEmployeeCredential(Integer employeeCredentialID) throws Exception {
        this.employeeCredentialDAO.delete(employeeCredentialID);
    }

    @TMSTransaction
    @Override
    public EmployeeCredentialDTO getEmployeeCredentialByID(Integer employeeCredentialID) throws Exception {

        /* get EmployeeCredential By ID.  */
        EmployeeCredential employeeCredential = this.employeeCredentialDAO.get(employeeCredentialID);

        /* convert entity to DTO. */
        EmployeeCredentialDTO employeeCredentialDTO = this.mapper.getEmployeeCredentialDTO(employeeCredential);

        return employeeCredentialDTO;
    }

    @TMSTransaction
    @Override
    public List<EmployeeCredentialDTO> getAllEmployeeCredentials(Integer employeeCredentialID) throws Exception {

        /* get all employee credentials. */
        List<EmployeeCredential> employeeCredentialList = this.employeeCredentialDAO.getAll();

        /* convert employeeCredentialList to DTOList. */
        List<EmployeeCredentialDTO> employeeCredentialDTOList = this.mapper.
                getEmployeeCredentialDTOList(employeeCredentialList);

        return employeeCredentialDTOList;
    }
}
