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

import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.RoomTypeDAO;
import com.elephasvacation.tms.web.entity.RoomType;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.Assert.*;

public class RoomTypeDAOImplTest {

    EntityManagerFactory emf = null;
    EntityManager em = null;

    RoomTypeDAO roomTypeDAO = DAOFactory.getInstance().getDAO(DAOTypes.ROOM_TYPE);

//    @Before
//    public void setEntityManager() {
//        try {
//            this.emf = HibernateUtil.getEntityManagerFactory();
//            this.em = emf.createEntityManager();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @After
//    public void closeEntityManager() {
//        if (em != null) {
//            em.close();
//            emf.close();
//        }
//    }

    @Test
    public void save() {
        this.em.getTransaction().begin();
        this.roomTypeDAO.setEntityManager(this.em);

        RoomType standardRoomType = new RoomType("Standard");
        try {
            this.roomTypeDAO.save(standardRoomType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.em.getTransaction().commit();
    }

    @Test
    public void update() {
        String std = "STD";
        this.em.getTransaction().begin();
        this.roomTypeDAO.setEntityManager(this.em);

        try {
            RoomType roomType = this.roomTypeDAO.get(1);
            assertNotNull(roomType);
            roomType.setRoomType(std);
            this.roomTypeDAO.update(roomType);

            RoomType roomTypeAfter = this.roomTypeDAO.get(1);
            assertEquals(std, roomTypeAfter.getRoomType());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.em.getTransaction().commit();
    }

    @Test
    public void delete() {
        this.em.getTransaction().begin();
        this.roomTypeDAO.setEntityManager(this.em);

        try {
            RoomType roomType = this.roomTypeDAO.get(1);
            assertNotNull(roomType);
            this.roomTypeDAO.delete(1);

            RoomType roomTypeAfter = this.roomTypeDAO.get(1);
            assertNull(roomTypeAfter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.em.getTransaction().commit();
    }
}
