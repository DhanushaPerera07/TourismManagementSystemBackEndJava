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

import com.elephasvacation.tms.web.entity.enumeration.TourDetailStatusTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "tour_detail", indexes = {
        @Index(name = "fk_tour_detail_customer1_idx", columnList = "customer_id")
})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourDetail implements SuperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "no_of_days", nullable = false, precision = 6, scale = 2)
    private BigDecimal noOfDays;

    @Column(name = "no_of_adults", nullable = false)
    private Integer noOfAdults;

    @Column(name = "no_of_children")
    private Integer noOfChildren;

    @Column(name = "star_category")
    private Integer starCategory;

    @Column(name = "arrival_date", nullable = false)
    private LocalDateTime arrivalDate;

    @Column(name = "departure_date", nullable = false)
    private LocalDateTime departureDate;

    @Column(name = "status", length = 45)
    private TourDetailStatusTypes status;

    @Column(name = "exchange_rate", precision = 19, scale = 2)
    private BigDecimal exchangeRate;

    @Lob
    @Column(name = "tour_agent")
    private String tourAgent;

    @Column(name = "agent_profit", precision = 12, scale = 2)
    private BigDecimal agentProfit;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    /* constructor with ID. */
    public TourDetail(Integer id,
                      BigDecimal noOfDays,
                      Integer noOfAdults,
                      Integer noOfChildren,
                      Integer starCategory,
                      LocalDateTime arrivalDate,
                      LocalDateTime departureDate,
                      TourDetailStatusTypes status,
                      BigDecimal exchangeRate,
                      String tourAgent,
                      BigDecimal agentProfit,
                      Customer customer) {
        this.id = id;
        this.noOfDays = noOfDays;
        this.noOfAdults = noOfAdults;
        this.noOfChildren = noOfChildren;
        this.starCategory = starCategory;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.status = status;
        this.exchangeRate = exchangeRate;
        this.tourAgent = tourAgent;
        this.agentProfit = agentProfit;
        this.customer = customer;
    }

    /* constructor without ID. */
    public TourDetail(BigDecimal noOfDays,
                      Integer noOfAdults,
                      Integer noOfChildren,
                      Integer starCategory,
                      LocalDateTime arrivalDate,
                      LocalDateTime departureDate,
                      TourDetailStatusTypes status,
                      BigDecimal exchangeRate,
                      String tourAgent,
                      BigDecimal agentProfit,
                      Customer customer) {
        this.noOfDays = noOfDays;
        this.noOfAdults = noOfAdults;
        this.noOfChildren = noOfChildren;
        this.starCategory = starCategory;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.status = status;
        this.exchangeRate = exchangeRate;
        this.tourAgent = tourAgent;
        this.agentProfit = agentProfit;
        this.customer = customer;
    }

    @PrePersist
    public void creationTimeStamps() {
        created = LocalDateTime.now();
    }


    @PreUpdate
    public void updateTimeStamps() {
        updated = LocalDateTime.now();
    }
}