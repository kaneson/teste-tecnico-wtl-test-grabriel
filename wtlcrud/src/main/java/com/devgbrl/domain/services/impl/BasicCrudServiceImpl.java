package com.devgbrl.domain.services.impl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devgbrl.domain.services.WithCrud;

import jakarta.transaction.Transactional;

public abstract class BasicCrudServiceImpl<T, IdT> implements WithCrud<T, IdT> {

    @Override
    public Optional<T> getById(IdT id) {
        return getRepository().findById(id);
    }

    @Override
    @Transactional
    public T save(T entity) {
        return getRepository().save(entity);
    }

    @Override
    @Transactional
    public Collection<T> saveAll(Collection<T> entities) {
        return getRepository().saveAll(entities);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        getRepository().delete(entity);
    }

    @Override
    @Transactional
    public void deleteById(IdT id) {
        getRepository().deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllByIds(Collection<IdT> ids) {
        getRepository().deleteAllById(ids);
    }

    protected abstract JpaRepository<T, IdT> getRepository();
    
}
