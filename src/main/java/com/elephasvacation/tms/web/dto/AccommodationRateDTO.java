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
package com.elephasvacation.tms.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AccommodationRateDTO implements Serializable {

    private AccommodationRateDTOId accommodationRateId;
    private BigDecimal rate;
    private LocalDateTime created;
    private LocalDateTime updated;

    public AccommodationRateDTO(Integer accommodationPackageId,
                                Integer pkgMealPlanId,
                                Integer pkgRoomCategoryId,
                                Integer pkgRoomTypeId,
                                BigDecimal rate) {
        this.accommodationRateId = new AccommodationRateDTOId(accommodationPackageId,
                pkgMealPlanId,
                pkgRoomCategoryId,
                pkgRoomTypeId);
        this.rate = rate;
    }

    public AccommodationRateDTO(AccommodationRateDTOId accommodationRateDTOId,
                                BigDecimal rate) {
        this.accommodationRateId = accommodationRateDTOId;
        this.rate = rate;
    }

}
