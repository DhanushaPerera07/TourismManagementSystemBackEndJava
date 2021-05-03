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

public class RoomPlan {
    private int accommodationPackageId;
    private String roomType;
    private BigDecimal singleRoom;
    private BigDecimal doubleRoom;
    private BigDecimal tripleRoom;
    private BigDecimal quadRoom;
    private BigDecimal childRoom;
    private BigDecimal family;
    private BigDecimal suite;
    private BigDecimal remark;

    /* no args constructor. */
    public RoomPlan() {
    }

    /* full args constructor. */
    public RoomPlan(int accommodationPackageId,
                    String roomType,
                    BigDecimal singleRoom,
                    BigDecimal doubleRoom,
                    BigDecimal tripleRoom,
                    BigDecimal quadRoom,
                    BigDecimal childRoom,
                    BigDecimal family,
                    BigDecimal suite,
                    BigDecimal remark) {
        this.accommodationPackageId = accommodationPackageId;
        this.roomType = roomType;
        this.singleRoom = singleRoom;
        this.doubleRoom = doubleRoom;
        this.tripleRoom = tripleRoom;
        this.quadRoom = quadRoom;
        this.childRoom = childRoom;
        this.family = family;
        this.suite = suite;
        this.remark = remark;
    }

    /* getters and setters. */
    public int getAccommodationPackageId() {
        return accommodationPackageId;
    }

    public void setAccommodationPackageId(int accommodationPackageId) {
        this.accommodationPackageId = accommodationPackageId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public BigDecimal getSingleRoom() {
        return singleRoom;
    }

    public void setSingleRoom(BigDecimal singleRoom) {
        this.singleRoom = singleRoom;
    }

    public BigDecimal getDoubleRoom() {
        return doubleRoom;
    }

    public void setDoubleRoom(BigDecimal doubleRoom) {
        this.doubleRoom = doubleRoom;
    }

    public BigDecimal getTripleRoom() {
        return tripleRoom;
    }

    public void setTripleRoom(BigDecimal tripleRoom) {
        this.tripleRoom = tripleRoom;
    }

    public BigDecimal getQuadRoom() {
        return quadRoom;
    }

    public void setQuadRoom(BigDecimal quadRoom) {
        this.quadRoom = quadRoom;
    }

    public BigDecimal getChildRoom() {
        return childRoom;
    }

    public void setChildRoom(BigDecimal childRoom) {
        this.childRoom = childRoom;
    }

    public BigDecimal getFamily() {
        return family;
    }

    public void setFamily(BigDecimal family) {
        this.family = family;
    }

    public BigDecimal getSuite() {
        return suite;
    }

    public void setSuite(BigDecimal suite) {
        this.suite = suite;
    }

    public BigDecimal getRemark() {
        return remark;
    }

    public void setRemark(BigDecimal remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "RoomPlan{" +
                "accommodationPackageId=" + accommodationPackageId +
                ", roomType='" + roomType + '\'' +
                ", singleRoom=" + singleRoom +
                ", doubleRoom=" + doubleRoom +
                ", tripleRoom=" + tripleRoom +
                ", quadRoom=" + quadRoom +
                ", childRoom=" + childRoom +
                ", family=" + family +
                ", suite=" + suite +
                ", remark=" + remark +
                '}';
    }
}
