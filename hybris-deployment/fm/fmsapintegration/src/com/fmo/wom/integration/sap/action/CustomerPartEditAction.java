package com.fmo.wom.integration.sap.action;

import java.util.List;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.CurrencyTypeBO;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.PriceBO;
import com.fmo.wom.domain.enums.CustomerBusinessType;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.integration.sap.SapConnectionManager;
import com.fmo.wom.integration.sap.SapConnectionManager.SAPDestinationType;
import com.fmo.wom.integration.sap.SapConstants;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

public class CustomerPartEditAction extends com.fmo.wom.integration.sap.common.CustomerPartEditAction {

	private static Logger logger = Logger.getLogger(CustomerPartEditAction.class);
	
	protected CustomerPartEditAction() { super(); };

	public CustomerPartEditAction(String sapAccountCode, CustomerSalesOrgBO salesOrg, List<ItemBO> itemList) {
		super(sapAccountCode, salesOrg, itemList);
	}

	public CustomerPartEditAction(String sapAccountCode, CustomerSalesOrgBO salesOrg, 
	                              List<ItemBO> itemList, SapConnectionManager aCxnMgr) {
        super(sapAccountCode, salesOrg, itemList, aCxnMgr);
    }
	
	@Override
    protected Logger getLogger() { return logger; }
    
    @Override
    public String getFunctionName() {
        return SapConstants.CUSTOMER_EDIT_FUNC_NAME;
    }
    
    @Override
    protected JCoDestination getDestination() throws JCoException, WOMExternalSystemException {
        return sapCxnMgr.getJCoDestination(SAPDestinationType.EVEREST);
    }
    
	@Override
	public void initializeImportParams(JCoParameterList importParams) {

		importParams.setValue(SapConstants.SOLD_TO_CUSTOMER_KEY, getSapAccountCode());
		importParams.setValue(SapConstants.SALES_ORGANIZATION_KEY, getCustomerSalesOrganization().getSalesOrganization().getCode()); 
		importParams.setValue(SapConstants.DISTRIBUTION_CHANNEL_KEY, getCustomerSalesOrganization().getDistributionChannel());
		importParams.setValue(SapConstants.DIVISION_KEY, getCustomerSalesOrganization().getDivision()); 
	}
	

    @Override
    protected ExternalSystem getExternalSystem() {
        return ExternalSystem.EVRST;
    }
    
    @Override
    protected CustomerBusinessType getDefaultCustomerBusinessType() {
        return CustomerBusinessType.JBR;
    }
    
    protected void createPrice(ItemBO currentInputItem, JCoTable currentRow) {
        
        PriceBO price = currentInputItem.getItemPrice();
        if (price == null) {
            price = new PriceBO();
            currentInputItem.setItemPrice(price);
        }
        
        price.setDisplayPrice(currentRow.getDouble(SapConstants.PRICE_KEY));
        
        // default to what underlying system tell us
        price.setPriceType(getDefaultCustomerBusinessType());
        // now check what sap gave us
        String priceType = currentRow.getString(SapConstants.PRICE_TYPE_KEY);
        if (GenericValidator.isBlankOrNull(priceType) == false) {
            price.setPriceType(CustomerBusinessType.getFromCode(priceType));
        }
        
        price.setFreightPrice(currentRow.getDouble(SapConstants.NET_PRICE_KEY));
        
        CurrencyTypeBO aCurrency = new CurrencyTypeBO();
        aCurrency.setCode(currentRow.getString(SapConstants.CURRENCY_KEY));
        price.setCurrency( aCurrency );
    }
}
