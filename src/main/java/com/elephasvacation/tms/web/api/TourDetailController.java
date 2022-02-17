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
import com.elephasvacation.tms.web.business.custom.TourDetailBO;
import com.elephasvacation.tms.web.commonconstant.API;
import com.elephasvacation.tms.web.dto.TourDetailDTO;
import com.elephasvacation.tms.web.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = API.HTTP_LOCALHOST_8080)
@RequestMapping("/api/v1/customers")
@RestController
public class TourDetailController {


    @Autowired
    private TourDetailBO tourDetailBO;


    @GetMapping(value = {"/{customerId}/tour-details"})
    public List<TourDetailDTO> getAllTourDetails(@PathVariable(name = "customerId") String customerIdStr) {
        System.out.println(this.getClass().getSimpleName() + ": CustomerID= " + customerIdStr);

        /* get the Integer ID. */
        Integer customerId = ApiUtil.getIntegerId(customerIdStr);

        return this.tourDetailBO.getAllTourDetailsByCustomerID(customerId);
    }

    @GetMapping(value = "/{customerId}/tour-details/{id}")
    public TourDetailDTO getTourDetailByID(@PathVariable(name = "customerId") String customerIdStr, @PathVariable String id) {
        System.out.println(this.getClass().getSimpleName() + ": CustomerID= " + customerIdStr + ", TourDetailID: " + id);

        /* get the Customer Integer ID. */
        Integer customerId = ApiUtil.getIntegerId(customerIdStr);
        /* get the TourDetail Integer ID. */
        Integer tourDetailId = ApiUtil.getIntegerId(id);

        return this.tourDetailBO.getTourDetailByIDAndCustomerID(customerId, tourDetailId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = {"/{customerId}/tour-details"})
    public Integer createTourDetail(@PathVariable(name = "customerId") String customerIdStr,
                                    @RequestBody TourDetailDTO tourDetailDTO) {
        System.out.println("API Layer: TourDetailDTO ---> " + tourDetailDTO);

        /* TODO: validation. */

        /* validation. */
        if (tourDetailDTO.getCustomerId() != null) throw new BadRequestException("Invalid field \"CustomerId\"");

        /* get the Customer Integer ID. */
        Integer customerId = ApiUtil.getIntegerId(customerIdStr);
        /* set Customer ID. */
        tourDetailDTO.setCustomerId(customerId);

        /* Save the record. */
        return this.tourDetailBO.createTourDetail(tourDetailDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{customerId}/tour-details/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateTourDetail(@PathVariable(name = "customerId") String customerIdStr,
                                 @PathVariable String id,
                                 @RequestBody TourDetailDTO tourDetailDTO) {

        /* TODO: validation. */

        /* validation. */
        if (tourDetailDTO.getCustomerId() != null) throw new BadRequestException("Invalid field \"CustomerId\"");

        /* get the Customer Integer ID. */
        Integer customerId = ApiUtil.getIntegerId(customerIdStr);

        /* get the TourDetail Integer ID. */
        Integer tourDetailId = ApiUtil.getIntegerId(id);

        /* validation. */
        if (tourDetailDTO.getId() != null) throw new BadRequestException("Invalid field \"tourDetailId\"");

        /* set tourDetailId. */
        tourDetailDTO.setId(tourDetailId);

        /* set customerId. */
        tourDetailDTO.setCustomerId(customerId);

        /* update. */
        this.tourDetailBO.updateTourDetail(tourDetailDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{customerId}/tour-details/{id}")
    public void deleteTourDetail(@PathVariable(name = "customerId") String customerIdStr, @PathVariable String id) {
        System.out.println("CustomerId: " + customerIdStr);
        System.out.println("TourDetailId: " + id);

        /* get the Customer Integer ID. */
        Integer customerId = ApiUtil.getIntegerId(customerIdStr);

        /* get the TourDetail Integer ID. */
        Integer tourDetailId = ApiUtil.getIntegerId(id);

        /* TODO: validation. */

        this.tourDetailBO.deleteTourDetail(tourDetailId);

    }

}
