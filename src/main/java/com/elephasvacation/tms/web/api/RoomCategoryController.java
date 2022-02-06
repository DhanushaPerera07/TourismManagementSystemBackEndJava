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
@date : 16/07/2021
*/
package com.elephasvacation.tms.web.api;


import com.elephasvacation.tms.web.api.util.ApiUtil;
import com.elephasvacation.tms.web.business.custom.RoomCategoryBO;
import com.elephasvacation.tms.web.commonconstant.API;
import com.elephasvacation.tms.web.dto.RoomCategoryDTO;
import com.elephasvacation.tms.web.exception.IdFormatException;
import com.elephasvacation.tms.web.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = API.HTTP_LOCALHOST_8080)
@RequestMapping("/api/v1/room-categories")
@RestController
public class RoomCategoryController {
    @Autowired
    private RoomCategoryBO roomCategoryBO;

    /**
     * Get all roomCategories list.
     *
     * @return List<RoomCategoryDTO> roomCategoriesList.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RoomCategoryDTO> getAllRoomCategories() {
        return this.roomCategoryBO.getAllRoomCategories();
    }

    /**
     * Get roomCategory by roomCategory ID.
     *
     * @return RoomCategoryDTO roomCategory object.
     * @throws IdFormatException       if the ID is not an Integer.
     * @throws RecordNotFoundException if matching roomCategory record not found,
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            value = "/{id}")
    public RoomCategoryDTO getRoomCategoryByID(@PathVariable(name = "id") String id) {
        System.out.println("RoomCategoryID: " + id);

        Integer roomCategoryID = ApiUtil.getIntegerId(id);

        RoomCategoryDTO roomCategoryDTO = this.roomCategoryBO.getRoomCategoryByID(roomCategoryID);
        System.out.println("RoomCategory Result: " + roomCategoryDTO);

        /* If RoomCategory not found. */
        if (roomCategoryDTO == null) throw new RecordNotFoundException();
        return roomCategoryDTO;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Integer saveRoomCategory(@RequestBody RoomCategoryDTO roomCategoryDTO) {
        System.out.println("API Layer: RoomCategoryDTO ---> " + roomCategoryDTO);
        return this.roomCategoryBO.createRoomCategory(roomCategoryDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateRoomCategory(@PathVariable String id,
                                   @RequestBody RoomCategoryDTO roomCategoryDTO) {
        Integer roomCategoryID = ApiUtil.getIntegerId(id);

        /* TODO: RoomCategory - update validation logic. */
        if (roomCategoryDTO.getId() == roomCategoryID) {
            roomCategoryDTO.setId(roomCategoryID);
            this.roomCategoryBO.updateRoomCategory(roomCategoryDTO);
        } else {
            /* URL param roomCategoryID and roomCategoryObject's roomCategoryID mismatched.
            Therefore, Let's throw an error.*/
            /* TODO: handle error. */
            throw new RuntimeException();
        }

    }

    /**
     * Delete roomCategory by RoomCategoryID.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteRoomCategory(@PathVariable String id) {
        Integer roomCategoryID = ApiUtil.getIntegerId(id);
        System.out.println("RoomCategory ID: " + roomCategoryID);
        this.roomCategoryBO.deleteRoomCategory(roomCategoryID);
    }

}
