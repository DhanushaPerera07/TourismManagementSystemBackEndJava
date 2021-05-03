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
 */
/**
 * @author : Dhanusha Perera
 * @date : 03/05/2021
 */
package com.elephasvacation.tms.web.entity;

import java.sql.Date;

public class Customer {
    private int id;
    private String name;
    private String nationality;
    private String passportNo;
    private String email;
    private String countryCallingCode;
    private String country;
    private String description;
    private String additionalNotes;
    private Date addedDate;
    private Date lastUpdated;

    /* no args constructor. */
    public Customer() {
    }

    /* constructor without addedDate and lastUpdated properties. */
    public Customer(int id,
                    String name,
                    String nationality,
                    String passportNo,
                    String email,
                    String countryCallingCode,
                    String country,
                    String description,
                    String additionalNotes) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.passportNo = passportNo;
        this.email = email;
        this.countryCallingCode = countryCallingCode;
        this.country = country;
        this.description = description;
        this.additionalNotes = additionalNotes;
    }

    /* full args constructor. */
    public Customer(int id,
                    String name,
                    String nationality,
                    String passportNo,
                    String email,
                    String countryCallingCode,
                    String country,
                    String description,
                    String additionalNotes,
                    Date addedDate,
                    Date lastUpdated) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.passportNo = passportNo;
        this.email = email;
        this.countryCallingCode = countryCallingCode;
        this.country = country;
        this.description = description;
        this.additionalNotes = additionalNotes;
        this.addedDate = addedDate;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryCallingCode() {
        return countryCallingCode;
    }

    public void setCountryCallingCode(String countryCallingCode) {
        this.countryCallingCode = countryCallingCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
                ", passportNo='" + passportNo + '\'' +
                ", email='" + email + '\'' +
                ", countryCallingCode='" + countryCallingCode + '\'' +
                ", country='" + country + '\'' +
                ", description='" + description + '\'' +
                ", additionalNotes='" + additionalNotes + '\'' +
                ", addedDate=" + addedDate +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
