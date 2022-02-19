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
@author : Dhanusha Perera
@since : 21/04/2021
*/
package com.elephasvacation.tms.web.api;

import com.elephasvacation.tms.web.api.util.ApiUtil;
import com.elephasvacation.tms.web.business.custom.EmployeeBO;
import com.elephasvacation.tms.web.commonconstant.API;
import com.elephasvacation.tms.web.dto.EmployeeDTO;
import com.elephasvacation.tms.web.exception.BadRequestException;
import com.elephasvacation.tms.web.exception.IdFormatException;
import com.elephasvacation.tms.web.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = API.HTTP_LOCALHOST_8080)
@RequestMapping("/api/v1/employees")
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeBO employeeBO;

    /**
     * Get all employees list.
     *
     * @return List<EmployeeDTO> employeesList.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDTO> getAllEmployees() {
        return this.employeeBO.getAllEmployees();
    }

    /**
     * Get employee by employee ID.
     *
     * @return EmployeeDTO employee object.
     * @throws IdFormatException       if the ID is not an Integer.
     * @throws RecordNotFoundException if matching employee record not found,
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            value = "/{id}")
    public EmployeeDTO getEmployeeByID(@PathVariable(name = "id") String id) {
        System.out.println("EmployeeID: " + id);

        Integer employeeID = ApiUtil.getIntegerId(id);

        EmployeeDTO employeeDTO = this.employeeBO.getEmployeeByID(employeeID);
        System.out.println("Employee Result: " + employeeDTO);

        /* If Employee not found. */
        if (employeeDTO == null) throw new RecordNotFoundException();
        return employeeDTO;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Integer saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        System.out.println("API Layer: EmployeeDTO ---> " + employeeDTO);
        Integer employeeId = this.employeeBO.createEmployee(employeeDTO);
        return employeeId;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateEmployee(@PathVariable String id,
                               @RequestBody EmployeeDTO employeeDTO) {
        Integer employeeID = ApiUtil.getIntegerId(id);

        /* validation. */
        if (employeeDTO.getId() != null)
            throw new BadRequestException("Invalid field \"EmployeeId\"");

        /* TODO: Employee - update validation logic. */
        employeeDTO.setId(employeeID);
        this.employeeBO.updateEmployee(employeeDTO);

    }

    /**
     * Delete employee by EmployeeID.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteEmployee(@PathVariable String id) {
        Integer employeeID = ApiUtil.getIntegerId(id);
        System.out.println("Employee ID: " + employeeID);
        this.employeeBO.deleteEmployee(employeeID);
    }
}
