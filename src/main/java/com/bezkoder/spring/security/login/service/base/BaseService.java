package com.bezkoder.spring.security.login.service.base;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @SuppressWarnings("unchecked")
    public <T> void updateNonNullFields(Object sourceDto, T targetEntity) {
        Field[] dtoFields = sourceDto.getClass().getDeclaredFields();
        Field[] entityFields = targetEntity.getClass().getDeclaredFields();

        Map<String, Field> entityFieldMap = new HashMap<>();
        for (Field ef : entityFields) {
            ef.setAccessible(true);
            entityFieldMap.put(ef.getName(), ef);
        }

        for (Field dtoField : dtoFields) {
            dtoField.setAccessible(true);
            try {
                Object value = dtoField.get(sourceDto);
                if (value != null && entityFieldMap.containsKey(dtoField.getName())) {
                    Field entityField = entityFieldMap.get(dtoField.getName());
                    // Optionally check type compatibility
                    if (entityField.getType().isAssignableFrom(dtoField.getType())) {
                        entityField.set(targetEntity, value);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to update field: " + dtoField.getName(), e);
            }
        }
    }
}
