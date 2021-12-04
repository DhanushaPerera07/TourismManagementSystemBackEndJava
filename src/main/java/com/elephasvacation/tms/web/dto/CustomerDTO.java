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
 * @date : 03/07/2021
 */
package com.elephasvacation.tms.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO implements Serializable {

    private int id;
    private String name;
    private String nationality;
    private String passportNo;
    private String email;
    //    private String countryCallingCode;
    private String contactNo;
    private String country;
    private String description;
    private String additionalNotes;
    private LocalDateTime created;
    private LocalDateTime updated;

    public CustomerDTO(int id,
                       String name,
                       String nationality,
                       String passportNo,
                       String email,
                       String contactNo,
                       String country,
                       String description,
                       String additionalNotes) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.passportNo = passportNo;
        this.email = email;
        this.contactNo = contactNo;
        this.country = country;
        this.description = description;
        this.additionalNotes = additionalNotes;
    }

    public CustomerDTO(String name,
                       String nationality,
                       String passportNo,
                       String email,
                       String contactNo,
                       String country,
                       String description,
                       String additionalNotes) {
        this.name = name;
        this.nationality = nationality;
        this.passportNo = passportNo;
        this.email = email;
        this.contactNo = contactNo;
        this.country = country;
        this.description = description;
        this.additionalNotes = additionalNotes;
    }
}
