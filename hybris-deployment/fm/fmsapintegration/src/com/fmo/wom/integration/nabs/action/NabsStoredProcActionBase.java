package com.fmo.wom.integration.nabs.action;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.hibernate.ejb.EntityManagerImpl;

import com.fmo.wom.common.dao.JdbcDAODB2;

public abstract class NabsStoredProcActionBase extends JdbcDAODB2 {

    private static Logger logger = Logger.getLogger(NabsStoredProcActionBase.class);
    
	protected NabsStoredProcOutput executeNabsStoredProcCall(final NabsStoredProcInput input) {
  
		logger.info("Executing NABS call on "+input);
		
		EntityManager anEntityManager = getEntityManager();
    	org.hibernate.Session session = ((EntityManagerImpl)anEntityManager).getSession();
    	java.sql.Connection connectionObj = session.connection();
    	CallableStatement cstmt = null;
		try {
			anEntityManager.getTransaction().begin();
			Properties p = new Properties();
			connectionObj.setClientInfo(p);
			cstmt = input.createCallableStatement(connectionObj);
			
			Date startTime = new Date();
            ResultSet rs = cstmt.executeQuery();
			Date endTime = new Date();
			logger.info("Executed NABS proc " + input.getProcedureName()+ ".  Time= "+ (endTime.getTime() - startTime.getTime())+ " ms");
			
			NabsStoredProcOutput output = createOutput(cstmt, rs);
			anEntityManager.getTransaction().commit();
			return output;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(cstmt != null){
				try {
					cstmt.close();
					releaseEntityManager();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
		
	}

    protected abstract NabsStoredProcOutput createOutput(CallableStatement cs, ResultSet rs) throws SQLException;

}
