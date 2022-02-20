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
import com.elephasvacation.tms.web.business.custom.AccommodationPackageMealPlanBO;
import com.elephasvacation.tms.web.commonconstant.API;
import com.elephasvacation.tms.web.dto.AccommodationPackageMealPlanDTO;
import com.elephasvacation.tms.web.dto.MealPlanDTO;
import com.elephasvacation.tms.web.dto.PackageMealPlanDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = API.HTTP_LOCALHOST_8080)
@RequestMapping("/api/v1/accommodations")
@RestController
public class AccommodationPackageMealPlanController {

    @Autowired
    private AccommodationPackageMealPlanBO packageMealPlanBO;

    @Autowired
    private AccommodationPackageMealPlanDTO packageMealPlanDTO;

    @GetMapping(value = "/{accommodationId}/packages/{packageId}/meal-plans")
    public List<MealPlanDTO> getAllMealPlansForAccommodationPackage(@PathVariable(name = "accommodationId") String accommodationIdStr,
                                                                    @PathVariable(name = "packageId") String packageIdStr) {

        /* get the Integer ID. */
        Integer accommodationId = ApiUtil.getIntegerId(accommodationIdStr);
        Integer packageId = ApiUtil.getIntegerId(packageIdStr);

        return this.packageMealPlanBO.getAllMealPlansForAccommodationPackage(accommodationId, packageId);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(value = "/{accommodationId}/packages/{packageId}/meal-plans")
    public void addMealPlan(@PathVariable(name = "accommodationId") String accommodationIdStr,
                            @PathVariable(name = "packageId") String packageIdStr,
                            @RequestBody PackageMealPlanDataDTO packageMealPlanDataDTO) {
        /* get the Integer ID. */
        Integer accommodationId = ApiUtil.getIntegerId(accommodationIdStr);
        Integer packageId = ApiUtil.getIntegerId(packageIdStr);

        packageMealPlanDTO.setAccommodationPackageId(packageId);
        packageMealPlanDTO.setMealPlanId(packageMealPlanDataDTO.getMealPlanId());

        this.packageMealPlanBO.addAccommodationPackageMealPlan(accommodationId, packageMealPlanDTO);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{accommodationId}/packages/{packageId}/meal-plans/{mealPlanId}")
    public void deleteMealPlan(@PathVariable(name = "accommodationId") String accommodationIdStr,
                               @PathVariable(name = "packageId") String packageIdStr,
                               @PathVariable(name = "mealPlanId") String mealPlanIdStr) {
        /* get the Integer ID. */
        Integer accommodationId = ApiUtil.getIntegerId(accommodationIdStr);
        Integer packageId = ApiUtil.getIntegerId(packageIdStr);
        Integer mealPlanId = ApiUtil.getIntegerId(mealPlanIdStr);

        packageMealPlanDTO.setAccommodationPackageId(packageId);
        packageMealPlanDTO.setMealPlanId(mealPlanId);

        this.packageMealPlanBO.deleteAccommodationPackageMealPlan(accommodationId, packageMealPlanDTO);
    }
}
