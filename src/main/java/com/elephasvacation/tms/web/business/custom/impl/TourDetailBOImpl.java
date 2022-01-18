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
package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.TourDetailBO;
import com.elephasvacation.tms.web.business.custom.util.mapper.TourDetailDTOMapper;
import com.elephasvacation.tms.web.dal.custom.TourDetailDAO;
import com.elephasvacation.tms.web.dto.TourDetailDTO;
import com.elephasvacation.tms.web.entity.TourDetail;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NoArgsConstructor
@Transactional
@Service
public class TourDetailBOImpl implements TourDetailBO {

    @Autowired
    private TourDetailDAO tourDetailDAO;

    @Autowired
    private TourDetailDTOMapper mapper;


    @Override
    public Integer createTourDetail(TourDetailDTO tourDetailDTO) throws Exception {

        /* convert DTO to entity. */
        TourDetail tourDetail = this.mapper.getTourDetail(tourDetailDTO);

        /* save. */
        return this.tourDetailDAO.save(tourDetail).getId();
    }

    @Override
    public void updateTourDetail(TourDetailDTO tourDetailDTO) throws Exception {
        /* update. */
        this.tourDetailDAO.update(this.mapper.getTourDetail(tourDetailDTO));
    }

    @Override
    public void deleteTourDetail(Integer tourDetailID) throws Exception {
        /* delete. */
        this.tourDetailDAO.delete(tourDetailID);
    }

    /**
     * Get tour detail by tourDetailID.
     *
     * @return TourDetailDTO tour detail.
     */
    @Transactional(readOnly = true)
    @Override
    public TourDetailDTO getTourDetailByID(Integer tourDetailID) throws Exception {

        /* get tour detail by tourDetailID. */
        TourDetail tourDetail = this.tourDetailDAO.get(tourDetailID);

        /* convert entity to DTO. */
        return this.mapper.getTourDetailDTO(tourDetail);
    }

    /**
     * Get tour details by customerID & tourDetailID.
     *
     * @return TourDetailDTO tour detail.
     */
    @Transactional(readOnly = true)
    @Override
    public TourDetailDTO getTourDetailByIDAndCustomerID(Integer customerID, Integer tourDetailID) throws Exception {

        /* get tour detail by customerID &  tourDetailID. */
        TourDetail tourDetail = this.tourDetailDAO.
                getTourDetailByCustomerIDAndTourDetailID(customerID, tourDetailID);

        /* convert tourDetail to DTO. */
        return this.mapper.getTourDetailDTO(tourDetail);
    }

    /**
     * Get all tour details.
     *
     * @return List<TourDetailDTO> all tour details.
     */
    @Transactional(readOnly = true)
    @Override
    public List<TourDetailDTO> getAllTourDetails() throws Exception {

        /* get all Tour Details. */
        List<TourDetail> tourDetailList = this.tourDetailDAO.getAll();

        /* convert tourDetailList to DTOList. */
        return this.mapper.getTourDetailDTOList(tourDetailList);
    }

    /**
     * Get tour details by customerID.
     *
     * @return List<TourDetailDTO> all tour details for a customer.
     */
    @Transactional(readOnly = true)
    @Override
    public List<TourDetailDTO> getAllTourDetailsByCustomerID(Integer customerID) throws Exception {
        /* get all tour details by customerID. */
        List<TourDetail> tourDetailList = this.tourDetailDAO.getAllTourDetailsByCustomerID(customerID);

        /* convert tourDetailList to DTOList. */
        return this.mapper.getTourDetailDTOList(tourDetailList);
    }
}
