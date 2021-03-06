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
 * @author : Dhanusha Perera
 * @date : 06/07/2021
 */
package com.elephasvacation.tms.web.business.custom;

import com.elephasvacation.tms.web.business.SuperBO;
import com.elephasvacation.tms.web.dto.TourDetailDTO;

import java.util.List;

public interface TourDetailBO extends SuperBO {

    Integer createTourDetail(TourDetailDTO tourDetailDTO) throws Exception;

    void updateTourDetail(TourDetailDTO tourDetailDTO) throws Exception;

    void deleteTourDetail(Integer tourDetailID) throws Exception;

    TourDetailDTO getTourDetailByID(Integer tourDetailID) throws Exception;

    TourDetailDTO getTourDetailByIDAndCustomerID(Integer customerID, Integer tourDetailID) throws Exception;

    List<TourDetailDTO> getAllTourDetails() throws Exception;

    List<TourDetailDTO> getAllTourDetailsByCustomerID(Integer customerID) throws Exception;
}
