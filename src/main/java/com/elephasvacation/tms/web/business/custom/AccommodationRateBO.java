package com.elephasvacation.tms.web.business.custom;

import com.elephasvacation.tms.web.business.SuperBO;
import com.elephasvacation.tms.web.dto.AccommodationRateDTO;

import java.sql.SQLException;
import java.util.List;

public interface AccommodationRateBO extends SuperBO {

    Integer createAccommodationRate(AccommodationRateDTO accommodationRateDTO) throws Exception;

    void updateAccommodationRate(AccommodationRateDTO accommodationRateDTO) throws Exception;

    void deleteAccommodationRate(Integer accommodationRateDTOID) throws Exception;

    AccommodationRateDTO getAccommodationRateByID(Integer accommodationRateDTOID) throws Exception;

    List<AccommodationRateDTO> getAllAccommodationRates(Integer accommodationRateDTOID) throws Exception;

    List<AccommodationRateDTO> getAllAccommodationRatesForAccommodationPackage(Integer accommodationPackageID) throws SQLException;
}
