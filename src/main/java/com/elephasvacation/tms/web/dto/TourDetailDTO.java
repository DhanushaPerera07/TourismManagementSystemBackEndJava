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
/*
 * @author : Dhanusha Perera
 * @date : 03/07/2021
 */
package com.elephasvacation.tms.web.dto;

import com.elephasvacation.tms.web.entity.enumeration.TourDetailStatusTypes;
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
public class TourDetailDTO implements Serializable {

    private int id;
    private int noOfDays;
    private int noOfPeople;
    private int noOfChildren;
    private int starCategory;
    private LocalDateTime arrivalDate;
    private LocalDateTime departureDate;
    private TourDetailStatusTypes status;
    private BigDecimal exchangeRate;
    private String tourAgent;
    private BigDecimal agentProfit;
    private int customerId;
    private LocalDateTime created;
    private LocalDateTime updated;

    public TourDetailDTO(
            int id,
            int noOfDays,
            int noOfPeople,
            int noOfChildren,
            int starCategory,
            LocalDateTime arrivalDate,
            LocalDateTime departureDate,
            TourDetailStatusTypes status,
            BigDecimal exchangeRate,
            String tourAgent,
            BigDecimal agentProfit,
            int customerId) {
        this.id = id;
        this.noOfDays = noOfDays;
        this.noOfPeople = noOfPeople;
        this.noOfChildren = noOfChildren;
        this.starCategory = starCategory;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.status = status;
        this.exchangeRate = exchangeRate;
        this.tourAgent = tourAgent;
        this.agentProfit = agentProfit;
        this.customerId = customerId;
    }
}
