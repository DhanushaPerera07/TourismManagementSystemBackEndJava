/*
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
 * @author : Dhanusha Perera
 * @date : 03/05/2021
 */
/*
 * @author : Dhanusha Perera
 * @date : 03/05/2021
 */
package com.elephasvacation.tms.web.entity;

import com.elephasvacation.tms.web.entity.enumeration.TourDetailStatusTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourDetail implements SuperEntity {
    private int id;
    private int noOfDays;
    private int noOfPeople;
    private int noOfChildren;
    private int starCategory;
    private Date arrivalDate;
    private Date departureDate;
    private TourDetailStatusTypes status;
    private BigDecimal exchangeRate;
    private String tourAgent;
    private BigDecimal agentProfit;
    private int customerId;
    private Date created;
    private Date lastUpdated;

    public TourDetail(int id,
                      int noOfDays,
                      int noOfPeople,
                      int noOfChildren,
                      int starCategory,
                      Date arrivalDate,
                      Date departureDate,
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
