package com.fmo.wom.integration.dao.nabs;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.hibernate.ejb.EntityManagerImpl;

import com.fmo.wom.common.dao.JdbcDAODB2;


public class MasterOrderNumberDAOImpl extends JdbcDAODB2 implements MasterOrderNumberDAO {

    private static Logger logger = Logger.getLogger(MasterOrderNumberDAOImpl.class);
    
    public Logger getLogger(){
    	return logger;
    }
    
    // The system id used for obtaining a master order number.
    private static final String SYSTEM_ID = "W";
    // default
    private static final String REQUESTING_FUNCTION = "WEB       ";
    // proc
    private static final String SQL = "{ call DDSP0004(?,?,?) }";
    // date formatter
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyMMdd");
    
    @Override
    public String getMasterOrderNumber() {
        return getMasterOrderNumber(REQUESTING_FUNCTION);
    }

    public MasterOrderNumberDAOImpl() {
		super();
	}

     
    @Override  
    // @Transactional(propagation=Propagation.NOT_SUPPORTED) 
    public String getMasterOrderNumber(final String callingSystem) {
    	logger.info("Calling stored procedure DDSP0004 with systemid= "+ SYSTEM_ID+ ", requesting function= "+ callingSystem);
    	EntityManager anEntityManager = getEntityManager();
    	org.hibernate.Session session = ((EntityManagerImpl)anEntityManager).getSession();
    	java.sql.Connection connectionObj = session.connection();
    	String masterOrderNumber = null;
    	CallableStatement cs = null;
		try {
			anEntityManager.getTransaction().begin();
			cs = connectionObj.prepareCall(SQL);
			cs.setString(1, SYSTEM_ID); 
			cs.setString(2, callingSystem); // second argument
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.execute();
			masterOrderNumber = cs.getString(3);
			anEntityManager.getTransaction().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(cs != null){
				try {
					cs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
    	return masterOrderNumber;
    }

    public static void main(String[] args) {
    	MasterOrderNumberDAOImpl dao = new MasterOrderNumberDAOImpl();
    	String masterOrderNumber = dao.getMasterOrderNumber();
	}

	//@Override  
   // @Transactional(propagation=Propagation.NOT_SUPPORTED) 
    /*public String getMasterOrderNumber(final String callingSystem) {
     
        logger.debug("Called stored procedure DDSP0004 with systemid={} and requesting function={}.", SYSTEM_ID, callingSystem);

        String masterOrderNumber = getJdbcTemplate().execute(new CallableStatementCreator() {
                
                @Override
                public CallableStatement createCallableStatement(Connection con) throws SQLException {
                    CallableStatement cs = con.prepareCall(SQL);
                    cs.setString(1, SYSTEM_ID); 
                    cs.setString(2, callingSystem); // second argument
                    cs.registerOutParameter(3, Types.VARCHAR);
                    return cs;
                }
            },
            new CallableStatementCallback<String>() {  
                public String doInCallableStatement(CallableStatement cs) throws SQLException {  
                    cs.execute();
                    return  cs.getString(3);
                }
            });  
        return masterOrderNumber != null ? masterOrderNumber.trim() : null;
    }*/

    @Override  
    public String getRandomMasterOrderNumber() {
     
        // W1205091520295 
        logger.info("Generating master order number");
        StringBuffer masterOrderNumber = new StringBuffer("W");
        masterOrderNumber.append(dateFormatter.format(new Date()));
        masterOrderNumber.append(RandomStringUtils.randomNumeric(7));
        
        return masterOrderNumber.toString();
    }

}

