/**
 * MIT License
 * <p>
 * Copyright (c) 2021 Dhanusha Perera
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * @author : Dhanusha Perera
 * @date : 03/05/2021
 * @author : Dhanusha Perera
 * @date : 03/05/2021
 * @author : Dhanusha Perera
 * @date : 03/05/2021
 */
/**
 * @author : Dhanusha Perera
 * @date : 03/05/2021
 */
package com.elephasvacation.tms.web.entity;

import java.sql.Date;

public class Accommodation implements SuperEntity {
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
    private Date created;
    private Date lastUpdated;

    /* no args constructor. */
    public Accommodation() {
    }

    public Accommodation(int id,
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

    /* full args constructor. */
    public Accommodation(int id, String name, String situatedIn, int starRating, String type, String contact, String email, String address, String website, String specialDetails, String remark, Date created, Date lastUpdated) {
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
        this.created = created;
        this.lastUpdated = lastUpdated;
    }

    /* getters and setters. */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSituatedIn() {
        return situatedIn;
    }

    public void setSituatedIn(String situatedIn) {
        this.situatedIn = situatedIn;
    }

    public int getStarRating() {
        return starRating;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSpecialDetails() {
        return specialDetails;
    }

    public void setSpecialDetails(String specialDetails) {
        this.specialDetails = specialDetails;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "Accommodation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", situatedIn='" + situatedIn + '\'' +
                ", starRating=" + starRating +
                ", type='" + type + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", website='" + website + '\'' +
                ", specialDetails='" + specialDetails + '\'' +
                ", remark='" + remark + '\'' +
                ", created=" + created +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
