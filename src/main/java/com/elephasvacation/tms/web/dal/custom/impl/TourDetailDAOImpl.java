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
 * @date : 04/07/2021
 */
package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.dal.CrudUtil;
import com.elephasvacation.tms.web.dal.custom.TourDetailDAO;
import com.elephasvacation.tms.web.entity.TourDetail;
import com.elephasvacation.tms.web.entity.enumeration.TourDetailStatusTypes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TourDetailDAOImpl implements TourDetailDAO {

    private static final String COLUMN_NAMES =
            "(no_of_days, no_of_people, no_of_children, star_category, " +
                    "arrival_date, departure_date, status, exchange_rate, " +
                    "tour_agent, agent_profit, customer_id)";
    private Connection connection;

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.connection = connection;
    }

    @Override
    public Integer save(TourDetail tourDetail) throws Exception {
        return CrudUtil.executeAndReturnGeneratedKey(this.connection,
                "INSERT INTO tour_detail " + COLUMN_NAMES + " VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                tourDetail.getNoOfDays(),
                tourDetail.getNoOfPeople(),
                tourDetail.getNoOfChildren(),
                tourDetail.getStarCategory(),
                tourDetail.getArrivalDate(),
                tourDetail.getDepartureDate(),
                tourDetail.getStatus().toString(),
                tourDetail.getExchangeRate(),
                tourDetail.getTourAgent(),
                tourDetail.getAgentProfit(),
                tourDetail.getCustomerId()
        );
    }

    @Override
    public boolean update(TourDetail tourDetail) throws Exception {
        return CrudUtil.execute(this.connection,
                "UPDATE tour_detail SET no_of_days=?, no_of_people=?, no_of_children=?, star_category=?, arrival_date=?, departure_date=?, status=?, exchange_rate=?, tour_agent=?, agent_profit=?, customer_id=?  WHERE id=?",
                tourDetail.getNoOfDays(),
                tourDetail.getNoOfPeople(),
                tourDetail.getNoOfChildren(),
                tourDetail.getStarCategory(),
                tourDetail.getArrivalDate(),
                tourDetail.getDepartureDate(),
                tourDetail.getStatus().toString(),
                tourDetail.getExchangeRate(),
                tourDetail.getTourAgent(),
                tourDetail.getAgentProfit(),
                tourDetail.getCustomerId(),
                tourDetail.getId()
        );
    }

    @Override
    public boolean delete(Integer key) throws Exception {
        return CrudUtil.execute(connection,
                "DELETE FROM tour_detail WHERE id=?",
                key);
    }

    @Override
    public TourDetail get(Integer key) throws Exception {
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM tour_detail WHERE id=?",
                key);

        if (resultSet.next()) {
            return getTourDetailObject(resultSet);
        } else {
            return null;
        }
    }

    @Override
    public List<TourDetail> getAll() throws Exception {
        List<TourDetail> tourDetailArr = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM tour_detail");
        while (resultSet.next()) {
            tourDetailArr.add(getTourDetailObject(resultSet));
        }
        return tourDetailArr;
    }

    @Override
    public List<TourDetail> getAllTourDetailsByCustomerID(int customerID) throws SQLException {
        List<TourDetail> tourDetailArr = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM tour_detail WHERE customer_id=?", customerID);
        while (resultSet.next()) {
            tourDetailArr.add(getTourDetailObject(resultSet));
        }
        return tourDetailArr;
    }

    private TourDetail getTourDetailObject(ResultSet resultSet) throws SQLException {
        return new TourDetail(resultSet.getInt("id"),
                resultSet.getInt("no_of_days"),
                resultSet.getInt("no_of_people"),
                resultSet.getInt("no_of_children"),
                resultSet.getInt("star_category"),
                resultSet.getDate("arrival_date"),
                resultSet.getDate("departure_date"),
                TourDetailStatusTypes.valueOf(resultSet.getString("status")),
                resultSet.getBigDecimal("exchange_rate"),
                resultSet.getString("tour_agent"),
                resultSet.getBigDecimal("agent_profit"),
                resultSet.getInt("customer_id"),
                resultSet.getDate("created"),
                resultSet.getDate("last_updated")
        );
    }

    @Override
    public TourDetail getTourDetailByCustomerIDAndTourDetailID(int customerID,
                                                               int tourDetailID) throws SQLException {
        ResultSet resultSet = CrudUtil.execute(this.connection,
                "SELECT * FROM tour_detail WHERE id=? AND customer_id=?",
                tourDetailID, customerID);

        if (resultSet.next()) {
            return getTourDetailObject(resultSet);
        } else {
            return null;
        }
    }
}
