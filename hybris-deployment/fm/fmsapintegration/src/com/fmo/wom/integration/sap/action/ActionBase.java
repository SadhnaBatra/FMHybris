package com.fmo.wom.integration.sap.action;

import java.util.List;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.integration.sap.SapBapiAction;
import com.fmo.wom.integration.sap.SapConnectionManager;
import com.fmo.wom.integration.sap.SapConnectionManager.SAPDestinationType;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;

public abstract class ActionBase extends SapBapiAction {

    public ActionBase() {
        super();
    }
    
    public ActionBase(SapConnectionManager aCxnMgr) {
        super(aCxnMgr);
    }
        
    protected JCoDestination getDestination() throws JCoException, WOMExternalSystemException {
        return sapCxnMgr.getJCoDestination(SAPDestinationType.EVEREST);
    }
    
}
