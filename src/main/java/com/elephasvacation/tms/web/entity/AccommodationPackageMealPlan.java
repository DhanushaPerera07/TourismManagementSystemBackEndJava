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

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "accommodation_package_meal_plan", indexes = {
        @Index(name = "fk_accommodation_package_has_meal_plan_meal_plan1_idx", columnList = "meal_plan_id"),
        @Index(name = "id_UNIQUE", columnList = "index_id", unique = true),
        @Index(name = "fk_accommodation_package_has_meal_plan_accommodation_packag_idx", columnList = "accommodation_package_id")
})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AccommodationPackageMealPlan implements SuperEntity<Serializable> {
    @EmbeddedId
    private AccommodationPackageMealPlanId id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_id", nullable = false)
    private Integer indexId;

    public AccommodationPackageMealPlan(AccommodationPackageMealPlanId id) {
        this.id = id;
    }

    public AccommodationPackageMealPlan(Integer AccommodationPackageId, Integer mealPlanId) {
        this.id = new AccommodationPackageMealPlanId(AccommodationPackageId, mealPlanId);
    }
}