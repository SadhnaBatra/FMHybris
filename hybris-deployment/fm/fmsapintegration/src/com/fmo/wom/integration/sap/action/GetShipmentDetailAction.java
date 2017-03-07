package com.fmo.wom.integration.sap.action;




import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.CountryBO;
import com.fmo.wom.domain.OrderUnitBO;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.integration.sap.SapConnectionManager;
import com.fmo.wom.integration.sap.SapConnectionManager.SAPDestinationType;
import com.fmo.wom.integration.sap.SapConstants;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoParameterList;

public class GetShipmentDetailAction extends com.fmo.wom.integration.sap.common.GetShipmentDetailAction {

    private static Logger logger = Logger.getLogger(GetShipmentDetailAction.class);
    
       // define constructors to expose search criteria needed
    public GetShipmentDetailAction(OrderUnitBO orderUnit) {
        super(orderUnit);
    }
    
    public GetShipmentDetailAction(OrderUnitBO orderUnit, SapConnectionManager aCxnMgr) {
        super(orderUnit, aCxnMgr);
    }
    
    @Override
    protected Logger getLogger() { return logger; }
    
    @Override
    public String getFunctionName() {
        return SapConstants.GET_SHIPMENT_DETAIL_FUNC_NAME;
    }

    @Override
    protected ExternalSystem getExternalSystem() {
        return ExternalSystem.EVRST;
    }
    
    @Override
    protected Market getMarket() {
        return Market.US_CANADA;
    }

    @Override
    protected JCoDestination getDestination() throws JCoException, WOMExternalSystemException {
        return sapCxnMgr.getJCoDestination(SAPDestinationType.EVEREST);
    }

    @Override
    protected CountryBO createShipToCountry(JCoParameterList exportParamList) {
        CountryBO country = new CountryBO();
        country.setCountryAbbreviation(AddressBO.US_CODE);
        country.setIsoCountryCode(AddressBO.US_CODE);
        return country;
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
