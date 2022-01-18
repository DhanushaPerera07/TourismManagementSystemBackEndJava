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
 * @date : 28/07/2021
 */
package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.AccommodationPackageBO;
import com.elephasvacation.tms.web.business.custom.util.mapper.AccommodationPackageDTOMapper;
import com.elephasvacation.tms.web.dal.custom.AccommodationPackageDAO;
import com.elephasvacation.tms.web.dto.AccommodationPackageDTO;
import com.elephasvacation.tms.web.entity.AccommodationPackage;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@NoArgsConstructor
@Transactional
@Service
public class AccommodationPackageBOImpl implements AccommodationPackageBO {

    @Autowired
    private AccommodationPackageDAO accommodationPackageDAO;

    @Autowired
    private AccommodationPackageDTOMapper mapper;


    @Override
    public Integer createAccommodationPackage(AccommodationPackageDTO accommodationPackageDTO) throws Exception {
        AccommodationPackage accommodationPackage = this.accommodationPackageDAO.
                save(this.mapper.getAccommodationPackage(accommodationPackageDTO));
        return accommodationPackage.getId(); // returns the ID
    }

    @Override
    public void updateAccommodationPackage(AccommodationPackageDTO accommodationPackageDTO) throws Exception {
        this.accommodationPackageDAO.update(this.mapper.getAccommodationPackage(accommodationPackageDTO));
    }

    @Override
    public void deleteAccommodationPackage(Integer accommodationPackageID) throws Exception {
        this.accommodationPackageDAO.delete(accommodationPackageID);
    }

    @Transactional(readOnly = true)
    @Override
    public AccommodationPackageDTO getAccommodationPackageByID(Integer accommodationPackageID) throws Exception {
        return this.mapper.
                getAccommodationPackageDTO(this.accommodationPackageDAO.get(accommodationPackageID));
    }

    /**
     * Get all the Accommodation Packages for Accommodation.
     *
     * @return List<AccommodationPackageDTO> all the accommodation packages for a given accommodation ID.
     */
    @Transactional(readOnly = true)
    @Override
    public List<AccommodationPackageDTO> getAllAccommodationPackagesByAccommodationID(Integer accommodationID)
            throws SQLException {
        /* Get all accommodation packages by accommodation ID. */
        List<AccommodationPackage> allAccommodationPackagesByAccommodationID = this.accommodationPackageDAO.
                getAllAccommodationPackagesByAccommodationID(accommodationID);

        /* convert to DTO. */
        return this.mapper.
                getAccommodationPackageDTOList(allAccommodationPackagesByAccommodationID);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AccommodationPackageDTO> getAllAccommodationPackages() throws Exception {
        return this.mapper.
                getAccommodationPackageDTOList(this.accommodationPackageDAO.getAll());
    }
}
