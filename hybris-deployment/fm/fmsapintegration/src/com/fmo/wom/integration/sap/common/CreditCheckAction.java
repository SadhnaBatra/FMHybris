package com.fmo.wom.integration.sap.common;

import java.util.List;



import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.CreditCheckBO;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.SalesOrganizationBO;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.integration.sap.SapBapiAction;
import com.fmo.wom.integration.sap.SapConstants;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

public abstract class CreditCheckAction extends SapBapiAction {

	/* Input*/
	private String customerNumber = null;
	private SalesOrganizationBO salesOrganization = null;
	
	/*Output*/
	private String creditBlock = null;
	private String orderBlock  = null;
	
	public CreditCheckAction() {
		super();
	}

	public CreditCheckAction(String customerNumber, SalesOrganizationBO salesOrganization) {
		super();
		this.customerNumber = customerNumber;
		this.salesOrganization = salesOrganization;
	}
	
	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public SalesOrganizationBO getSalesOrganization() {
		return salesOrganization;
	}

	public void setSalesOrganization(SalesOrganizationBO salesOrganization) {
		this.salesOrganization = salesOrganization;
	}

	/**
	 * Main method to execute this action.  Defines the return type as a String  
	 * @throws WOMExternalSystemException
	 */
	public CreditCheckBO execute() throws WOMExternalSystemException {
		executeSapFunction();
		return new CreditCheckBO(this.creditBlock, this.orderBlock);
	}
	
	protected abstract Market getMarket();
	
	
	@Override
	public List<String> getInTableKeyList() {
		return null;
	}

	@Override
	public List<String> getOutTableKeyList() {
		return null;
	}

	@Override
	public void initializeImportParams(JCoParameterList importParams) {
		importParams.setValue(SapConstants.CREDIT_CHECK_IN_CUST_NUM, customerNumber);
		importParams.setValue(SapConstants.CREDIT_CHECK_IN_SALES_ORG, getSalesOrganization().getCode());
	}

	@Override
	public void initializeInTableParams(String tableName, JCoTable inTable) {

	}
	
	

	@Override
	public void processOutTable(String tableName, JCoTable outTable) {

	}
	
	/**
     * Process the export param list for this call.  For process order, all we need
     * is the order number returned.
     */
    @Override
    protected void processExportParamList(JCoParameterList exportParamList) {
        creditBlock = exportParamList.getString(SapConstants.CREDIT_CHECK_OUT_CREDIT_BLOCK);
        orderBlock = exportParamList.getString(SapConstants.CREDIT_CHECK_OUT_ORDER_BLOCK);
    }

}
