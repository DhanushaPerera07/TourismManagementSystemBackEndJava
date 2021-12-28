package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.AccommodationRateBO;
import com.elephasvacation.tms.web.business.custom.util.mapper.AccommodationRateDTOMapper;
import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.AccommodationRateDAO;
import com.elephasvacation.tms.web.dto.AccommodationRateDTO;
import com.elephasvacation.tms.web.dto.AccommodationRateDTOId;
import com.elephasvacation.tms.web.entity.AccommodationRate;
import com.elephasvacation.tms.web.entity.AccommodationRateId;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

@Component
public class AccommodationRateBOImpl implements AccommodationRateBO {

    private final AccommodationRateDAO accommodationRateDAO = DAOFactory.getInstance().
            getDAO(DAOTypes.ACCOMMODATION_RATE);
    private final AccommodationRateDTOMapper mapper = AccommodationRateDTOMapper.instance;
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
    public AccommodationRateDTOId createAccommodationRate(AccommodationRateDTO accommodationRateDTO) throws Exception {
        this.entityManager.getTransaction().begin();

        /* convert AccommodationRateDTO to entity. */
        AccommodationRate accommodationRate = this.mapper.getAccommodationRate(accommodationRateDTO);

        /* save AccommodationRate and get the accommodationRateId. */
        AccommodationRateId accommodationRateId =
                this.accommodationRateDAO.save(accommodationRate).getId();

        /* convert accommodationRateId to accommodationRateDTOId */
        AccommodationRateDTOId accommodationRateDTOId = this.mapper.getAccommodationRateDTOId(accommodationRateId);

        this.entityManager.getTransaction().commit();
        return accommodationRateDTOId;
    }

    @Override
    public void updateAccommodationRate(AccommodationRateDTO accommodationRateDTO) throws Exception {
        this.entityManager.getTransaction().begin();
        /* update */
        this.accommodationRateDAO.update(this.mapper.getAccommodationRate(accommodationRateDTO));
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void deleteAccommodationRate(AccommodationRateDTOId accommodationRateDTOId) throws Exception {
        this.entityManager.getTransaction().begin();

        /* convert accommodationRateDTOId to entity. */
        AccommodationRateId accommodationRateId = this.mapper.getAccommodationRateId(accommodationRateDTOId);

        /* delete AccommodationRate By ID. */
        this.accommodationRateDAO.delete(accommodationRateId);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public AccommodationRateDTO getAccommodationRateByID(AccommodationRateDTOId accommodationRateDTOId)
            throws Exception {
        this.entityManager.getTransaction().begin();

        /* convert accommodationRateDTOId to entity. */
        AccommodationRateId accommodationRateId = this.mapper.getAccommodationRateId(accommodationRateDTOId);

        /* get AccommodationRate by ID. */
        AccommodationRate accommodationRate = this.accommodationRateDAO.get(accommodationRateId);

        /* convert AccommodationRate to AccommodationRateDTO. */
        AccommodationRateDTO accommodationRateDTO = this.mapper.getAccommodationRateDTO(accommodationRate);
        this.entityManager.getTransaction().commit();
        return accommodationRateDTO;
    }

    @Override
    public List<AccommodationRateDTO> getAllAccommodationRates() throws Exception {
        this.entityManager.getTransaction().begin();

        /* get all Accommodation Rates. */
        List<AccommodationRate> allAccommodationRateList = this.accommodationRateDAO.getAll();

        /* convert allAccommodationRateList to DTOList. */
        List<AccommodationRateDTO> accommodationRateDTOList = this.mapper.
                getAccommodationRateDTOList(allAccommodationRateList);

        this.entityManager.getTransaction().commit();
        return accommodationRateDTOList;
    }

    /**
     * Get all the accommodation rates for an accommodation PACKAGE.
     *
     * @return List<AccommodationRateDTO> All accommodation rates for a given Accommodation Package.
     */
    @Override
    public List<AccommodationRateDTO> getAllAccommodationRatesForAccommodationPackage(Integer accommodationPackageID)
            throws SQLException {
        this.entityManager.getTransaction().begin();

        /* get all accommodation rates by Accommodation Package ID. */
        List<AccommodationRate> allAccommodationRatesByAccommodationPackageID = this.accommodationRateDAO.
                getAllAccommodationRatesByAccommodationPackageID(accommodationPackageID);

        /* convert allAccommodationRatesByAccommodationPackageID to DTOList. */
        List<AccommodationRateDTO> accommodationRateDTOList = this.mapper.
                getAccommodationRateDTOList(allAccommodationRatesByAccommodationPackageID);

        this.entityManager.getTransaction().commit();
        return accommodationRateDTOList;
    }
}
