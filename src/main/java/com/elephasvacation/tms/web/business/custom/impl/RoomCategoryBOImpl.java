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
 * @date : 15/07/2021
 */
package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.RoomCategoryBO;
import com.elephasvacation.tms.web.business.custom.util.RoomCategoryDTOMapper;
import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.RoomCategoryDAO;
import com.elephasvacation.tms.web.dto.RoomCategoryDTO;
import com.elephasvacation.tms.web.entity.RoomCategory;

import javax.persistence.EntityManager;
import java.util.List;

public class RoomCategoryBOImpl implements RoomCategoryBO {

    private final RoomCategoryDAO roomCategoryDAO = DAOFactory.getInstance().getDAO(DAOTypes.ROOM_CATEGORY);
    private final RoomCategoryDTOMapper mapper = RoomCategoryDTOMapper.instance;
    private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;

        /* Set Entity Manager to the DAL. */
        this.roomCategoryDAO.setEntityManager(this.entityManager);
    }

    @Override
    public Integer createRoomCategoryDTO(RoomCategoryDTO roomCategoryDTO) throws Exception {
        this.entityManager.getTransaction().begin();

        /* convert DTO to entity. */
        RoomCategory roomCategory = this.mapper.getRoomCategory(roomCategoryDTO);

        /* save. */
        Integer generatedRoomCategoryID = this.roomCategoryDAO.save(roomCategory).getId();

        this.entityManager.getTransaction().commit();
        return generatedRoomCategoryID;
    }

    @Override
    public void updateRoomCategoryDTO(RoomCategoryDTO roomCategoryDTO) throws Exception {
        this.entityManager.getTransaction().begin();
        /* update. */
        this.roomCategoryDAO.update(this.mapper.getRoomCategory(roomCategoryDTO));
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void deleteRoomCategoryDTO(Integer roomCategoryID) throws Exception {
        this.entityManager.getTransaction().begin();
        this.roomCategoryDAO.delete(roomCategoryID);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public RoomCategoryDTO getRoomCategoryByID(Integer roomCategoryID) throws Exception {
        this.entityManager.getTransaction().begin();

        /* get room category by ID. */
        RoomCategory roomCategory = this.roomCategoryDAO.get(roomCategoryID);

        /* convert entity to DTO. */
        RoomCategoryDTO roomCategoryDTO = this.mapper.getRoomCategoryDTO(roomCategory);

        this.entityManager.getTransaction().commit();
        return roomCategoryDTO;
    }

    @Override
    public List<RoomCategoryDTO> getAllRoomCategories() throws Exception {
        this.entityManager.getTransaction().begin();

        /* get all Room Categories. */
        List<RoomCategory> roomCategoryList = this.roomCategoryDAO.getAll();

        /* convert roomCategoryList to DTOList. */
        List<RoomCategoryDTO> roomCategoryDTOList = this.mapper.getRoomCategoryDTOs(roomCategoryList);
        this.entityManager.getTransaction().commit();
        return roomCategoryDTOList;
    }
}
