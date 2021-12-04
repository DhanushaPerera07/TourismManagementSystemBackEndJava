package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.commonConstant.Number;
import com.elephasvacation.tms.web.dal.custom.AccommodationPackageRoomTypeDAO;
import com.elephasvacation.tms.web.entity.AccommodationPackage;
import com.elephasvacation.tms.web.entity.AccommodationPackageRoomType;
import com.elephasvacation.tms.web.entity.AccommodationPackageRoomTypeId;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class AccommodationPackageRoomTypeDAOImpl implements AccommodationPackageRoomTypeDAO {

    private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Integer save(AccommodationPackageRoomType pkgRoomType) throws Exception {
        this.entityManager.persist(pkgRoomType);
        //  call the flush method on EntityManager manually, because we need to get the Generated ID
        this.entityManager.flush();
        return pkgRoomType.getIndexId(); // TODO: Check the return of the indexID
    }

    @Override
    public void update(AccommodationPackageRoomType pkgRoomType) throws Exception {
        this.entityManager.merge(pkgRoomType);
    }

    @Override
    public void delete(AccommodationPackageRoomTypeId key) throws Exception {
        this.entityManager.remove(this.entityManager.find(AccommodationPackageRoomType.class, key));
    }

    @Override
    public AccommodationPackageRoomType get(AccommodationPackageRoomTypeId key) throws Exception {
        return this.entityManager.find(AccommodationPackageRoomType.class, key);
    }

    @Override
    public List<AccommodationPackageRoomType> getAll() throws Exception {
        TypedQuery<AccommodationPackageRoomType> selectPkgRoomTypeQuery = this.entityManager
                .createQuery("SELECT accRt FROM AccommodationPackageRoomType accRt",
                        AccommodationPackageRoomType.class);

        return selectPkgRoomTypeQuery.getResultList();
    }

    @Override
    public List<AccommodationPackageRoomType> getAllPackageRoomTypesForAccommodationPackage(AccommodationPackage accommodationPackage) {
        TypedQuery<AccommodationPackageRoomType> query = this.entityManager
                .createQuery("SELECT accRt " +
                                "FROM AccommodationPackageRoomType accRt " +
                                "WHERE accRt.id.accommodationPackageId=?1",
                        AccommodationPackageRoomType.class);

        query.setParameter(Number.ONE, accommodationPackage.getId());

        return query.getResultList();
    }
}
