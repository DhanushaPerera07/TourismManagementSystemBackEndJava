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
 * @date : 10/07/2021
 */
package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.AccommodationBO;
import com.elephasvacation.tms.web.business.custom.util.EntityDTOMapper;
import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.AccommodationDAO;
import com.elephasvacation.tms.web.dto.AccommodationDTO;

import java.sql.Connection;
import java.util.List;

public class AccommodationBOImpl implements AccommodationBO {

    AccommodationDAO accommodationDAO = DAOFactory.getInstance().getDAO(DAOTypes.ACCOMMODATION);
    EntityDTOMapper mapper = EntityDTOMapper.instance;

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.accommodationDAO.setConnection(connection);
    }


    @Override
    public Integer createAccommodation(AccommodationDTO accommodationDTO) throws Exception {
        return this.accommodationDAO.save(this.mapper.getAccommodation(accommodationDTO));
    }

    @Override
    public boolean updateAccommodation(AccommodationDTO accommodationDTO) throws Exception {
        return this.accommodationDAO.update(this.mapper.getAccommodation(accommodationDTO));
    }

    @Override
    public boolean deleteAccommodation(int accommodationID) throws Exception {
        return this.accommodationDAO.delete(accommodationID);
    }

    @Override
    public AccommodationDTO getAccommodationByID(int accommodationID) throws Exception {
        return this.mapper.getAccommodationDTO(this.accommodationDAO.get(accommodationID));
    }

    @Override
    public List<AccommodationDTO> getAllAccommodations() throws Exception {
        return this.mapper.getAccommodationDTOs(this.accommodationDAO.getAll());
    }
}
