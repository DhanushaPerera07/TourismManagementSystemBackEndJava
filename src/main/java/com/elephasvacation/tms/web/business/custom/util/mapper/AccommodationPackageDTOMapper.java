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

import com.elephasvacation.tms.web.AppInitializer;
import com.elephasvacation.tms.web.dto.AccommodationPackageDTO;
import com.elephasvacation.tms.web.entity.Accommodation;
import com.elephasvacation.tms.web.entity.AccommodationPackage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccommodationPackageDTOMapper {

    /*  -------------------- Accommodation Package  -------------------- */
    @Mapping(source = ".", target = "accommodation", qualifiedByName = "toAccommodationQBN")
    AccommodationPackage getAccommodationPackage(AccommodationPackageDTO accommodationPackageDTO);

    @Named(value = "toAccommodationQBN")
    default Accommodation toAccommodation(AccommodationPackageDTO accommodationPackageDTO) {
        Accommodation accommodation = AppInitializer.getContext().getBean(Accommodation.class);
        accommodation.setId(accommodationPackageDTO.getAccommodationId());
        return accommodation;
    }

    @Mapping(target = "accommodationId", source = ".", qualifiedByName = "toAccommodationPackageId")
    AccommodationPackageDTO getAccommodationPackageDTO(AccommodationPackage accommodationPackage);

    @Named(value = "toAccommodationPackageId")
    default Integer toAccommodationId(AccommodationPackage accommodationPackage) {
        return accommodationPackage.getAccommodation().getId();
    }

    List<AccommodationPackageDTO> getAccommodationPackageDTOList(List<AccommodationPackage> accommodationPackageList);
}
