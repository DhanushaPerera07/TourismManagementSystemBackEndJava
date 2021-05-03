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
 */
/**
 * @author : Dhanusha Perera
 * @date : 03/05/2021
 */
package com.elephasvacation.tms.web.entity;

import java.math.BigDecimal;
import java.sql.Date;

public class MealPlan {
    private int accommodationPackageId;
    private BigDecimal bedAndBreakfast;
    private BigDecimal halfBoard;
    private BigDecimal fullBoard;
    private BigDecimal allInclusive;
    private Date created;
    private Date lastUpdated;
    private String remark;

    /* no args constructor. */
    public MealPlan() {
    }

    /* full args constructor. */

    public MealPlan(int accommodationPackageId,
                    BigDecimal bedAndBreakfast,
                    BigDecimal halfBoard,
                    BigDecimal fullBoard,
                    BigDecimal allInclusive,
                    Date created,
                    Date lastUpdated,
                    String remark) {
        this.accommodationPackageId = accommodationPackageId;
        this.bedAndBreakfast = bedAndBreakfast;
        this.halfBoard = halfBoard;
        this.fullBoard = fullBoard;
        this.allInclusive = allInclusive;
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.remark = remark;
    }

    /* getters and setters. */
    public int getAccommodationPackageId() {
        return accommodationPackageId;
    }

    public void setAccommodationPackageId(int accommodationPackageId) {
        this.accommodationPackageId = accommodationPackageId;
    }

    public BigDecimal getBedAndBreakfast() {
        return bedAndBreakfast;
    }

    public void setBedAndBreakfast(BigDecimal bedAndBreakfast) {
        this.bedAndBreakfast = bedAndBreakfast;
    }

    public BigDecimal getHalfBoard() {
        return halfBoard;
    }

    public void setHalfBoard(BigDecimal halfBoard) {
        this.halfBoard = halfBoard;
    }

    public BigDecimal getFullBoard() {
        return fullBoard;
    }

    public void setFullBoard(BigDecimal fullBoard) {
        this.fullBoard = fullBoard;
    }

    public BigDecimal getAllInclusive() {
        return allInclusive;
    }

    public void setAllInclusive(BigDecimal allInclusive) {
        this.allInclusive = allInclusive;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "MealPlan{" +
                "accommodationPackageId=" + accommodationPackageId +
                ", bedAndBreakfast=" + bedAndBreakfast +
                ", halfBoard=" + halfBoard +
                ", fullBoard=" + fullBoard +
                ", allInclusive=" + allInclusive +
                ", created=" + created +
                ", lastUpdated=" + lastUpdated +
                ", remark='" + remark + '\'' +
                '}';
    }
}
