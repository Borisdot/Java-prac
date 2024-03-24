package ru.msu.cmc.webprac.DAO;

import ru.msu.cmc.webprac.models.CommonEntity;

import java.util.Collection;

public interface CommonDAO<T extends CommonEntity<ID>, ID> {
    T getById(ID id);

    Collection<T> getAll();

    void save(T entity);

    void saveCollection(Collection<T> entities);

    void delete(T entity);

    void deleteById(ID id);

    void update(T entity);
}
