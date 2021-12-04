package com.elephasvacation.tms.web.dal.custom.impl;

import com.elephasvacation.tms.web.dal.custom.EmployeeCredentialDAO;
import com.elephasvacation.tms.web.entity.EmployeeCredential;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class EmployeeCredentialDAOImpl implements EmployeeCredentialDAO {

    private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Integer save(EmployeeCredential credential) throws Exception {
        this.entityManager.persist(credential);
        //  call the flush method on EntityManager manually, because we need to get the Generated ID
        this.entityManager.flush();
        return credential.getId();
    }

    @Override
    public void update(EmployeeCredential credential) throws Exception {
        this.entityManager.merge(credential);
    }

    @Override
    public void delete(Integer key) throws Exception {
        this.entityManager.remove(this.entityManager.find(EmployeeCredential.class, key));
    }

    @Override
    public EmployeeCredential get(Integer key) throws Exception {
        return this.entityManager.find(EmployeeCredential.class, key);
    }

    @Override
    public List<EmployeeCredential> getAll() throws Exception {
//        Query allEmployeesQuery = this.entityManager.createQuery("SELECT ec FROM EmployeeCredential ec");
//        return (List<EmployeeCredential>) allEmployeesQuery.getResultList();

        TypedQuery<EmployeeCredential> selectEmployeeCredentialTypedQuery =
                this.entityManager.createQuery("SELECT ec FROM EmployeeCredential ec", EmployeeCredential.class);

        return selectEmployeeCredentialTypedQuery.getResultList();
    }
}
