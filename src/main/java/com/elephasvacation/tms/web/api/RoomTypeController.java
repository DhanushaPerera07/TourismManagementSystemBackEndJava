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
package com.elephasvacation.tms.web.api;

import com.elephasvacation.tms.web.api.util.ApiUtil;
import com.elephasvacation.tms.web.business.custom.RoomTypeBO;
import com.elephasvacation.tms.web.commonconstant.API;
import com.elephasvacation.tms.web.dto.RoomTypeDTO;
import com.elephasvacation.tms.web.exception.IdFormatException;
import com.elephasvacation.tms.web.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = API.HTTP_LOCALHOST_8080)
@RequestMapping("/api/v1/room-types")
@RestController
public class RoomTypeController {

    @Autowired
    private RoomTypeBO roomTypeBO;

    /**
     * Get all roomTypes list.
     *
     * @return List<RoomTypeDTO> roomTypesList.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RoomTypeDTO> getAllRoomTypes() {
        return this.roomTypeBO.getAllRoomTypes();
    }

    /**
     * Get roomType by roomType ID.
     *
     * @return RoomTypeDTO roomType object.
     * @throws IdFormatException       if the ID is not an Integer.
     * @throws RecordNotFoundException if matching roomType record not found,
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            value = "/{id}")
    public RoomTypeDTO getRoomTypeByID(@PathVariable(name = "id") String id) {
        System.out.println("RoomTypeID: " + id);

        Integer roomTypeID = ApiUtil.getIntegerId(id);

        RoomTypeDTO roomTypeDTO = this.roomTypeBO.getRoomTypeByID(roomTypeID);
        System.out.println("RoomType Result: " + roomTypeDTO);

        /* If RoomType not found. */
        if (roomTypeDTO == null) throw new RecordNotFoundException();
        return roomTypeDTO;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Integer saveRoomType(@RequestBody RoomTypeDTO roomTypeDTO) {
        System.out.println("API Layer: RoomTypeDTO ---> " + roomTypeDTO);
        return this.roomTypeBO.createRoomType(roomTypeDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateRoomType(@PathVariable String id,
                               @RequestBody RoomTypeDTO roomTypeDTO) {
        Integer roomTypeID = ApiUtil.getIntegerId(id);

        /* TODO: RoomType - update validation logic. */
        if (roomTypeDTO.getId() == roomTypeID) {
            roomTypeDTO.setId(roomTypeID);
            this.roomTypeBO.updateRoomType(roomTypeDTO);
        } else {
            /* URL param roomTypeID and roomTypeObject's roomTypeID mismatched.
            Therefore, Let's throw an error.*/
            /* TODO: handle error. */
            throw new RuntimeException();
        }

    }

    /**
     * Delete roomType by RoomTypeID.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteRoomType(@PathVariable String id) {
        Integer roomTypeID = ApiUtil.getIntegerId(id);
        System.out.println("RoomType ID: " + roomTypeID);
        this.roomTypeBO.deleteRoomType(roomTypeID);
    }

}
