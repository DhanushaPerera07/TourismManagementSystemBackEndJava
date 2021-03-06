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

import com.elephasvacation.tms.web.dto.AccommodationPackageRoomTypeDTO;
import com.elephasvacation.tms.web.entity.AccommodationPackageRoomType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccommodationPackageRoomTypeDTOMapper {
    AccommodationPackageRoomTypeDTOMapper instance = Mappers.getMapper(AccommodationPackageRoomTypeDTOMapper.class);

    @Mappings(value = {
            @Mapping( source = "id.accommodationPackageId", target = "accommodationPackageId"),
            @Mapping( source = "id.roomTypeId", target = "roomTypeId")
    })
    AccommodationPackageRoomTypeDTO
    getAccommodationPackageRoomTypeDTO(AccommodationPackageRoomType accommodationPackageRoomType);

    @Mappings( value = {
            @Mapping( source = "accommodationPackageId", target = "id.accommodationPackageId"),
            @Mapping( source = "roomTypeId", target = "id.roomTypeId")
    })
    AccommodationPackageRoomType
    getAccommodationPackageRoomType(AccommodationPackageRoomTypeDTO accommodationPackageRoomTypeDTO);

    List<AccommodationPackageRoomTypeDTO>
    getAccommodationPackageRoomTypeDTOList(List<AccommodationPackageRoomType> accommodationPackageRoomTypeList);
}
