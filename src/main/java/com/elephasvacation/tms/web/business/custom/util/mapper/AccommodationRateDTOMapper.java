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
package com.elephasvacation.tms.web.business.custom.util.mapper;

import com.elephasvacation.tms.web.dto.AccommodationRateDTO;
import com.elephasvacation.tms.web.dto.AccommodationRateDTOId;
import com.elephasvacation.tms.web.entity.AccommodationRate;
import com.elephasvacation.tms.web.entity.AccommodationRateId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AccommodationRateDTOMapper {

    @Mappings(value = {
            @Mapping(source = "id.pkgRoomTypeId", target = "accommodationRateId.pkgRoomTypeId"),
            @Mapping(source = "id.pkgRoomCategoryId", target = "accommodationRateId.pkgRoomCategoryId"),
            @Mapping(source = "id.pkgMealPlanId", target = "accommodationRateId.pkgMealPlanId"),
            @Mapping(source = "id.accommodationPackageId", target = "accommodationRateId.accommodationPackageId"),
    })
    public abstract AccommodationRateDTO getAccommodationRateDTO(AccommodationRate accommodationRate);

    @Mappings(value = {
            @Mapping(source = "accommodationRateId.pkgRoomTypeId", target = "id.pkgRoomTypeId"),
            @Mapping(source = "accommodationRateId.pkgRoomCategoryId", target = "id.pkgRoomCategoryId"),
            @Mapping(source = "accommodationRateId.pkgMealPlanId", target = "id.pkgMealPlanId"),
            @Mapping(source = "accommodationRateId.accommodationPackageId", target = "id.accommodationPackageId"),
    })
    public abstract AccommodationRate getAccommodationRate(AccommodationRateDTO accommodationRateDTO);

    public abstract List<AccommodationRateDTO> getAccommodationRateDTOList(List<AccommodationRate> accommodationRateList);

    public abstract AccommodationRateDTOId getAccommodationRateDTOId(AccommodationRateId accommodationPackageId);

    public abstract AccommodationRateId getAccommodationRateId(AccommodationRateDTOId accommodationRateDTOId);
}
