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
import com.elephasvacation.tms.web.business.custom.util.mapper.RoomCategoryDTOMapper;
import com.elephasvacation.tms.web.dal.RoomCategoryDAO;
import com.elephasvacation.tms.web.dto.RoomCategoryDTO;
import com.elephasvacation.tms.web.entity.RoomCategory;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NoArgsConstructor
@Transactional
@Service
public class RoomCategoryBOImpl implements RoomCategoryBO {

    @Autowired
    private RoomCategoryDAO roomCategoryDAO;

    @Autowired
    private RoomCategoryDTOMapper mapper;

    @Override
    public Integer createRoomCategoryDTO(RoomCategoryDTO roomCategoryDTO) {

        /* convert DTO to entity. */
        RoomCategory roomCategory = this.mapper.getRoomCategory(roomCategoryDTO);

        /* save. */
        return this.roomCategoryDAO.save(roomCategory).getId();
    }

    @Override
    public void updateRoomCategoryDTO(RoomCategoryDTO roomCategoryDTO) {
        /* update. */
        this.roomCategoryDAO.save(this.mapper.getRoomCategory(roomCategoryDTO));
    }

    @Override
    public void deleteRoomCategoryDTO(Integer roomCategoryID) {
        this.roomCategoryDAO.deleteById(roomCategoryID);
    }

    @Transactional(readOnly = true)
    @Override
    public RoomCategoryDTO getRoomCategoryByID(Integer roomCategoryID) {

        /* get room category by ID. */
        RoomCategory roomCategory = this.roomCategoryDAO.getById(roomCategoryID);

        /* convert entity to DTO. */
        return this.mapper.getRoomCategoryDTO(roomCategory);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoomCategoryDTO> getAllRoomCategories() {

        /* get all Room Categories. */
        List<RoomCategory> roomCategoryList = this.roomCategoryDAO.findAll();

        /* convert roomCategoryList to DTOList. */
        return this.mapper.getRoomCategoryDTOs(roomCategoryList);
    }
}
