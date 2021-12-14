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
import com.elephasvacation.tms.web.business.custom.util.AccommodationDTOMapper;
import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.AccommodationDAO;
import com.elephasvacation.tms.web.dto.AccommodationDTO;
import com.elephasvacation.tms.web.entity.Accommodation;

import javax.persistence.EntityManager;
import java.util.List;

public class AccommodationBOImpl implements AccommodationBO {

    AccommodationDAO accommodationDAO = DAOFactory.getInstance().getDAO(DAOTypes.ACCOMMODATION);
    AccommodationDTOMapper mapper = AccommodationDTOMapper.instance;
    private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;

        /* Set Entity Manager to the DAL. */
        this.accommodationDAO.setEntityManager(this.entityManager);
    }


    @Override
    public Integer createAccommodation(AccommodationDTO accommodationDTO) throws Exception {
        this.entityManager.getTransaction().begin();
        Accommodation accommodation = this.accommodationDAO.save(this.mapper.getAccommodation(accommodationDTO));
        this.entityManager.getTransaction().commit();
        return accommodation.getId();
    }

    @Override
    public void updateAccommodation(AccommodationDTO accommodationDTO) throws Exception {
        this.entityManager.getTransaction().begin();
        this.accommodationDAO.update(this.mapper.getAccommodation(accommodationDTO));
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void deleteAccommodation(int accommodationID) throws Exception {
        this.entityManager.getTransaction().begin();
        this.accommodationDAO.delete(accommodationID);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public AccommodationDTO getAccommodationByID(int accommodationID) throws Exception {
        this.entityManager.getTransaction().begin();
        AccommodationDTO accommodationDTO = this.mapper.
                getAccommodationDTO(this.accommodationDAO.get(accommodationID));
        this.entityManager.getTransaction().commit();
        return accommodationDTO;
    }

    @Override
    public List<AccommodationDTO> getAllAccommodations() throws Exception {
        this.entityManager.getTransaction().begin();
        List<AccommodationDTO> accommodationDTOList = this.mapper.
                getAccommodationDTOList(this.accommodationDAO.getAll());
        this.entityManager.getTransaction().commit();
        return accommodationDTOList;
    }
}
