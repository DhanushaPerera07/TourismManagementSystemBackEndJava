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

import java.math.BigDecimal;

public class GuidePlan {
    private int accommodationPackageId;
    private boolean isAvailable;
    private BigDecimal cost;
    private String remark;

    /* no args constructor. */
    public GuidePlan() {
    }

    /* full args constructor. */
    public GuidePlan(int accommodationPackageId,
                     boolean isAvailable,
                     BigDecimal cost,
                     String remark) {
        this.accommodationPackageId = accommodationPackageId;
        this.isAvailable = isAvailable;
        this.cost = cost;
        this.remark = remark;
    }

    /* getters and setters. */
    public int getAccommodationPackageId() {
        return accommodationPackageId;
    }

    public void setAccommodationPackageId(int accommodationPackageId) {
        this.accommodationPackageId = accommodationPackageId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "GuidePlan{" +
                "accommodationPackageId=" + accommodationPackageId +
                ", isAvailable=" + isAvailable +
                ", cost=" + cost +
                ", remark='" + remark + '\'' +
                '}';
    }
}
