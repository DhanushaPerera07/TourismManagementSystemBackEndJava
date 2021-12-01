package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.dal.custom.AccommodationPackageRoomCategoryDAO;
import com.elephasvacation.tms.web.entity.AccommodationPackageRoomCategory;
import com.elephasvacation.tms.web.entity.AccommodationPackageRoomCategoryId;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class AccommodationPackageRoomCategoryDAOImpl implements AccommodationPackageRoomCategoryDAO {

    private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Integer save(AccommodationPackageRoomCategory pkgRoomCategory) throws Exception {
        this.entityManager.persist(pkgRoomCategory);
        //  call the flush method on EntityManager manually, because we need to get the Generated ID
        this.entityManager.flush();
        return pkgRoomCategory.getIndexId(); // TODO: Check the return of the indexID
    }

    @Override
    public void update(AccommodationPackageRoomCategory pkgRoomCategory) throws Exception {
        this.entityManager.merge(pkgRoomCategory);
    }

    @Override
    public void delete(AccommodationPackageRoomCategoryId key) throws Exception {
        this.entityManager.remove(this.entityManager.find(AccommodationPackageRoomCategory.class, key));
    }

    @Override
    public AccommodationPackageRoomCategory get(AccommodationPackageRoomCategoryId key) throws Exception {
        return this.entityManager.find(AccommodationPackageRoomCategory.class, key);
    }

    @Override
    public List<AccommodationPackageRoomCategory> getAll() throws Exception {
        TypedQuery<AccommodationPackageRoomCategory> selectPkgRoomCategoryTypedQuery = this.entityManager
                .createQuery("SELECT pkgRc FROM AccommodationPackageRoomCategory pkgRc",
                        AccommodationPackageRoomCategory.class);
        return selectPkgRoomCategoryTypedQuery.getResultList();
    }
}
