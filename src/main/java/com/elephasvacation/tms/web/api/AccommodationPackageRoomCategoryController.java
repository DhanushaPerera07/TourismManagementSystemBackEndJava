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
import com.elephasvacation.tms.web.business.custom.AccommodationPackageRoomCategoryBO;
import com.elephasvacation.tms.web.commonconstant.API;
import com.elephasvacation.tms.web.dto.AccommodationPackageRoomCategoryDTO;
import com.elephasvacation.tms.web.dto.PackageRoomCategoryDataDTO;
import com.elephasvacation.tms.web.dto.RoomCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = API.HTTP_LOCALHOST_8080)
@RequestMapping("/api/v1/accommodations")
@RestController
public class AccommodationPackageRoomCategoryController {

    @Autowired
    private AccommodationPackageRoomCategoryBO packageRoomCategoryBO;

    @Autowired
    private AccommodationPackageRoomCategoryDTO packageRoomCategoryDTO;

    @GetMapping(value = "/{accommodationId}/packages/{packageId}/room-categories")
    public List<RoomCategoryDTO> getAllMealPlansForAccommodationPackage(@PathVariable(name = "accommodationId") String accommodationIdStr,
                                                                        @PathVariable(name = "packageId") String packageIdStr) {

        /* get the Integer ID. */
        Integer accommodationId = ApiUtil.getIntegerId(accommodationIdStr);
        Integer packageId = ApiUtil.getIntegerId(packageIdStr);

        return this.packageRoomCategoryBO.getAllRoomCategoriesForAccommodationPackage(accommodationId, packageId);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(value = "/{accommodationId}/packages/{packageId}/room-categories")
    public void addMealPlan(@PathVariable(name = "accommodationId") String accommodationIdStr,
                            @PathVariable(name = "packageId") String packageIdStr,
                            @RequestBody PackageRoomCategoryDataDTO packageRoomCategoryDataDTO) {
        /* get the Integer ID. */
        Integer accommodationId = ApiUtil.getIntegerId(accommodationIdStr);
        Integer packageId = ApiUtil.getIntegerId(packageIdStr);

        /* set values. */
        packageRoomCategoryDTO.setAccommodationPackageId(packageId);
        packageRoomCategoryDTO.setRoomCategoryId(packageRoomCategoryDataDTO.getRoomCategoryId());

        this.packageRoomCategoryBO.addPackageRoomCategory(accommodationId, packageRoomCategoryDTO);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{accommodationId}/packages/{packageId}/room-categories/{roomCategoryId}")
    public void deleteMealPlan(@PathVariable(name = "accommodationId") String accommodationIdStr,
                               @PathVariable(name = "packageId") String packageIdStr,
                               @PathVariable(name = "roomCategoryId") String roomCategoryIdStr) {
        /* get the Integer ID. */
        Integer accommodationId = ApiUtil.getIntegerId(accommodationIdStr);
        Integer packageId = ApiUtil.getIntegerId(packageIdStr);
        Integer roomCategoryId = ApiUtil.getIntegerId(roomCategoryIdStr);

        packageRoomCategoryDTO.setAccommodationPackageId(packageId);
        packageRoomCategoryDTO.setRoomCategoryId(roomCategoryId);

        this.packageRoomCategoryBO.deletePackageRoomCategory(accommodationId, packageRoomCategoryDTO);
    }
}
