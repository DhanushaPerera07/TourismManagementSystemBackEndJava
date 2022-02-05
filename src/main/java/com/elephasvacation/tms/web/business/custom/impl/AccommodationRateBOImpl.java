package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.AccommodationRateBO;
import com.elephasvacation.tms.web.business.custom.util.mapper.AccommodationRateDTOMapper;
import com.elephasvacation.tms.web.dal.AccommodationRateDAO;
import com.elephasvacation.tms.web.dto.AccommodationRateDTO;
import com.elephasvacation.tms.web.dto.AccommodationRateDTOId;
import com.elephasvacation.tms.web.entity.AccommodationRate;
import com.elephasvacation.tms.web.entity.AccommodationRateId;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NoArgsConstructor
@Transactional
@Service
public class AccommodationRateBOImpl implements AccommodationRateBO {

    @Autowired
    private AccommodationRateDAO accommodationRateDAO;

    @Autowired
    private AccommodationRateDTOMapper mapper;


    @Override
    public AccommodationRateDTOId createAccommodationRate(AccommodationRateDTO accommodationRateDTO) {

        /* convert AccommodationRateDTO to entity. */
        AccommodationRate accommodationRate = this.mapper.getAccommodationRate(accommodationRateDTO);

        /* save AccommodationRate and get the accommodationRateId. */
        AccommodationRateId accommodationRateId =
                this.accommodationRateDAO.save(accommodationRate).getId();

        /* convert accommodationRateId to accommodationRateDTOId */
        return this.mapper.getAccommodationRateDTOId(accommodationRateId);
    }

    @Override
    public void updateAccommodationRate(AccommodationRateDTO accommodationRateDTO) {
        /* update */
        this.accommodationRateDAO.save(this.mapper.getAccommodationRate(accommodationRateDTO));
    }

    @Override
    public void deleteAccommodationRate(AccommodationRateDTOId accommodationRateDTOId) {

        /* convert accommodationRateDTOId to entity. */
        AccommodationRateId accommodationRateId = this.mapper.getAccommodationRateId(accommodationRateDTOId);

        /* delete AccommodationRate By ID. */
        this.accommodationRateDAO.deleteById(accommodationRateId);
    }

    @Override
    @Transactional(readOnly = true)
    public AccommodationRateDTO getAccommodationRateByID(AccommodationRateDTOId accommodationRateDTOId) {

        /* convert accommodationRateDTOId to entity. */
        AccommodationRateId accommodationRateId = this.mapper.getAccommodationRateId(accommodationRateDTOId);

        /* get AccommodationRate by ID. */
        AccommodationRate accommodationRate = this.accommodationRateDAO.getById(accommodationRateId);

        /* convert AccommodationRate to AccommodationRateDTO. */
        return this.mapper.getAccommodationRateDTO(accommodationRate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccommodationRateDTO> getAllAccommodationRates() {

        /* get all Accommodation Rates. */
        List<AccommodationRate> allAccommodationRateList = this.accommodationRateDAO.findAll();

        /* convert allAccommodationRateList to DTOList. */
        return this.mapper.
                getAccommodationRateDTOList(allAccommodationRateList);
    }

    /**
     * Get all the accommodation rates for an accommodation PACKAGE.
     *
     * @return List<AccommodationRateDTO> All accommodation rates for a given Accommodation Package.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccommodationRateDTO> getAllAccommodationRatesForAccommodationPackage(Integer accommodationPackageID) {

        /* get all accommodation rates by Accommodation Package ID. */
        List<AccommodationRate> allAccommodationRatesByAccommodationPackageID = this.accommodationRateDAO.
                getAllAccommodationRatesByAccommodationPackageID(accommodationPackageID);

        /* convert allAccommodationRatesByAccommodationPackageID to DTOList. */
        return this.mapper.
                getAccommodationRateDTOList(allAccommodationRatesByAccommodationPackageID);
    }
}
