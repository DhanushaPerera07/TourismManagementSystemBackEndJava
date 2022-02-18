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

@Table(name = "accommodation_package", indexes = {
        @Index(name = "fk_accommodation_package_accommodation_idx", columnList = "accommodation_id")
})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AccommodationPackage implements SuperEntity<Serializable> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "year")
    private Integer year;

    @Column(name = "season", nullable = false, length = 45)
    private String season;

    @Column(name = "valid_period", length = 45)
    private String validPeriod;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "updated")
    private ZonedDateTime updated;

    @ManyToOne(optional = false)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    public AccommodationPackage(Integer id,
                                Integer year,
                                String season,
                                String validPeriod,
                                Accommodation accommodation) {
        this.id = id;
        this.year = year;
        this.season = season;
        this.validPeriod = validPeriod;
        this.accommodation = accommodation;
    }

    public AccommodationPackage(Integer year,
                                String season,
                                String validPeriod,
                                Accommodation accommodation) {
        this.year = year;
        this.season = season;
        this.validPeriod = validPeriod;
        this.accommodation = accommodation;
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