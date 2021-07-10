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
 * @date : 04/07/2021
 */
package com.elephasvacation.tms.web.business.custom.util;

import com.elephasvacation.tms.web.dto.AccommodationDTO;
import com.elephasvacation.tms.web.dto.CustomerDTO;
import com.elephasvacation.tms.web.dto.EmployeeDTO;
import com.elephasvacation.tms.web.dto.TourDetailsDTO;
import com.elephasvacation.tms.web.entity.Accommodation;
import com.elephasvacation.tms.web.entity.Customer;
import com.elephasvacation.tms.web.entity.Employee;
import com.elephasvacation.tms.web.entity.TourDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EntityDTOMapper {
    EntityDTOMapper instance = Mappers.getMapper(EntityDTOMapper.class);

    /* -------------------- Employee. -------------------- */
    Employee getEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO getEmployeeDTO(Employee employee);

    List<EmployeeDTO> getEmployeeDTOs(List<Employee> employeeList);

    /*  -------------------- Customer  -------------------- */
    Customer getCustomer(CustomerDTO customerDTO);

    CustomerDTO getCustomerDTO(Customer customer);

    List<CustomerDTO> getCustomerDTOs(List<Customer> customerList);

    /*  -------------------- Tour-Details.  -------------------- */
    TourDetail getTourDetail(TourDetailsDTO tourDetailsDTO);

    TourDetailsDTO getTourDetailsDTO(TourDetail tourDetails);

    List<TourDetailsDTO> getTourDetailDTOs(List<TourDetail> tourDetailList);

    /*  -------------------- Accommodation  -------------------- */
    Accommodation getAccommodation(AccommodationDTO accommodationDTO);

    AccommodationDTO getAccommodationDTO(Accommodation accommodation);

    List<AccommodationDTO> getAccommodationDTOs(List<Accommodation> accommodationList);

}
