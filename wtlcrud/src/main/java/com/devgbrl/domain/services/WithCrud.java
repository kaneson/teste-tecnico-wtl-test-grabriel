package com.devgbrl.domain.services;

import java.util.Collection;
import java.util.Optional;

public interface WithCrud<T, IdT> {

    Optional<T> getById(IdT id);
    
    T save(T entity);
    Collection<T> saveAll(Collection<T> entities);

    void delete(T entity);
    void deleteById(IdT id);
    void deleteAllByIds(Collection<IdT> ids);

}
