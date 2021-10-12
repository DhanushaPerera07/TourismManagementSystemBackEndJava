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
package com.elephasvacation.tms.web.api.accommodationPackage;

import com.elephasvacation.tms.web.business.BOFactory;
import com.elephasvacation.tms.web.business.BOTypes;
import com.elephasvacation.tms.web.business.custom.AccommodationPackageBO;
import com.elephasvacation.tms.web.dto.AccommodationPackageDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AccommodationPackageAPIImpl implements AccommodationPackageAPI {

    private static AccommodationPackageBO accommodationPackageBO = BOFactory
            .getInstance()
            .getBO(BOTypes.ACCOMMODATION_PACKAGE);

    @Override
    public void setConnection(Connection connection) throws Exception {
        accommodationPackageBO.setConnection(connection);
    }

    @Override
    public Integer createAccommodationPackage(AccommodationPackageDTO accommodationPackageDTO) throws Exception {
        return accommodationPackageBO.createAccommodationPackage(accommodationPackageDTO);
    }

    @Override
    public boolean updateAccommodationPackage(AccommodationPackageDTO accommodationPackageDTO) throws Exception {
        return accommodationPackageBO.updateAccommodationPackage(accommodationPackageDTO);
    }

    @Override
    public boolean deleteAccommodationPackage(Integer accommodationPackageID) throws Exception {
        return accommodationPackageBO.deleteAccommodationPackage(accommodationPackageID);
    }

    @Override
    public AccommodationPackageDTO getAccommodationPackageByID(Integer accommodationPackageID) throws Exception {
        return accommodationPackageBO.getAccommodationPackageByID(accommodationPackageID);
    }

    @Override
    public List<AccommodationPackageDTO> getAllAccommodationPackagesByAccommodationID(Integer accommodationID) throws SQLException {
        return accommodationPackageBO.getAllAccommodationPackagesByAccommodationID(accommodationID);
    }

    @Override
    public List<AccommodationPackageDTO> getAllAccommodationPackages() throws Exception {
        return accommodationPackageBO.getAllAccommodationPackages();
    }
}
