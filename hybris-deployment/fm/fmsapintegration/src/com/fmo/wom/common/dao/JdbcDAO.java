package com.fmo.wom.common.dao;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

public abstract class JdbcDAO  {
    
    private DataSource dataSource;
    
    public JdbcDAO() { 
        super();
    } 
    
    protected abstract EntityManager getEntityManager();
    protected abstract void setEntityManager(EntityManager entityManager);
    
    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /*protected JdbcTemplate getJdbcTemplate() {
         // if we don't have a data source, it did not inject and we may be setting it directly.  Look it up
         if (dataSource == null) {
             EntityManagerFactoryInfo emf = (EntityManagerFactoryInfo) getEntityManager().getEntityManagerFactory();
             dataSource = emf.getDataSource();
        }
        
        return new JdbcTemplate(dataSource);
    }*/
}
