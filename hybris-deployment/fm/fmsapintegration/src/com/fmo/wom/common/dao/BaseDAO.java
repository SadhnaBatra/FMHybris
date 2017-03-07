package com.fmo.wom.common.dao;

import java.util.List;

public interface BaseDAO<K, E> {

    public void persist(E entity);
    public void remove(K pk);
    public E merge(E entity);
    public void refresh(E entity);
    public E flush(E entity);
    public E findById(K id); 
    public List<E> findAll();
    public List<E> findAll(int offset, int maxRows);
    public List<E> findByExample(E exampleInstance);
    public void releaseEntityManager();
    
}
