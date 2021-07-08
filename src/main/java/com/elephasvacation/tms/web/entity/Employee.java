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
 * @since : 26/04/2021
 */
package com.elephasvacation.tms.web.entity;

import com.elephasvacation.tms.web.entity.enumeration.GenderTypes;

import java.sql.Date;

public class Employee implements SuperEntity{
    private int id;
    private String name;
    private String address;
    private Date dateOfBirth;
    private String nic;
    private String contact;
    private String email;
    private GenderTypes gender;
    private String position;
    private String status; // ACTIVE', 'FORMER EMPLOYEE', 'RESIGNED'
    private String password;
    private Date created;
    private Date lastUpdated;

    /* no args constructor */
    public Employee() {
    }

    /* custom constructor - without created, lastUpdated. */
    public Employee(int id,
                    String name,
                    String address,
                    Date dateOfBirth,
                    String nic,
                    String contact,
                    String email,
                    GenderTypes gender,
                    String position,
                    String status,
                    String password) {
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
        this.password = password;
    }

    /* full args constructor. */
    public Employee(int id,
                    String name,
                    String address,
                    Date dateOfBirth,
                    String nic,
                    String contact,
                    String email,
                    GenderTypes gender,
                    String position,
                    String status,
                    String password,
                    Date created,
                    Date lastUpdated) {
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
        this.password = password;
        this.created = created;
        this.lastUpdated = lastUpdated;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
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

    public GenderTypes getGender() {
        return gender;
    }

    public void setGender(GenderTypes gender) {
        this.gender = gender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", nic='" + nic + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", position='" + position + '\'' +
                ", status='" + status + '\'' +
                ", password='" + password + '\'' +
                ", created=" + created +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
