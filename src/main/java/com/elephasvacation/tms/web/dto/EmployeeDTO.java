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
package com.elephasvacation.tms.web.dto;

import com.elephasvacation.tms.web.entity.enumeration.GenderTypes;
import jakarta.json.bind.annotation.JsonbDateFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO implements Serializable {

    private int id;
    private String name;
    private String address;
    @JsonbDateFormat("yyyy-MM-dd") // dd.MM.yyyy
    private LocalDate dateOfBirth;
    private String nic;
    private String contact;
    private String email;
    private GenderTypes gender;
    private String position;
    private String status;
    //    private String password;
    @JsonbDateFormat("dd.MM.yyyy")
    private LocalDateTime created;
    @JsonbDateFormat("dd.MM.yyyy")
    private LocalDateTime lastUpdated;

    /* custom constructor - without created, lastUpdated. */
    public EmployeeDTO(int id,
                       String name,
                       String address,
                       LocalDate dateOfBirth,
                       String nic,
                       String contact,
                       String email,
                       GenderTypes gender,
                       String position,
                       String status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.nic = nic;
        this.contact = contact;
        this.email = email;
        this.gender = gender;
        this.position = position;
        this.status = status;
    }

    public EmployeeDTO(String name,
                       String address,
                       LocalDate dateOfBirth,
                       String nic,
                       String contact,
                       String email,
                       GenderTypes gender,
                       String position,
                       String status) {
        this.name = name;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.nic = nic;
        this.contact = contact;
        this.email = email;
        this.gender = gender;
        this.position = position;
        this.status = status;
    }
}
