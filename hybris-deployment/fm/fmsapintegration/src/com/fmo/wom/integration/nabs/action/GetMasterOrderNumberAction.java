package com.fmo.wom.integration.nabs.action;

import org.apache.log4j.Logger;

import com.fmo.wom.integration.dao.nabs.MasterOrderNumberDAO;
import com.fmo.wom.integration.dao.nabs.MasterOrderNumberDAOImpl;

public class GetMasterOrderNumberAction {

    private static Logger logger = Logger.getLogger(GetMasterOrderNumberAction.class);
    
    private MasterOrderNumberDAO masterOrderNumberDAO;
    
    
    public GetMasterOrderNumberAction() {
		super();
		masterOrderNumberDAO = new MasterOrderNumberDAOImpl();
	}

	private String callingSystem;
    
    public String executeAction() {
        
        String masterOrderNumber = null;
        if (callingSystem == null) {
            masterOrderNumber = masterOrderNumberDAO.getRandomMasterOrderNumber();
        } else {
            masterOrderNumber = masterOrderNumberDAO.getMasterOrderNumber(callingSystem);
        }
        logger.info("Calling System : "+callingSystem+" Master Order Number = "+masterOrderNumber);
        masterOrderNumberDAO.releaseEntityManager();
        return masterOrderNumber;
    }
  
    public String getCallingSystem() {
        return callingSystem;
    }

    public void setCallingSystem(String callingSystem) {
        this.callingSystem = callingSystem;
    }

}
