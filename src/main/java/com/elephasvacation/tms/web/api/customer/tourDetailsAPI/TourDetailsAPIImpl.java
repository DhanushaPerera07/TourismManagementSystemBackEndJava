/**
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
 * @date : 03/07/2021
 * @author : Dhanusha Perera
 * @date : 03/07/2021
 * @author : Dhanusha Perera
 * @date : 03/07/2021
 * @author : Dhanusha Perera
 * @date : 03/07/2021
 * @author : Dhanusha Perera
 * @date : 03/07/2021
 * @author : Dhanusha Perera
 * @date : 03/07/2021
 * @author : Dhanusha Perera
 * @date : 03/07/2021
 */
/**
 * @author : Dhanusha Perera
 * @date : 03/07/2021
 */
package com.elephasvacation.tms.web.api.customer.tourDetailsAPI;

import com.elephasvacation.tms.web.business.BOFactory;
import com.elephasvacation.tms.web.business.BOTypes;
import com.elephasvacation.tms.web.business.custom.TourDetailBO;
import com.elephasvacation.tms.web.business.custom.util.EntityDTOMapper;
import com.elephasvacation.tms.web.dto.TourDetailsDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TourDetailsAPIImpl implements TourDetailsAPI {

    private final TourDetailBO tourDetailBO;
    private final EntityDTOMapper mapper = EntityDTOMapper.instance;

    public TourDetailsAPIImpl() {
        this.tourDetailBO = BOFactory.getInstance().getBO(BOTypes.TOUR_DETAIL);
    }

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.tourDetailBO.setConnection(connection);
    }

    @Override
    public Integer createTourDetails(TourDetailsDTO tourDetailsDTO) throws Exception {
        return this.tourDetailBO.createTourDetail(mapper.getTourDetail(tourDetailsDTO));
    }

    @Override
    public boolean updateTourDetails(TourDetailsDTO tourDetailsDTO) throws Exception {
        return this.tourDetailBO.updateTourDetail(mapper.getTourDetail(tourDetailsDTO));
    }

    @Override
    public boolean deleteTourDetails(int tourDetailID) throws Exception {
        return this.tourDetailBO.deleteTourDetail(tourDetailID);
    }

    @Override
    public TourDetailsDTO getTourDetailsByID(int tourDetailID) throws Exception {
        return mapper.getTourDetailsDTO(this.tourDetailBO.getTourDetailByID(tourDetailID));
    }

    @Override
    public TourDetailsDTO getTourDetailsByIDAndCustomerID(int customerID, int tourDetailID) throws Exception {
        return mapper.getTourDetailsDTO(this.tourDetailBO.getTourDetailByIDAndCustomerID(customerID, tourDetailID));
    }

    @Override
    public List<TourDetailsDTO> getAllTourDetailByCustomerID(int customerID) throws SQLException {
        return mapper.getTourDetailDTOs(this.tourDetailBO.getAllTourDetailsByCustomerID(customerID));
    }

    @Override
    public List<TourDetailsDTO> getAllTourDetails() throws Exception {
        return mapper.getTourDetailDTOs(this.tourDetailBO.getAllTourDetails());
    }
}
