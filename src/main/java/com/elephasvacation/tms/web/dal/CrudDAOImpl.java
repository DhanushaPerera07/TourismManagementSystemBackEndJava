/*
 * MIT License
 *
 * Copyright (c) 2021 Dhanusha Perera
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.elephasvacation.tms.web.dal;

import com.elephasvacation.tms.web.entity.SuperEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class CrudDAOImpl<T extends SuperEntity<Serializable>, K extends Serializable> implements CrudDAO<T, K> {

    private final Class<T> entityClass;
    @PersistenceContext
    private EntityManager entityManager;

    public CrudDAOImpl() {
        this.entityClass =
                (Class<T>) (((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
    }

    /**
     * This method is used to pass the EntityManager to the lower level classes that extend the CrudDAOImpl class.
     */
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public T save(T entity) throws Exception {
        this.entityManager.persist(entity);
        this.entityManager.flush();
//        return entity.getId(); // Let's return the generated ID here.
        return entity; // Let's return the saved entity here.
    }

    @Override
    public void update(T entity) throws Exception {
        this.entityManager.merge(entity);
    }

    @Override
    public void delete(K key) throws Exception {
        this.entityManager.remove( this.entityManager.getReference(entityClass, key));
    }

    @Override
    public T get(K key) throws Exception {
        return this.entityManager.find(this.entityClass, key);
    }

    @Override
    public List<T> getAll() throws Exception {
        List<T> resultList = (List<T>) this.entityManager.
                createQuery("SELECT e FROM " + this.entityClass.getName() + " e").getResultList();
        return resultList;
    }
}
