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
 * @date : 10/07/2021
 */
package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.AccommodationBO;
import com.elephasvacation.tms.web.business.custom.util.mapper.AccommodationDTOMapper;
import com.elephasvacation.tms.web.dal.AccommodationDAO;
import com.elephasvacation.tms.web.dto.AccommodationDTO;
import com.elephasvacation.tms.web.entity.Accommodation;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NoArgsConstructor
@Transactional
@Service
public class AccommodationBOImpl implements AccommodationBO {

    @Autowired
    AccommodationDAO accommodationDAO;

    @Autowired
    AccommodationDTOMapper mapper;

    @Override
    public Integer createAccommodation(AccommodationDTO accommodationDTO) {
        Accommodation accommodation = this.accommodationDAO.save(this.mapper.getAccommodation(accommodationDTO));
        return accommodation.getId();
    }

    @Override
    public void updateAccommodation(AccommodationDTO accommodationDTO) {
        this.accommodationDAO.save(this.mapper.getAccommodation(accommodationDTO));
    }

    @Override
    public void deleteAccommodation(Integer accommodationID) {
        this.accommodationDAO.deleteById(accommodationID);
    }

    @Transactional(readOnly = true)
    @Override
    public AccommodationDTO getAccommodationByID(Integer accommodationID) {
        return this.mapper.
                getAccommodationDTO(this.accommodationDAO.findById(accommodationID).get());
    }

    @Transactional(readOnly = true)
    @Override
    public List<AccommodationDTO> getAllAccommodations() {
        return this.mapper.
                getAccommodationDTOList(this.accommodationDAO.findAll());
    }
}
