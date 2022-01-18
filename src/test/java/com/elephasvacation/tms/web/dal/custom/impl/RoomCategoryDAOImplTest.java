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
@author : Dhanusha Perera
@date : 13/07/2021
*/
package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.dal.custom.RoomCategoryDAO;
import com.elephasvacation.tms.web.entity.RoomCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class RoomCategoryDAOImplTest {

    @Autowired
    RoomCategoryDAO roomCategoryDAO;

    @Test
    public void save() {

        RoomCategory singleRoomCategory = new RoomCategory("Single");
        try {
            this.roomCategoryDAO.save(singleRoomCategory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void update() {
        String single = "SGL";

        try {
            RoomCategory roomCategory = this.roomCategoryDAO.get(1);
            assertNotNull(roomCategory);
            roomCategory.setRoomCategory(single);
            this.roomCategoryDAO.update(roomCategory);

            RoomCategory roomCategoryAfter = this.roomCategoryDAO.get(1);
            assertEquals(single, roomCategoryAfter.getRoomCategory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete() {

        try {
            RoomCategory roomCategory = this.roomCategoryDAO.get(1);
            assertNotNull(roomCategory);
            this.roomCategoryDAO.delete(1);

            RoomCategory roomCategoryAfter = this.roomCategoryDAO.get(1);
            assertNull(roomCategoryAfter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
