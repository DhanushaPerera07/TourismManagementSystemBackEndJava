/*
 * MIT License
 *
 * Copyright (c) 2022 Dhanusha Perera
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
package com.elephasvacation.tms.web.api;

import com.elephasvacation.tms.web.api.util.ApiUtil;
import com.elephasvacation.tms.web.business.custom.AccommodationPackageBO;
import com.elephasvacation.tms.web.commonconstant.API;
import com.elephasvacation.tms.web.dto.AccommodationPackageDTO;
import com.elephasvacation.tms.web.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = API.HTTP_LOCALHOST_8080)
@RequestMapping("/api/v1/accommodations")
@RestController
public class AccommodationPackageController {

    @Autowired
    private AccommodationPackageBO accommodationPackageBO;

    @GetMapping(value = "/{accommodationId}/packages")
    public List<AccommodationPackageDTO> getAllAccommodationPackages(
            @PathVariable(name = "accommodationId") String accommodationIdStr) {
        System.out.println(this.getClass().getSimpleName() + ": AccommodationId= " + accommodationIdStr);

        /* get the Integer ID. */
        Integer accommodationId = ApiUtil.getIntegerId(accommodationIdStr);

        return this.accommodationPackageBO.getAllAccommodationPackagesByAccommodationID(accommodationId);
    }

    @GetMapping(value = "/{accommodationId}/packages/{id}")
    public AccommodationPackageDTO getAccommodationPackageByID(
            @PathVariable(name = "accommodationId") String accommodationIdStr,
            @PathVariable String id) {
        System.out.println(this.getClass().getSimpleName() + ": AccommodationId= " + accommodationIdStr);

        /* get the Integer ID. */
        Integer accommodationId = ApiUtil.getIntegerId(accommodationIdStr);

        /* get the Integer ID. */
        Integer packageId = ApiUtil.getIntegerId(id);

        return this.accommodationPackageBO.getAccommodationPackageByID(accommodationId, packageId);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = {"/{accommodationId}/packages"})
    public Integer createTourDetail(@PathVariable(name = "accommodationId") String accommodationIdStr,
                                    @RequestBody AccommodationPackageDTO accommodationPackageDTO) {
        System.out.println("API Layer: AccommodationPackageDTO ---> " + accommodationPackageDTO);

        /* TODO: validation. */

        /* validation. */
        if (accommodationPackageDTO.getAccommodationId() != null)
            throw new BadRequestException("Invalid field \"AccommodationId\"");

        /* get the Accommodation ID as an Integer. */
        Integer accommodationId = ApiUtil.getIntegerId(accommodationIdStr);
        /* set Accommodation ID. */
        accommodationPackageDTO.setAccommodationId(accommodationId);

        /* Save the record. */
        return this.accommodationPackageBO.createAccommodationPackage(accommodationPackageDTO);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{accommodationId}/packages/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateAccommodationPackage(@PathVariable(name = "accommodationId") String accommodationIdStr,
                                           @PathVariable String id,
                                           @RequestBody AccommodationPackageDTO accommodationPackageDTO) {

        /* TODO: validation. */

        /* validation. */
        if (accommodationPackageDTO.getAccommodationId() != null)
            throw new BadRequestException("Invalid field \"AccommodationId\"");

        /* get the Accommodation ID as an Integer. */
        Integer accommodationId = ApiUtil.getIntegerId(accommodationIdStr);

        /* get the AccommodationPackage ID as an Integer. */
        Integer accommodationPackageId = ApiUtil.getIntegerId(id);

        /* validation. */
        if (accommodationPackageDTO.getId() != null)
            throw new BadRequestException("Invalid field \"AccommodationPackageId\"");

        /* set accommodationPackage ID. */
        accommodationPackageDTO.setId(accommodationPackageId);

        /* set AccommodationId. */
        accommodationPackageDTO.setAccommodationId(accommodationId);

        /* update. */
        this.accommodationPackageBO.updateAccommodationPackage(accommodationPackageDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{accommodationId}/packages/{id}")
    public void deleteAccommodationPackage(@PathVariable(name = "accommodationId") String accommodationIdStr,
                                           @PathVariable String id) {
        System.out.println("AccommodationId: " + accommodationIdStr);
        System.out.println("AccommodationPackageID: " + id);

        /* get the accommodation ID as an Integer. */
        Integer accommodationId = ApiUtil.getIntegerId(accommodationIdStr);

        /* get the accommodationPackage ID as an Integer. */
        Integer accommodationPackageId = ApiUtil.getIntegerId(id);

        /* TODO: validation. */

        this.accommodationPackageBO.deleteAccommodationPackage(accommodationId, accommodationPackageId);

    }


}
