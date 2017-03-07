package com.fmo.wom.common.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.fmo.wom.integration.nabs.util.DB2ConnectionUtil;

public abstract class JdbcDAODB2 extends JdbcDAO {

	protected abstract Logger getLogger();
	
    @PersistenceContext(unitName="db2")
    protected EntityManager entityManager = null;
    
    public JdbcDAODB2() { 
        super();
    } 
    
    protected EntityManager getEntityManager() {
    	if(entityManager == null){
    		entityManager = DB2ConnectionUtil.getEntityManager();
    	}
    	return entityManager;
    }
    
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
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
   				getLogger().debug("Release entitymanager for "+ this.getClass().getName());
       		}
   		}
    }
}
