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
 * @date : 16/07/2021
 */
package com.elephasvacation.tms.web.api.roomCategory;

import com.elephasvacation.tms.web.business.BOFactory;
import com.elephasvacation.tms.web.business.BOTypes;
import com.elephasvacation.tms.web.business.custom.RoomCategoryBO;
import com.elephasvacation.tms.web.dto.RoomCategoryDTO;

import java.sql.Connection;
import java.util.List;

public class RoomCategoryAPIImpl implements RoomCategoryAPI {

    private RoomCategoryBO roomCategoryBO = BOFactory.getInstance()
            .getBO(BOTypes.ROOM_CATEGORY);

    @Override
    public void setConnection(Connection connection) throws Exception {
        this.roomCategoryBO.setConnection(connection);
    }

    @Override
    public Integer createRoomCategoryDTO(RoomCategoryDTO roomCategoryDTO) throws Exception {
        return this.roomCategoryBO.createRoomCategoryDTO(roomCategoryDTO);
    }

    @Override
    public boolean updateRoomCategoryDTO(RoomCategoryDTO roomCategoryDTO) throws Exception {
        return this.roomCategoryBO.updateRoomCategoryDTO(roomCategoryDTO);
    }

    @Override
    public boolean deleteRoomCategoryDTO(Integer roomCategoryID) throws Exception {
        return this.roomCategoryBO.deleteRoomCategoryDTO(roomCategoryID);
    }

    @Override
    public RoomCategoryDTO getRoomCategoryByID(Integer roomCategoryID) throws Exception {
        return this.roomCategoryBO.getRoomCategoryByID(roomCategoryID);
    }

    @Override
    public List<RoomCategoryDTO> getAllRoomCategories() throws Exception {
        return this.roomCategoryBO.getAllRoomCategories();
    }
}
