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
package com.elephasvacation.tms.web.api;

import com.elephasvacation.tms.web.business.custom.CustomerBO;
import com.elephasvacation.tms.web.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/v1/customers")
@RestController
public class CustomerController {

    @Autowired
    private CustomerBO customerBO;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDTO> getAllCustomers() throws Exception {
        return customerBO.getAllCustomers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Integer saveCustomer(@RequestBody CustomerDTO customerDTO) throws Exception {
        customerBO.createCustomer(customerDTO);
        return customerDTO.getId();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id:\\d}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateCustomer(@PathVariable String id,
                               @RequestBody CustomerDTO customerDTO) throws Exception {
        try {
            Integer customerID = new Integer(id);

            if (customerDTO.getId() == customerID) {
                customerDTO.setId(customerID);
                customerBO.updateCustomer(customerDTO);
            } else {
            /* URL param customerID and customerObject's customerID mismatched.
            Therefore, Let's throw an error.*/
                /* TODO: handle error. */
                throw new RuntimeException();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id:\\d}")
    public void deleteCustomer(@PathVariable String id) throws Exception {
        Integer customerID;
        try {
            customerID = new Integer(id);
            customerBO.deleteCustomer(customerID);
        } catch (NumberFormatException e) {
            /* TODO: handle error. */
            e.printStackTrace();
        }
    }

}
