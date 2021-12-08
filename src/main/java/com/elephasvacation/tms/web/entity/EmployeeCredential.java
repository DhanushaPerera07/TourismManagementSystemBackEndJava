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

package com.elephasvacation.tms.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "employee_credential", indexes = {
        @Index(name = "email_UNIQUE", columnList = "email", unique = true)
})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCredential implements SuperEntity {
    @Id
    @Column(name = "employee_id", nullable = false)
    private Integer id;

    @Column(name = "email", length = 200)
    private String email;

    @Lob
    @Column(name = "password")
    private String password;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;

    public EmployeeCredential(Integer id,
                              String email,
                              String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public EmployeeCredential(String email,
                              String password) {
        this.email = email;
        this.password = password;
    }

    @PrePersist
    public void creationTimeStamps() {
        created = LocalDateTime.now();
    }


    @PreUpdate
    public void updateTimeStamps() {
        updated = LocalDateTime.now();
    }
}