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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
/*
 * @author : Dhanusha Perera
 * @date : 28/07/2021
 */
package com.elephasvacation.tms.web.business.custom;

import com.elephasvacation.tms.web.business.SuperBO;
import com.elephasvacation.tms.web.dto.AccommodationPackageDTO;

import java.sql.SQLException;
import java.util.List;

public interface AccommodationPackageBO extends SuperBO {

    Integer createAccommodationPackage(AccommodationPackageDTO accommodationPackageDTO) throws Exception;

    boolean updateAccommodationPackage(AccommodationPackageDTO accommodationPackageDTO) throws Exception;

    boolean deleteAccommodationPackage(Integer accommodationPackageID) throws Exception;

    AccommodationPackageDTO getAccommodationPackageByID(Integer accommodationPackageID) throws Exception;

    List<AccommodationPackageDTO> getAllAccommodationPackagesByAccommodationID(Integer accommodationID) throws SQLException;

    List<AccommodationPackageDTO> getAllAccommodationPackages() throws Exception;

}