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
 * @date : 15/07/2021
 */
package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.RoomCategoryBO;
import com.elephasvacation.tms.web.business.custom.util.RoomCategoryDTOMapper;
import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.RoomCategoryDAO;
import com.elephasvacation.tms.web.dto.RoomCategoryDTO;

import javax.persistence.EntityManager;
import java.util.List;

public class RoomCategoryBOImpl implements RoomCategoryBO {

    private RoomCategoryDAO roomCategoryDAO = DAOFactory.getInstance().getDAO(DAOTypes.ROOM_CATEGORY);
    private RoomCategoryDTOMapper mapper = RoomCategoryDTOMapper.instance;
    private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;

        /* Set Entity Manager to the DAL. */
        this.roomCategoryDAO.setEntityManager(this.entityManager);
    }

    @Override
    public Integer createRoomCategoryDTO(RoomCategoryDTO roomCategoryDTO) throws Exception {
        return this.roomCategoryDAO.save(this.mapper.getRoomCategory(roomCategoryDTO));
    }

    @Override
    public void updateRoomCategoryDTO(RoomCategoryDTO roomCategoryDTO) throws Exception {
        this.roomCategoryDAO.update(this.mapper.getRoomCategory(roomCategoryDTO));
    }

    @Override
    public void deleteRoomCategoryDTO(Integer roomCategoryID) throws Exception {
        this.roomCategoryDAO.delete(roomCategoryID);
    }

    @Override
    public RoomCategoryDTO getRoomCategoryByID(Integer roomCategoryID) throws Exception {
        return this.mapper.getRoomCategoryDTO(this.roomCategoryDAO.get(roomCategoryID));
    }

    @Override
    public List<RoomCategoryDTO> getAllRoomCategories() throws Exception {
        return this.mapper.getRoomCategoryDTOs(this.roomCategoryDAO.getAll());
    }
}
