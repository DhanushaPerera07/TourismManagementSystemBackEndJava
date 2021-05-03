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

public class AccommodationPackage {
    private int id;
    private String validPeriod;
    private boolean isLatestPackage;
    private Date created;
    private Date lastUpdated;
    private int accommodationId;

    /* no args constructor. */
    public AccommodationPackage() {
    }

    /* full args constructor. */
    public AccommodationPackage(int id,
                                String validPeriod,
                                boolean isLatestPackage,
                                Date created,
                                Date lastUpdated,
                                int accommodationId) {
        this.id = id;
        this.validPeriod = validPeriod;
        this.isLatestPackage = isLatestPackage;
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.accommodationId = accommodationId;
    }

    /* getters and setters. */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(String validPeriod) {
        this.validPeriod = validPeriod;
    }

    public boolean isLatestPackage() {
        return isLatestPackage;
    }

    public void setLatestPackage(boolean latestPackage) {
        isLatestPackage = latestPackage;
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

    public int getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(int accommodationId) {
        this.accommodationId = accommodationId;
    }

    @Override
    public String toString() {
        return "AccommodationPackage{" +
                "id=" + id +
                ", validPeriod='" + validPeriod + '\'' +
                ", isLatestPackage=" + isLatestPackage +
                ", created=" + created +
                ", lastUpdated=" + lastUpdated +
                ", accommodationId=" + accommodationId +
                '}';
    }
}
