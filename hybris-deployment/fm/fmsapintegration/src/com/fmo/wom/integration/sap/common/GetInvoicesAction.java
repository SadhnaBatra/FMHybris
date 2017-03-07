package com.fmo.wom.integration.sap.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.GenericValidator;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.BillToAcctBO;
import com.fmo.wom.domain.CurrencyTypeBO;
import com.fmo.wom.domain.InvoiceBO;
import com.fmo.wom.domain.InvoiceSearchCriteriaBO;
import com.fmo.wom.domain.PriceBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.ShipToAcctBO;
import com.fmo.wom.integration.sap.SapBapiAction;
import com.fmo.wom.integration.sap.SapConnectionManager;
import com.fmo.wom.integration.sap.SapConstants;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

public abstract class GetInvoicesAction extends SapBapiAction {
	
	private static final String OUT_TABLE_NAME_KEY = "OUT_INV_DISPLAY";

	private InvoiceSearchCriteriaBO invoiceSearchCriteria;
	
	private List<InvoiceBO> invoicesList = null;
	
	public GetInvoicesAction() {
		super();
	}

	public GetInvoicesAction(SapConnectionManager aConnectionManager) {
		super(aConnectionManager);
	}

	public GetInvoicesAction(InvoiceSearchCriteriaBO invoiceSearchCriteria) {
		super();
		this.invoiceSearchCriteria = invoiceSearchCriteria;
	}

	@Override
	public List<String> getInTableKeyList() {
		return null;
	}

	@Override
	public List<String> getOutTableKeyList() {
		return constructSingleTableKeyList(OUT_TABLE_NAME_KEY);
	}

	public List<InvoiceBO> execute() throws WOMExternalSystemException {
		executeSapFunction();
		return invoicesList;
	}
	
	@Override
	public void initializeImportParams(JCoParameterList importParams) {
		if(! GenericValidator.isBlankOrNull(invoiceSearchCriteria.getDelimitedInvoiceNumbers())){
			importParams.setValue(SapConstants.INVOICE_NUMBER, invoiceSearchCriteria.getDelimitedInvoiceNumbers());
		}
		SapAcctBO sapAccountBO = invoiceSearchCriteria.getBillToAcct().getSapAccount();
		
		importParams.setValue(SapConstants.CUSTOMER_NUMBER, sapAccountBO.getSapAccountCode());
		importParams.setValue(SapConstants.SALES_ORGANIZATION, sapAccountBO.getCustomerSalesOrganization().getSalesOrganization().getCode());
		importParams.setValue(SapConstants.DISTRIBUTION_CHANNEL_KEY, sapAccountBO.getCustomerSalesOrganization().getDistributionChannel());
		importParams.setValue(SapConstants.DIVISION_KEY, sapAccountBO.getCustomerSalesOrganization().getDivision());
		
		if(! GenericValidator.isBlankOrNull(invoiceSearchCriteria.getCustPONumber())){
			importParams.setValue(SapConstants.PURCHASE_ORDER_NUMBER, invoiceSearchCriteria.getCustPONumber());
		}
		
		if(invoiceSearchCriteria.getStartInvoiceDate() != null){
			importParams.setValue(SapConstants.INVOICE_SEARCH_START_DATE, invoiceSearchCriteria.getStartInvoiceDate());
		}
		
		if(invoiceSearchCriteria.getEndInvoiceDate() !=null ){
			importParams.setValue(SapConstants.INVOICE_SEARCH_END_DATE, invoiceSearchCriteria.getEndInvoiceDate());
		}
		
		if(! GenericValidator.isBlankOrNull(invoiceSearchCriteria.getDocumentCategory())){
			importParams.setValue(SapConstants.DOCUMENT_CATEGORY, invoiceSearchCriteria.getDocumentCategory());
		}
	}

	@Override
	public void initializeInTableParams(String tableName, JCoTable inTable) {
		
	}

	@Override
	public void processOutTable(String tableName, JCoTable outTable) {
		int numRows = outTable.getNumRows();
		
		for (int i = 0; i < numRows; i++) {
			outTable.setRow(i);
			if(invoicesList == null){
				invoicesList = new ArrayList<InvoiceBO>();
			}
			invoicesList.add(createInvoiceBO(outTable));
		}
	}

	private InvoiceBO createInvoiceBO(JCoTable outTable) {
		InvoiceBO invoiceBO = new InvoiceBO();
		//invoiceBO.setDocumentCategory(outTable.getString(SapConstants.DOCUMENT_CATEGORY));
		invoiceBO.setInvoiceNumber(outTable.getString(SapConstants.INVOICE_NUMBER));
		
		BillToAcctBO invoiceToAccount = new BillToAcctBO();
		invoiceToAccount.setAccountCode(outTable.getString(SapConstants.INVOICE_TO));
		//invoiceBO.setInvoiceToAccount(invoiceToAccount);
		
		ShipToAcctBO deliverToAccount = new ShipToAcctBO();
		deliverToAccount.setAccountCode(outTable.getString(SapConstants.DELIVER_TO));
		//invoiceBO.setDeliverToAccount(deliverToAccount);

		invoiceBO.setInvoiceDate(outTable.getDate(SapConstants.INVOICE_DATE));
		
		PriceBO invoiceAmount = new PriceBO();
		CurrencyTypeBO currency = new CurrencyTypeBO(); 
		currency.setCode(outTable.getString(SapConstants.CURRENCY_KEY));
		invoiceAmount.setCurrency(currency);
		invoiceAmount.setDisplayPrice(outTable.getDouble(SapConstants.INVOICE_AMOUNT));
		invoiceBO.setInvoiceAmount(invoiceAmount);

		//invoiceBO.setCustPONumber(outTable.getString(SapConstants.PURCHASE_ORDER_NUMBER));
		return invoiceBO;
	}
	

}
