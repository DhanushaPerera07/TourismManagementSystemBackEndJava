package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.AccommodationRateBO;
import com.elephasvacation.tms.web.business.custom.util.AccommodationRateDTOMapper;
import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.AccommodationRateDAO;
import com.elephasvacation.tms.web.dto.AccommodationRateDTO;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

public class AccommodationRateBOImpl implements AccommodationRateBO {

    AccommodationRateDTOMapper mapper = AccommodationRateDTOMapper.instance;
    private AccommodationRateDAO accommodationRateDAO = DAOFactory.getInstance()
            .getDAO(DAOTypes.ACCOMMODATION_RATE);
    private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;

        /* Set Entity Manager to the DAL. */
        this.accommodationRateDAO.setEntityManager(this.entityManager);
    }

    @Override
    public Integer createAccommodationRate(AccommodationRateDTO accommodationRateDTO) throws Exception {
        return this.accommodationRateDAO.save(this.mapper.getAccommodationRate(accommodationRateDTO));
    }

    @Override
    public void updateAccommodationRate(AccommodationRateDTO accommodationRateDTO) throws Exception {
        this.accommodationRateDAO.update(this.mapper.getAccommodationRate(accommodationRateDTO));
    }

    @Override
    public void deleteAccommodationRate(Integer accommodationRateDTOID) throws Exception {
        this.accommodationRateDAO.delete(accommodationRateDTOID);
    }

    @Override
    public AccommodationRateDTO getAccommodationRateByID(Integer accommodationRateDTOID) throws Exception {
        return this.mapper.getAccommodationRateDTO(this.accommodationRateDAO.get(accommodationRateDTOID));
    }

    @Override
    public List<AccommodationRateDTO> getAllAccommodationRates(Integer accommodationRateDTOID) throws Exception {
        return this.mapper.getAccommodationRateDTOList(this.accommodationRateDAO.getAll());
    }

    /**
     * Get all the accommodation rates for t accommodation PACKAGE.
     */
    @Override
    public List<AccommodationRateDTO> getAllAccommodationRatesForAccommodationPackage(Integer accommodationPackageID)
            throws SQLException {
        return this.mapper
                .getAccommodationRateDTOList(this.accommodationRateDAO
                        .getAllAccommodationRatesByAccommodationPackageID(accommodationPackageID));
    }
}
