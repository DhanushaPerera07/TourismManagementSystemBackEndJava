package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.entity.Accommodation;
import com.elephasvacation.tms.web.util.HibernateUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class AccommodationDAOImplTest {

    EntityManagerFactory emf = null;
    EntityManager em = null;

    AccommodationDAOImpl accommodationDAO = DAOFactory.getInstance().getDAO(DAOTypes.ACCOMMODATION);

    @Before
    public void setEntityManager() {
        try {
            this.emf = HibernateUtil.getEntityManagerFactory();
            this.em = emf.createEntityManager();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void closeEntityManager() {
        if (em != null) {
            em.close();
            emf.close();
        }
    }

    @Test
    public void save() {
        Assert.assertNotNull(accommodationDAO);
        Accommodation accommodation = new Accommodation("ABC",
                "Colombo",
                4,
                "Hotel",
                "01121234567",
                "abc@gmail.com",
                "123, Colombo",
                "abc.hotels.com",
                "None",
                "None");

        try {
            em.getTransaction().begin();
            accommodationDAO.setEntityManager(em);
            Integer newAccommodation = accommodationDAO.save(accommodation);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}