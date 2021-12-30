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
 * @date : 13/07/2021
 */
package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.RoomTypeBO;
import com.elephasvacation.tms.web.business.custom.util.mapper.RoomTypeDTOMapper;
import com.elephasvacation.tms.web.dal.custom.RoomTypeDAO;
import com.elephasvacation.tms.web.dto.RoomTypeDTO;
import com.elephasvacation.tms.web.entity.RoomType;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NoArgsConstructor
@Transactional
@Service
public class RoomTypeBOImpl implements RoomTypeBO {

    @Autowired
    private RoomTypeDAO roomTypeDAO;

    @Autowired
    private RoomTypeDTOMapper mapper;


    @Override
    public Integer createRoomTypeDTO(RoomTypeDTO roomTypeDTO) throws Exception {

        /* convert DTO to entity. */
        RoomType roomType = this.mapper.getRoomType(roomTypeDTO);

        /* save. */
        return this.roomTypeDAO.save(roomType).getId();
    }

    @Override
    public void updateRoomTypeDTO(RoomTypeDTO roomTypeDTO) throws Exception {
        /* update. */
        this.roomTypeDAO.update(this.mapper.getRoomType(roomTypeDTO));
    }

    @Override
    public void deleteRoomTypeDTO(Integer roomTypeID) throws Exception {
        this.roomTypeDAO.delete(roomTypeID);
    }

    @Transactional(readOnly = true)
    @Override
    public RoomTypeDTO getRoomTypeByID(Integer roomTypeID) throws Exception {
        /* get room type by ID. */
        RoomType roomType = this.roomTypeDAO.get(roomTypeID);

        /* convert entity to DTO. */
        return this.mapper.getRoomTypeDTO(roomType);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoomTypeDTO> getAllRoomTypes() throws Exception {
        /* get all room types. */
        List<RoomType> roomTypeList = this.roomTypeDAO.getAll();

        /* convert roomTypeList to DTOList. */
        return this.mapper.getRoomTypeDTOs(roomTypeList);
    }
}
