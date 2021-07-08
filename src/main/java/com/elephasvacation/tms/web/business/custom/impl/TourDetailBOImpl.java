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
 * @date : 06/07/2021
 */
package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.TourDetailBO;
import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.TourDetailDAO;
import com.elephasvacation.tms.web.entity.TourDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TourDetailBOImpl implements TourDetailBO {

    private final TourDetailDAO tourDetailDAO;
    private Connection connection;

    public TourDetailBOImpl() {
        this.tourDetailDAO = DAOFactory.getInstance().getDAO(DAOTypes.TOUR_DETAIL);
    }

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.tourDetailDAO.setConnection(connection);
    }

    @Override
    public Integer createTourDetail(TourDetail tourDetail) throws Exception {
        return this.tourDetailDAO.save(tourDetail);
    }

    @Override
    public boolean updateTourDetail(TourDetail tourDetail) throws Exception {
        return this.tourDetailDAO.update(tourDetail);
    }

    @Override
    public boolean deleteTourDetail(int tourDetailID) throws Exception {
        return this.tourDetailDAO.delete(tourDetailID);
    }

    @Override
    public TourDetail getTourDetailByID(int tourDetailID) throws Exception {
        return this.tourDetailDAO.get(tourDetailID);
    }

    @Override
    public TourDetail getTourDetailByIDAndCustomerID(int customerID, int tourDetailID) throws Exception {
        return this.tourDetailDAO.getTourDetailByCustomerIDAndTourDetailID(customerID, tourDetailID);
    }

    @Override
    public List<TourDetail> getAllTourDetails() throws Exception {
        return this.tourDetailDAO.getAll();
    }

    @Override
    public List<TourDetail> getAllTourDetailsByCustomerID(int customerID) throws SQLException {
        return this.tourDetailDAO.getAllTourDetailsByCustomerID(customerID);
    }
}
