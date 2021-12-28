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
 * @date : 10/07/2021
 */
package com.elephasvacation.tms.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AccommodationDTO implements Serializable {

    private int id;
    private String name;
    private String situatedIn;
    private int starRating;
    private String type;
    private String contact;
    private String email;
    private String address;
    private String website;
    private String specialDetails;
    private String remark;
    private LocalDateTime created;
    private LocalDateTime lastUpdated;

    public AccommodationDTO(int id,
                            String name,
                            String situatedIn,
                            int starRating,
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

    public AccommodationDTO(String name,
                            String situatedIn,
                            int starRating,
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
}
