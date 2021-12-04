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

package com.elephasvacation.tms.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationRateId implements Serializable {
    private static final long serialVersionUID = -4021988096487942951L;

    @Column(name = "pkg_room_type_id", nullable = false)
    private Integer pkgRoomTypeId;

    @Column(name = "pkg_room_category_id", nullable = false)
    private Integer pkgRoomCategoryId;

    @Column(name = "pkg_meal_plan_id", nullable = false)
    private Integer pkgMealPlanId;

    @Column(name = "accommodation_package_id", nullable = false)
    private Integer accommodationPackageId;

    @Override
    public int hashCode() {
        return Objects.hash(pkgMealPlanId, accommodationPackageId, pkgRoomTypeId, pkgRoomCategoryId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccommodationRateId entity = (AccommodationRateId) o;
        return Objects.equals(this.pkgMealPlanId, entity.pkgMealPlanId) &&
                Objects.equals(this.accommodationPackageId, entity.accommodationPackageId) &&
                Objects.equals(this.pkgRoomTypeId, entity.pkgRoomTypeId) &&
                Objects.equals(this.pkgRoomCategoryId, entity.pkgRoomCategoryId);
    }
}