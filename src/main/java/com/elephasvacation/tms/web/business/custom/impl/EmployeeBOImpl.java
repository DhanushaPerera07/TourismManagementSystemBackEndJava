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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
/*
 * @author : Dhanusha Perera
 * @date : 04/07/2021
 */
package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.EmployeeBO;
import com.elephasvacation.tms.web.business.custom.util.EmployeeDTOMapper;
import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.EmployeeDAO;
import com.elephasvacation.tms.web.dto.EmployeeDTO;

import javax.persistence.EntityManager;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {

    final EmployeeDTOMapper mapper = EmployeeDTOMapper.instance;
    private final EmployeeDAO employeeDAO = DAOFactory.getInstance().getDAO(DAOTypes.EMPLOYEE);
    private EntityManager entityManager;


    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;

        /* Set Entity Manager to the DAL. */
        this.employeeDAO.setEntityManager(this.entityManager);
    }

    @Override
    public Integer createEmployee(EmployeeDTO employeeDTO) throws Exception {
        return this.employeeDAO.save(mapper.getEmployee(employeeDTO));
    }

    @Override
    public void updateEmployee(EmployeeDTO employeeDTO) throws Exception {
        this.employeeDAO.update(mapper.getEmployee(employeeDTO));
    }

    @Override
    public void deleteEmployee(int employeeID) throws Exception {
        this.employeeDAO.delete(employeeID);
    }

    @Override
    public EmployeeDTO getEmployeeByID(int employeeID) throws Exception {
        return mapper.getEmployeeDTO(this.employeeDAO.get(employeeID));
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() throws Exception {
        return mapper.getEmployeeDTOs(this.employeeDAO.getAll());
    }
}
