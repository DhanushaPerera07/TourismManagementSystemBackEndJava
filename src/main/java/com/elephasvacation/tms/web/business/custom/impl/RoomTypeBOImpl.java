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
 * @date : 13/07/2021
 */
package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.RoomTypeBO;
import com.elephasvacation.tms.web.business.custom.util.EntityDTOMapper;
import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.RoomTypeDAO;
import com.elephasvacation.tms.web.dto.RoomTypeDTO;

import java.sql.Connection;
import java.util.List;

public class RoomTypeBOImpl implements RoomTypeBO {

    private RoomTypeDAO roomTypeDAO = DAOFactory.getInstance().getDAO(DAOTypes.ROOM_TYPE);
    private EntityDTOMapper mapper = EntityDTOMapper.instance;

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.roomTypeDAO.setConnection(connection);
    }

    @Override
    public Integer createRoomTypeDTO(RoomTypeDTO roomTypeDTO) throws Exception {
        return this.roomTypeDAO.save(this.mapper.getRoomType(roomTypeDTO));
    }

    @Override
    public boolean updateRoomTypeDTO(RoomTypeDTO roomTypeDTO) throws Exception {
        return this.roomTypeDAO.update(this.mapper.getRoomType(roomTypeDTO));
    }

    @Override
    public boolean deleteRoomTypeDTO(Integer roomTypeID) throws Exception {
        return this.roomTypeDAO.delete(roomTypeID);
    }

    @Override
    public RoomTypeDTO getRoomTypeByID(Integer roomTypeID) throws Exception {
        return this.mapper.getRoomTypeDTO(this.roomTypeDAO.get(roomTypeID));
    }

    @Override
    public List<RoomTypeDTO> getAllRoomTypes() throws Exception {
        return this.mapper.getRoomTypeDTOs(this.roomTypeDAO.getAll());
    }
}
