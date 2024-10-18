package org.example.services;

import java.util.Collection;

public interface CRUDService<T> {
    T getById(Long id);
    Collection<T> getAll();
    void create(T item);
    void update(T item);

    boolean delete(Long id);
}
