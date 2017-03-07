package com.fmo.wom.common.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.fmo.wom.integration.nabs.util.DB2ConnectionUtil;

  
public abstract class JpaDAODB2<K, E> extends JpaDAO<K, E>{
	
	private static Logger logger = Logger.getLogger(JpaDAODB2.class);
	
	@PersistenceContext(unitName="db2")
	protected EntityManager entityManager = null;
 
    public JpaDAODB2() { 
        super();
    } 
    
    @Override
	public EntityManager getEntityManager() {
    	if(entityManager == null){
    		entityManager = DB2ConnectionUtil.getEntityManager();
    	}
    	return entityManager;
	}
    
	/*public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}*/ 
 
    @Override
	protected void finalize() throws Throwable {
		super.finalize();
		if(entityManager != null){
			if(entityManager.isOpen()){
				entityManager.close();
				entityManager = null;
			}
			
		}
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