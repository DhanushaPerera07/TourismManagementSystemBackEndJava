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

import lombok.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Table(name = "accommodation")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Accommodation implements SuperEntity<Serializable> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "situated_in", length = 100)
    private String situatedIn;

    @Column(name = "star_rating")
    private Integer starRating;

    @Column(name = "type", length = 100)
    private String type;

    @Column(name = "contact", length = 45)
    private String contact;

    @Column(name = "email", length = 200)
    private String email;

    @Column(name = "address", length = 200)
    private String address;

    @Lob
    @Column(name = "website")
    private String website;

    @Lob
    @Column(name = "special_details")
    private String specialDetails;

    @Lob
    @Column(name = "remark")
    private String remark;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "updated")
    private ZonedDateTime updated;

    public Accommodation(Integer id,
                         String name,
                         String situatedIn,
                         Integer starRating,
                         String type,
                         String contact,
                         String email,
                         String address,
                         String website,
                         String specialDetails,
                         String remark) {
        this.id = id;
        this.name = name;
        this.situatedIn = situatedIn;
        this.starRating = starRating;
        this.type = type;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.website = website;
        this.specialDetails = specialDetails;
        this.remark = remark;
    }

    public Accommodation(String name,
                         String situatedIn,
                         Integer starRating,
                         String type,
                         String contact,
                         String email,
                         String address,
                         String website,
                         String specialDetails,
                         String remark) {
        this.name = name;
        this.situatedIn = situatedIn;
        this.starRating = starRating;
        this.type = type;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.website = website;
        this.specialDetails = specialDetails;
        this.remark = remark;
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