package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.AccommodationRateBO;
import com.elephasvacation.tms.web.business.custom.util.mapper.AccommodationRateDTOMapper;
import com.elephasvacation.tms.web.business.custom.util.transaction.TMSTransaction;
import com.elephasvacation.tms.web.dal.custom.AccommodationRateDAO;
import com.elephasvacation.tms.web.dto.AccommodationRateDTO;
import com.elephasvacation.tms.web.dto.AccommodationRateDTOId;
import com.elephasvacation.tms.web.entity.AccommodationRate;
import com.elephasvacation.tms.web.entity.AccommodationRateId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

@Component
public class AccommodationRateBOImpl implements AccommodationRateBO {

    @Autowired
    private AccommodationRateDAO accommodationRateDAO;

    @Autowired
    private AccommodationRateDTOMapper mapper;

    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;

        /* Set Entity Manager to the DAL. */
        this.accommodationRateDAO.setEntityManager(this.entityManager);
    }

    @Override
    @TMSTransaction
    public AccommodationRateDTOId createAccommodationRate(AccommodationRateDTO accommodationRateDTO) throws Exception {

        /* convert AccommodationRateDTO to entity. */
        AccommodationRate accommodationRate = this.mapper.getAccommodationRate(accommodationRateDTO);

        /* save AccommodationRate and get the accommodationRateId. */
        AccommodationRateId accommodationRateId =
                this.accommodationRateDAO.save(accommodationRate).getId();

        /* convert accommodationRateId to accommodationRateDTOId */
        AccommodationRateDTOId accommodationRateDTOId = this.mapper.getAccommodationRateDTOId(accommodationRateId);

        return accommodationRateDTOId;
    }

    @Override
    @TMSTransaction
    public void updateAccommodationRate(AccommodationRateDTO accommodationRateDTO) throws Exception {
        /* update */
        this.accommodationRateDAO.update(this.mapper.getAccommodationRate(accommodationRateDTO));
    }

    @Override
    @TMSTransaction
    public void deleteAccommodationRate(AccommodationRateDTOId accommodationRateDTOId) throws Exception {

        /* convert accommodationRateDTOId to entity. */
        AccommodationRateId accommodationRateId = this.mapper.getAccommodationRateId(accommodationRateDTOId);

        /* delete AccommodationRate By ID. */
        this.accommodationRateDAO.delete(accommodationRateId);
    }

    @Override
    @TMSTransaction
    public AccommodationRateDTO getAccommodationRateByID(AccommodationRateDTOId accommodationRateDTOId)
            throws Exception {

        /* convert accommodationRateDTOId to entity. */
        AccommodationRateId accommodationRateId = this.mapper.getAccommodationRateId(accommodationRateDTOId);

        /* get AccommodationRate by ID. */
        AccommodationRate accommodationRate = this.accommodationRateDAO.get(accommodationRateId);

        /* convert AccommodationRate to AccommodationRateDTO. */
        AccommodationRateDTO accommodationRateDTO = this.mapper.getAccommodationRateDTO(accommodationRate);
        return accommodationRateDTO;
    }

    @Override
    @TMSTransaction
    public List<AccommodationRateDTO> getAllAccommodationRates() throws Exception {

        /* get all Accommodation Rates. */
        List<AccommodationRate> allAccommodationRateList = this.accommodationRateDAO.getAll();

        /* convert allAccommodationRateList to DTOList. */
        List<AccommodationRateDTO> accommodationRateDTOList = this.mapper.
                getAccommodationRateDTOList(allAccommodationRateList);

        return accommodationRateDTOList;
    }

    /**
     * Get all the accommodation rates for an accommodation PACKAGE.
     *
     * @return List<AccommodationRateDTO> All accommodation rates for a given Accommodation Package.
     */
    @Override
    @TMSTransaction
    public List<AccommodationRateDTO> getAllAccommodationRatesForAccommodationPackage(Integer accommodationPackageID)
            throws SQLException {

        /* get all accommodation rates by Accommodation Package ID. */
        List<AccommodationRate> allAccommodationRatesByAccommodationPackageID = this.accommodationRateDAO.
                getAllAccommodationRatesByAccommodationPackageID(accommodationPackageID);

        /* convert allAccommodationRatesByAccommodationPackageID to DTOList. */
        List<AccommodationRateDTO> accommodationRateDTOList = this.mapper.
                getAccommodationRateDTOList(allAccommodationRatesByAccommodationPackageID);

        return accommodationRateDTOList;
    }
}
