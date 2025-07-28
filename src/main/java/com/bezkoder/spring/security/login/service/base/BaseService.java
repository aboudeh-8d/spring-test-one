package com.bezkoder.spring.security.login.service.base;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

public abstract class BaseService<T, ID> {

    protected abstract JpaRepository<T, ID> getRepository();

    public Optional<T> findById(ID id) {
        return getRepository().findById(id);
    }

    public List<T> findAll() {
        return getRepository().findAll();
    }

    public T save(T entity) {
        return getRepository().save(entity);
    }

    public T update(ID id, T updatedEntity) {
        if (!getRepository().existsById(id)) {
            throw new EntityNotFoundException("Entity not found with ID: " + id);
        }

        return getRepository().save(updatedEntity);
    }

    public boolean delete(ID id) {
        if (!existsById(id))
            throw new EntityNotFoundException("Entity not found with ID: " + id);
        getRepository().deleteById(id);
        return true;
    }

    public boolean existsById(ID id) {
        return getRepository().existsById(id);
    }
}
