package com.bezkoder.spring.security.login.repository.base;


import com.bezkoder.spring.security.login.entity.base.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class BaseRepositoryImpl<T extends BaseEntity, ID> {

    @Autowired
    protected EntityManager entityManager;

    public void detach(T entity) {
        entityManager.detach(entity);
    }

    public void softDelete(T entity) {
        entity.setDeleted(true);
        entityManager.merge(entity);
    }

    // Add any shared helper methods
}

