package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.AccommodationRateBO;
import com.elephasvacation.tms.web.business.custom.util.AccommodationRateDTOMapper;
import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.AccommodationRateDAO;
import com.elephasvacation.tms.web.dto.AccommodationRateDTO;
import com.elephasvacation.tms.web.dto.AccommodationRateDTOId;
import com.elephasvacation.tms.web.entity.AccommodationRateId;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

public class AccommodationRateBOImpl implements AccommodationRateBO {

    private final AccommodationRateDAO accommodationRateDAO = DAOFactory.getInstance().
            getDAO(DAOTypes.ACCOMMODATION_RATE);
    AccommodationRateDTOMapper mapper = AccommodationRateDTOMapper.instance;
    private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;

        /* Set Entity Manager to the DAL. */
        this.accommodationRateDAO.setEntityManager(this.entityManager);
    }

    @Override
    public AccommodationRateDTOId createAccommodationRate(AccommodationRateDTO accommodationRateDTO) throws Exception {
        AccommodationRateId accommodationRateId =
                this.accommodationRateDAO.save(this.mapper.getAccommodationRate(accommodationRateDTO)).getId();
        return this.mapper.getAccommodationRateDTOId(accommodationRateId);
    }

    @Override
    public void updateAccommodationRate(AccommodationRateDTO accommodationRateDTO) throws Exception {
        this.accommodationRateDAO.update(this.mapper.getAccommodationRate(accommodationRateDTO));
    }

    @Override
    public void deleteAccommodationRate(AccommodationRateDTOId accommodationRateDTOId) throws Exception {
        AccommodationRateId accommodationRateId = this.mapper.getAccommodationRateId(accommodationRateDTOId);
        this.accommodationRateDAO.delete(accommodationRateId);
    }

    @Override
    public AccommodationRateDTO getAccommodationRateByID(AccommodationRateDTOId accommodationRateDTOId)
            throws Exception {
        AccommodationRateId accommodationRateId = this.mapper.getAccommodationRateId(accommodationRateDTOId);
        return this.mapper.getAccommodationRateDTO(this.accommodationRateDAO.get(accommodationRateId));
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
        return this.mapper.
                getAccommodationRateDTOList(this.accommodationRateDAO.
                        getAllAccommodationRatesByAccommodationPackageID(accommodationPackageID));
    }
}
