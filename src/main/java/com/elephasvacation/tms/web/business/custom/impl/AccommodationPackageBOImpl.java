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
 * @date : 28/07/2021
 */
package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.AccommodationPackageBO;
import com.elephasvacation.tms.web.business.custom.util.EntityDTOMapper;
import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.AccommodationPackageDAO;
import com.elephasvacation.tms.web.dto.AccommodationPackageDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AccommodationPackageBOImpl implements AccommodationPackageBO {

    private AccommodationPackageDAO accommodationPackageDAO =
            DAOFactory.getInstance().getDAO(DAOTypes.ACCOMMODATION_PACKAGE);
    private EntityDTOMapper mapper = EntityDTOMapper.instance;

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.accommodationPackageDAO.setConnection(connection);
    }

    @Override
    public Integer createAccommodationPackage(AccommodationPackageDTO accommodationPackageDTO) throws Exception {
        return this.accommodationPackageDAO.save(this.mapper.getAccommodationPackage(accommodationPackageDTO));
    }

    @Override
    public boolean updateAccommodationPackage(AccommodationPackageDTO accommodationPackageDTO) throws Exception {
        return this.accommodationPackageDAO.update(this.mapper.getAccommodationPackage(accommodationPackageDTO));
    }

    @Override
    public boolean deleteAccommodationPackage(Integer accommodationPackageID) throws Exception {
        return this.accommodationPackageDAO.delete(accommodationPackageID);
    }

    @Override
    public AccommodationPackageDTO getAccommodationPackageByID(Integer accommodationPackageID) throws Exception {
        return this.mapper.getAccommodationPackageDTO(this.accommodationPackageDAO.get(accommodationPackageID));
    }

    @Override
    public List<AccommodationPackageDTO> getAllAccommodationPackagesByAccommodationID(Integer accommodationID) throws SQLException {
        return this.mapper
                .getAccommodationPackageDTOs(this.accommodationPackageDAO
                        .getAllAccommodationPackagesByAccommodationID(accommodationID));
    }

    @Override
    public List<AccommodationPackageDTO> getAllAccommodationPackages() throws Exception {
        return this.mapper.getAccommodationPackageDTOs(this.accommodationPackageDAO.getAll());
    }
}
