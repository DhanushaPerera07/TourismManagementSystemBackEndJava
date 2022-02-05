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
/*
@author : Dhanusha Perera
@date : 04/07/2021
*/
package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.WebAppConfig;
import com.elephasvacation.tms.web.WebRootConfig;
import com.elephasvacation.tms.web.dal.CustomerDAO;
import com.elephasvacation.tms.web.dal.TourDetailDAO;
import com.elephasvacation.tms.web.entity.Customer;
import com.elephasvacation.tms.web.entity.TourDetail;
import com.elephasvacation.tms.web.entity.enumeration.TourDetailStatusTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebRootConfig.class, WebAppConfig.class})
public class TourDetailDAOImplTest {

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private TourDetailDAO tourDetailDAO;

    @Test
    public void save() {
        assertNotNull(this.customerDAO);
        assertNotNull(this.tourDetailDAO);

        try {
            Customer customer = this.customerDAO.getById(1);

            /* creates a new Employee Credential object. */
            TourDetail tourDetail = new TourDetail(new BigDecimal("4"),
                    4,
                    2,
                    3,
                    LocalDateTime.of(2021, 12, 20, 0, 0),
                    LocalDateTime.of(2021, 12, 24, 0, 0),
                    TourDetailStatusTypes.POTENTIAL,
                    new BigDecimal("200.00"),
                    "None",
                    new BigDecimal("0"),
                    customer);

            /* saving the Tour Detail. */
            Integer generatedTourDetailId = this.tourDetailDAO.save(tourDetail).getId();

            /* assert */
            assertNotNull(generatedTourDetailId);

            /* print the generated ID on the terminal. */
            System.out.println("Generated Tour Detail ID: " + generatedTourDetailId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllTourDetailsByCustomerID() throws Exception {
        assertNotNull(this.tourDetailDAO);

        List<TourDetail> allTourDetailsByCustomerID = this.tourDetailDAO.getAllTourDetailsByCustomerID(2);

        for (TourDetail tourDetail : allTourDetailsByCustomerID) {
            System.out.println(tourDetail);
        }

        assertEquals(2, allTourDetailsByCustomerID.size());

    }
}
