package com.fmo.wom.integration.sap.action;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.domain.enums.OrderSearchFilter;
import com.fmo.wom.integration.sap.SapConnectionManager;
import com.fmo.wom.integration.sap.SapConnectionManager.SAPDestinationType;
import com.fmo.wom.integration.sap.SapConstants;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoTable;

public class GetShipmentInfoAction extends com.fmo.wom.integration.sap.common.GetShipmentInfoAction {

    private static Logger logger = Logger.getLogger(GetShipmentInfoAction.class);
    
    // These are currently the only search parameters supported.  As we move to include more (web or other interfaces), 
    // we'll probably want to migrate this from a constructor based to a setter based approach on input params.
    public GetShipmentInfoAction(String sapAccountCode, String masterOrderNumber, String customerPONumber) {
        super(sapAccountCode, masterOrderNumber, customerPONumber);
    }
    
    // These are currently the only search parameters supported.  As we move to include more (web or other interfaces), 
    // we'll probably want to migrate this from a constructor based to a setter based approach on input params.
    public GetShipmentInfoAction(String sapAccountCode, String masterOrderNumber, String customerPONumber, Date startDate, Date endDate, OrderSearchFilter filter) {
        super(sapAccountCode, masterOrderNumber, customerPONumber, startDate, endDate, filter);
    }
    
    public GetShipmentInfoAction(String sapAccountCode, String shipToAccountCode, String masterOrderNumber, String customerPONumber, SapConnectionManager aCxnMgr) {
        super(sapAccountCode, shipToAccountCode, masterOrderNumber, customerPONumber, aCxnMgr);
    }
    
    public GetShipmentInfoAction(List<String> sapAccountCodeList, String shipToAccountCode, String masterOrderNumber, String customerPONumber, Date startDate, Date endDate, OrderSearchFilter filter) {
        super(sapAccountCodeList, shipToAccountCode, masterOrderNumber, customerPONumber, startDate, endDate, filter);
    }

    @Override
    protected JCoDestination getDestination() throws JCoException, WOMExternalSystemException {
        return sapCxnMgr.getJCoDestination(SAPDestinationType.EVEREST);
    }
    
    @Override
    public String getFunctionName() {
        return SapConstants.GET_SHIPMENT_INFO_FUNC_NAME;
    }

    @Override
    protected Logger getLogger() { return logger; }

    @Override
    protected ExternalSystem getExternalSystem() {
        return ExternalSystem.EVRST;
    }

    @Override
    protected Market getMarket() {
        return Market.US_CANADA;
    }

    @Override
    public void initializeInTableParams(String tableName, JCoTable in) {
        // first set up the normal stuff
        super.initializeInTableParams(tableName, in);
        // now do our crazy stuff
        if (getSapAccountCodeList() != null) {
            for (String anAccountCode : getSapAccountCodeList()) {
        
                // this will look completely weird but bear with me
                if (anAccountCode.length() == 10 && anAccountCode.startsWith("00")) {
                    // it seems that searching on account numbers like 00xxxxxxxx may not return all orders for account xxxxxxxx
                    // so I'm going to add that in if the length is 10.  I am going to leave the original tho - hope that's right
                    // but I do not want to risk missing orders for 00xxxxxxxx by only sending xxxxxxxx.
                    //
                    // Could have done a regexp instead of the length and starts with, but I am going to make this very explicit 
                    // so it doesn't get misread in the future or match unintended strings
                    String weirdAccountCode = anAccountCode.substring(2);
                    in.appendRow();
                    in.setValue(SapConstants.CUSTOMER_KEY, weirdAccountCode); 
                }
            }
        }
    }

	@Override
	public void setOrderRetrunFlag(String orderRetrunFlag) {
		this.orderReturnFlag = orderRetrunFlag;
	}

	@Override
	public String getOrderRetrunFlag() {
		return this.orderReturnFlag;
	}
    
}
