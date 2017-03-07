package com.fmo.wom.common.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.fmo.wom.common.exception.WOMNoResultException;
import com.fmo.wom.common.exception.WOMNonUniqueResultException;

public abstract class JpaDAO<K, E> implements BaseDAO<K, E>   {
    protected Class<E> entityClass; 

    @SuppressWarnings("unchecked") 
    public JpaDAO() { 
        
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass(); 
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1]; 
    } 
    protected abstract EntityManager getEntityManager();
    
    /**
     * Execute a query that expects one result. This is merely catching some runtimes and
     * throwing some equivalent checked exceptions.  
     * @param query
     * @return the results of the query.  This is Object and not E on purpose - named queries
     * could be selecting one column and not the entire object.  I know this forces a cast
     * in each DAO method this is used so if you want to make two different methods, feel free.
     * @throws WOMNonUniqueResultException
     * @throws WOMNoResultException
     */
    public Object getSingleResult(Query query) throws WOMNonUniqueResultException, WOMNoResultException {
        
        return getSingleResult(query, true);
    }
    
    /**
     * Execute a query that expects one result. This is merely catching some runtimes and
     * throwing some equivalent checked exceptions. The supplied doLogging parameter will
     * dictate if the created WOM Exceptions log
     * @param query
     * @param doLogging log the created exceptions automatically
     * @return the results of the query.  This is Object and not E on purpose - named queries
     * could be selecting one column and not the entire object.  I know this forces a cast
     * in each DAO method this is used so if you want to make two different methods, feel free.
     * @throws WOMNonUniqueResultException
     * @throws WOMNoResultException
     */
    
    public Object getSingleResult(Query query, boolean doLogging) throws WOMNonUniqueResultException, WOMNoResultException {
            
        if (query == null) {
            throw new WOMNoResultException("No Query to execute");
        }
        
        try {
            return query.getSingleResult();
        } 
        catch (NoResultException noResult) {
            // - there is no result.  If doLogging is false, user must log these!
            throw new WOMNoResultException(noResult, doLogging);
        }
        catch (NonUniqueResultException nonUniqueResult) {
            // more than one. If doLogging is false, user must log these!
            throw new WOMNonUniqueResultException(nonUniqueResult, doLogging);
        }
    }
    
    public void persist(E entity) { 
    	getEntityManager().persist(entity); 
    } 

    public void remove(K pk) { 
    	EntityManager anEntityManager = getEntityManager();
    	anEntityManager.remove(anEntityManager.getReference(entityClass, pk)); 
    } 
    
    public E merge(E entity) { 
    	EntityManager anEntityManager = getEntityManager();
        entity = anEntityManager.merge(entity);
    	return entity;//getEntityManager().merge(entity); 
    } 
    
    public void refresh(E entity) { 
    	EntityManager anEntityManager = getEntityManager();
    	anEntityManager.refresh(entity);
    } 

    public E flush(E entity) { 
    	EntityManager anEntityManager = getEntityManager();
    	anEntityManager.flush(); 
    	return entity; 
	}

    public E findById(K id) {
        return getEntityManager().find(entityClass, id);
    }

    @SuppressWarnings("unchecked") 
    public List<E> findAll() { 
        Query q = getFindAllQuery(); 
        return q.getResultList(); 
    } 
    
    @SuppressWarnings("unchecked") 
    public List<E> findAll(int offset, int maxRows) { 
        Query q = getFindAllQuery(); 
        q.setFirstResult(offset);
        q.setMaxResults(maxRows);
        return q.getResultList(); 
    } 

    @SuppressWarnings("unchecked")
    public List<E> findByExample(E exampleInstance) {
        Session session = (Session) getEntityManager().getDelegate();
        Example example =  Example.create(exampleInstance);
        Criteria crit = session.createCriteria(entityClass);
        crit.add(example);
        final List<E> result = crit.list();  
        return result; 
    }
    
    private Query getFindAllQuery() {
        return getEntityManager().createQuery("SELECT h FROM " + entityClass.getName() + " h"); 
    }
}