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
package com.elephasvacation.tms.web.entity;

import com.elephasvacation.tms.web.entity.enumeration.GenderTypes;
import lombok.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Table(name = "customer")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE) // @Scope(value = "prototype")
public class Customer implements SuperEntity<Serializable> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "nationality", length = 45)
    private String nationality;

    @Column(name = "passport_no", length = 45)
    private String passportNo;

    @Column(name = "email", length = 200)
    private String email;

    @Column(name = "contact_no", length = 45)
    private String contactNo;

    @Column(name = "country", length = 45)
    private String country;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "additional_notes")
    private String additionalNotes;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "updated")
    private ZonedDateTime updated;

    @Column(name = "gender", length = 6)
    private GenderTypes gender;

    /* constructor with ID. */
    public Customer(Integer id,
                    String name,
                    String nationality,
                    GenderTypes gender,
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
        this.gender = gender;
    }

    /* constructor without ID. */
    public Customer(String name,
                    String nationality,
                    String passportNo,
                    String email,
                    String contactNo,
                    String country,
                    String description,
                    String additionalNotes,
                    GenderTypes gender) {
        this.name = name;
        this.nationality = nationality;
        this.passportNo = passportNo;
        this.email = email;
        this.contactNo = contactNo;
        this.country = country;
        this.description = description;
        this.additionalNotes = additionalNotes;
        this.gender = gender;
    }

    @PrePersist
    public void creationTimeStamps() {
        created = ZonedDateTime.now();
    }


    @PreUpdate
    public void updateTimeStamps() {
        updated = ZonedDateTime.now();
    }

}