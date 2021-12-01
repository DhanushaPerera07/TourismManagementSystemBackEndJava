package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.dal.custom.AccommodationPackageMealPlanDAO;
import com.elephasvacation.tms.web.entity.AccommodationPackageMealPlan;
import com.elephasvacation.tms.web.entity.AccommodationPackageMealPlanId;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class AccommodationPackageMealPlanDAOImpl implements AccommodationPackageMealPlanDAO {

    private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Integer save(AccommodationPackageMealPlan pkgMealPlan) throws Exception {
        this.entityManager.persist(pkgMealPlan);
        //  call the flush method on EntityManager manually, because we need to get the Generated ID
        this.entityManager.flush();
        return pkgMealPlan.getIndexId(); // TODO: Check the return of the indexID
    }

    @Override
    public void update(AccommodationPackageMealPlan pkgMealPlan) throws Exception {
        this.entityManager.merge(pkgMealPlan);
    }

    @Override
    public void delete(AccommodationPackageMealPlanId key) throws Exception {
        this.entityManager.remove(this.entityManager.find(AccommodationPackageMealPlan.class, key));
    }

    @Override
    public AccommodationPackageMealPlan get(AccommodationPackageMealPlanId key) throws Exception {
        return this.entityManager.find(AccommodationPackageMealPlan.class, key);
    }

    @Override
    public List<AccommodationPackageMealPlan> getAll() throws Exception {
        TypedQuery<AccommodationPackageMealPlan> accommodationPackageMealPlanTypedQuery = this.entityManager
                .createQuery("SELECT accMp FROM AccommodationPackageMealPlan accMp",
                        AccommodationPackageMealPlan.class);

        return accommodationPackageMealPlanTypedQuery.getResultList();
    }
}
