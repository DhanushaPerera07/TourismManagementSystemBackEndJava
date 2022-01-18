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
/*
 * @author : Dhanusha Perera
 * @date : 04/07/2021
 */
package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.EmployeeBO;
import com.elephasvacation.tms.web.business.custom.util.mapper.EmployeeDTOMapper;
import com.elephasvacation.tms.web.dal.custom.EmployeeDAO;
import com.elephasvacation.tms.web.dto.EmployeeDTO;
import com.elephasvacation.tms.web.entity.Employee;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NoArgsConstructor
@Transactional
@Service
public class EmployeeBOImpl implements EmployeeBO {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private EmployeeDTOMapper mapper;

    @Override
    public Integer createEmployee(EmployeeDTO employeeDTO) throws Exception {
        /* save. */
        Integer generatedEmployeeId = this.employeeDAO.save(mapper.getEmployee(employeeDTO)).getId();
        return generatedEmployeeId;
    }

    @Override
    public void updateEmployee(EmployeeDTO employeeDTO) throws Exception {
        /* update. */
        this.employeeDAO.update(mapper.getEmployee(employeeDTO));
    }

    @Override
    public void deleteEmployee(int employeeID) throws Exception {
        /* delete. */
        this.employeeDAO.delete(employeeID);
    }

    @Transactional(readOnly = true)
    @Override
    public EmployeeDTO getEmployeeByID(int employeeID) throws Exception {
        /* get employee by ID. */
        Employee employee = this.employeeDAO.get(employeeID);

        /* convert employee to DTO. */
        return this.mapper.getEmployeeDTO(employee);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EmployeeDTO> getAllEmployees() throws Exception {
        /* get all employees. */
        List<Employee> employeeList = this.employeeDAO.getAll();

        /* convert employeeList to DTOList. */
        return this.mapper.getEmployeeDTOs(employeeList);
    }
}
