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
@date : 18/07/2021
*/
package com.elephasvacation.tms.web.api;

import com.elephasvacation.tms.web.api.util.ApiUtil;
import com.elephasvacation.tms.web.business.custom.MealPlanBO;
import com.elephasvacation.tms.web.commonconstant.API;
import com.elephasvacation.tms.web.dto.MealPlanDTO;
import com.elephasvacation.tms.web.exception.IdFormatException;
import com.elephasvacation.tms.web.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = API.HTTP_LOCALHOST_8080)
@RequestMapping("/api/v1/meal-plans")
@RestController
public class MealPlanController {

    @Autowired
    private MealPlanBO mealPlanBO;

    /**
     * Get all MealPlans list.
     *
     * @return List<MealPlanDTO> mealPlansList.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealPlanDTO> getAllMealPlans() {
        return this.mealPlanBO.getAllMealPlans();
    }

    /**
     * Get MealPlan by MealPlan ID.
     *
     * @return MealPlanDTO MealPlan object.
     * @throws IdFormatException       if the ID is not an Integer.
     * @throws RecordNotFoundException if matching MealPlan record not found,
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            value = "/{id}")
    public MealPlanDTO getMealPlanByID(@PathVariable(name = "id") String id) {
        System.out.println("MealPlanID: " + id);

        Integer mealPlanID = ApiUtil.getIntegerId(id);

        MealPlanDTO mealPlanDTO = this.mealPlanBO.getMealPlanByID(mealPlanID);
        System.out.println("MealPlan Result: " + mealPlanDTO);

        /* If MealPlan not found. */
        if (mealPlanDTO == null) throw new RecordNotFoundException();
        return mealPlanDTO;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Integer saveMealPlan(@RequestBody MealPlanDTO mealPlanDTO) {
        System.out.println("API Layer: MealPlanDTO ---> " + mealPlanDTO);
        return this.mealPlanBO.createMealPlan(mealPlanDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateMealPlan(@PathVariable String id,
                               @RequestBody MealPlanDTO mealPlanDTO) {
        Integer mealPlanID = ApiUtil.getIntegerId(id);

        /* TODO: MealPlan - update validation logic. */
        if (mealPlanID.equals(mealPlanDTO.getId())) {
            mealPlanDTO.setId(mealPlanID);
            this.mealPlanBO.updateMealPlan(mealPlanDTO);
        } else {
            /* URL param mealPlanID and mealPlanObject's mealPlanID mismatched.
            Therefore, Let's throw an error.*/
            /* TODO: handle error. */
            throw new RuntimeException();
        }

    }

    /**
     * Delete mealPlan by MealPlanID.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteMealPlan(@PathVariable String id) {
        Integer mealPlanID = ApiUtil.getIntegerId(id);
        System.out.println("MealPlan ID: " + mealPlanID);
        this.mealPlanBO.deleteMealPlan(mealPlanID);
    }
}
