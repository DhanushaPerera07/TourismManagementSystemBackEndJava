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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EmployeeDTO implements Serializable {

    private int id;
    private String name;
    private String address;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class) // dd.MM.yyyy
    private LocalDate dateOfBirth;
    /*    @JsonbDateFormat("yyyy-MM-dd") // dd.MM.yyyy
        private LocalDate dateOfBirth;*/
    private String nic;
    private String contact;
    private String email;
    private GenderTypes gender;
    private String position;
    private String status;
    //    private String password;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
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
