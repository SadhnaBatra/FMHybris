package com.fmo.wom.common.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

  
public abstract class JpaDAOOracle<K, E> extends JpaDAO<K, E>{
	
	private static Logger logger = Logger.getLogger(JpaDAOOracle.class);
	
	@PersistenceContext(unitName="oracle")
	protected EntityManager entityManager = null;
 
    public JpaDAOOracle() { 
        super();
    } 
    
    @Override
	public EntityManager getEntityManager() {
		return entityManager;
	}
    public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	} 
    
    public void releaseEntityManager(){
    	if(entityManager != null){
    		if(entityManager.isOpen()){
				entityManager.close();
				entityManager = null;
				logger.debug("Release entitymanager for "+ this.getClass().getName());
    		}
		}
    }
    
}