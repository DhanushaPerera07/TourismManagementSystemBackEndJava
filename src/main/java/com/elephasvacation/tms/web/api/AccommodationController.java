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
@date : 10/07/2021
*/
package com.elephasvacation.tms.web.api;

import com.elephasvacation.tms.web.api.util.ApiUtil;
import com.elephasvacation.tms.web.business.custom.AccommodationBO;
import com.elephasvacation.tms.web.commonconstant.API;
import com.elephasvacation.tms.web.dto.AccommodationDTO;
import com.elephasvacation.tms.web.exception.IdFormatException;
import com.elephasvacation.tms.web.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = API.HTTP_LOCALHOST_8080)
@RequestMapping("/api/v1/accommodations")
@RestController
public class AccommodationController {
    @Autowired
    private AccommodationBO accommodationBO;

    /**
     * Get all accommodations list.
     *
     * @return List<AccommodationDTO> accommodationsList.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AccommodationDTO> getAllAccommodations() {
        return this.accommodationBO.getAllAccommodations();
    }

    /**
     * Get accommodation by accommodation ID.
     *
     * @return AccommodationDTO accommodation object.
     * @throws IdFormatException       if the ID is not an Integer.
     * @throws RecordNotFoundException if matching accommodation record not found,
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            value = "/{id}")
    public AccommodationDTO getAccommodationByID(@PathVariable(name = "id") String id) {
        System.out.println("AccommodationID: " + id);

        Integer accommodationID = ApiUtil.getIntegerId(id);

        AccommodationDTO accommodationDTO = this.accommodationBO.getAccommodationByID(accommodationID);
        System.out.println("accommodationDTO Result: " + accommodationDTO);

        /* If no matching accommodation found. */
        if (accommodationDTO == null) throw new RecordNotFoundException();
        return accommodationDTO;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Integer saveAccommodation(@RequestBody AccommodationDTO accommodationDTO) {
        System.out.println("API Layer: AccommodationDTO ---> " + accommodationDTO);
        return this.accommodationBO.createAccommodation(accommodationDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateAccommodation(@PathVariable String id,
                                    @RequestBody AccommodationDTO accommodationDTO) {
        Integer accommodationID = ApiUtil.getIntegerId(id);

        /* TODO: Accommodation - update validation logic. */
        if (accommodationDTO.getId() == accommodationID) {
            accommodationDTO.setId(accommodationID);
            this.accommodationBO.updateAccommodation(accommodationDTO);
        } else {
            /* URL param accommodationID and accommodationObject's accommodationID mismatched.
            Therefore, Let's throw an error.*/
            /* TODO: handle error. */
            throw new RuntimeException();
        }

    }

    /**
     * Delete accommodation by Accommodation ID.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteAccommodation(@PathVariable String id) {
        Integer accommodationID = ApiUtil.getIntegerId(id);
        System.out.println("Accommodation ID: " + accommodationID);
        accommodationBO.deleteAccommodation(accommodationID);
    }
}
