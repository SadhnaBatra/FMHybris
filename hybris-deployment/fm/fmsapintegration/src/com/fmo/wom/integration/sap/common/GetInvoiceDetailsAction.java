package com.fmo.wom.integration.sap.common;

import java.util.ArrayList;
import java.util.List;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.BillToAcctBO;
import com.fmo.wom.domain.CountryBO;
import com.fmo.wom.domain.InvoiceDetailsBO;
import com.fmo.wom.domain.ShipToAcctBO;
import com.fmo.wom.domain.ecc.ShippedItemBO;
import com.fmo.wom.integration.sap.SapBapiAction;
import com.fmo.wom.integration.sap.SapConnectionManager;
import com.fmo.wom.integration.sap.SapConstants;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

public abstract class GetInvoiceDetailsAction extends SapBapiAction {

	private String invoiceNumber = "";

	private static final String OUT_TABLE_BILLTO = "I_BILLTO_OUT";
	private static final String OUT_TABLE_SHIPTO = "I_SHIPTO_OUT";
	private static final String OUT_TABLE_SHIPPING_DETAILS = "I_SHIPPING_OUT";
	private static final String OUT_TABLE_ORDER_DETAIL = "I_ORDER_OUT";
	private static final String OUT_TABLE_INVOICE_FOOTER = "I_INV_FOOTER_OUT";
	
	private InvoiceDetailsBO invoiceDetails = null;
	
	public GetInvoiceDetailsAction(String invoiceNumber) {
		super();
		this.invoiceNumber = invoiceNumber;
	}
	
	public GetInvoiceDetailsAction() {
		super();
	}

	public GetInvoiceDetailsAction(SapConnectionManager aConnectionManager) {
		super(aConnectionManager);
	}

	@Override
	public List<String> getInTableKeyList() {
		return null;
	}
	
	@Override
	public List<String> getOutTableKeyList() {
		List<String> outTables = new ArrayList<String>();
        outTables.add(OUT_TABLE_BILLTO);
        outTables.add(OUT_TABLE_SHIPTO);
        outTables.add(OUT_TABLE_SHIPPING_DETAILS);
        outTables.add(OUT_TABLE_ORDER_DETAIL);
        outTables.add(OUT_TABLE_INVOICE_FOOTER);
        return outTables;
	}

	@Override
	public void initializeImportParams(JCoParameterList importParams) {
		importParams.setValue(SapConstants.INVOICE_NUMBER, this.invoiceNumber);
	}

	@Override
	public void initializeInTableParams(String tableName, JCoTable inTable) {

	}

	public InvoiceDetailsBO execute() throws WOMExternalSystemException {
		executeSapFunction();
		return this.invoiceDetails;
	}
	
	@Override
	public void processOutTable(String tableName, JCoTable outTable) {
		if(invoiceDetails == null){
			invoiceDetails = new InvoiceDetailsBO();
			invoiceDetails.setInvoiceNumber(this.invoiceNumber);
		}
		if(tableName.equalsIgnoreCase(OUT_TABLE_BILLTO)){
			processInvoiceTo(outTable);
		} else if(tableName.equalsIgnoreCase(OUT_TABLE_SHIPTO)){
			processDeliverTo(outTable);
		} else if(tableName.equalsIgnoreCase(OUT_TABLE_SHIPPING_DETAILS)){
			processShipingdetails(outTable);
		} else if(tableName.equalsIgnoreCase(OUT_TABLE_ORDER_DETAIL)){
			processShippedItemsDetail(outTable);
		} else if (tableName.equalsIgnoreCase(OUT_TABLE_INVOICE_FOOTER)){
			processInvoiceSummary(outTable);
		}
	}
	
	private void processInvoiceTo(JCoTable outTable) {
		
		outTable.setRow(0);
		
		BillToAcctBO invoiceToAccount = new BillToAcctBO();
		invoiceToAccount.setAccountCode(outTable.getString("BILL_TO"));
		
		AddressBO invoiceToAddress = new AddressBO();
		invoiceToAddress.setAddrName(outTable.getString("NAME"));
		invoiceToAddress.setAddrLine1(outTable.getString("STREET"));
		invoiceToAddress.setCity(outTable.getString("CITY"));
		invoiceToAddress.setStateOrProv(outTable.getString("REGION"));
		invoiceToAddress.setZipOrPostalCode(outTable.getString("POSTAL_CODE"));
		
		CountryBO invoiceToCountry = new CountryBO();
		invoiceToCountry.setCountryName(outTable.getString("COUNTRY"));
		
		invoiceToAddress.setCountry(invoiceToCountry);

		invoiceToAccount.setAddress(invoiceToAddress);
		
		this.invoiceDetails.setInvoiceToAccount(invoiceToAccount);
	}

	private void processDeliverTo(JCoTable outTable) {
		
		outTable.setRow(0);
		
		ShipToAcctBO deliverToAccount = new ShipToAcctBO();
		deliverToAccount.setAccountCode(outTable.getString("SHIPTO"));
		
		AddressBO shipToAddress = new AddressBO();
		shipToAddress.setAddrName(outTable.getString("NAME"));
		shipToAddress.setAddrLine1(outTable.getString("STREET"));
		shipToAddress.setCity(outTable.getString("CITY"));
		shipToAddress.setStateOrProv(outTable.getString("REGION"));
		shipToAddress.setZipOrPostalCode(outTable.getString("POSTAL_CODE"));
		
		CountryBO shipToCountry = new CountryBO();
		shipToCountry.setCountryName(outTable.getString("COUNTRY"));
		
		shipToAddress.setCountry(shipToCountry);

		deliverToAccount.setAddress(shipToAddress);
		this.invoiceDetails.setDeliverToAccount(deliverToAccount);
	}
	
	private void processShipingdetails(JCoTable outTable) {
		
		outTable.setRow(0);
		
		this.invoiceDetails.setPackingSlip(outTable.getString("PACK_SLIP"));
		this.invoiceDetails.setShippingDate(outTable.getDate("SHIPPING_DT"));
		this.invoiceDetails.setShippedVia(outTable.getString("ROUTE"));
		this.invoiceDetails.setTrackingNumber(outTable.getString("TRACKING_NUM"));
		this.invoiceDetails.setInvoiceDate(outTable.getDate("BILLING_DATE"));
		this.invoiceDetails.setConfirmationNumer(outTable.getString("CUSTOMER_NAME"));
		this.invoiceDetails.setPaymentMethod(outTable.getString("PAYMENT_METHOD"));
	}
	
	private void processShippedItemsDetail(JCoTable outTable) {
		
		int numRows = outTable.getNumRows();
		
		for (int i = 0; i < numRows; i++) {
			outTable.setRow(i);
			List<ShippedItemBO> orderDetailsList = this.invoiceDetails.getOrderDetails();
			if(orderDetailsList == null){
				orderDetailsList = new ArrayList<ShippedItemBO>();
				this.invoiceDetails.setOrderDetails(orderDetailsList);
			}
			ShippedItemBO shippedItemBO = new ShippedItemBO();
			shippedItemBO.setOrderedQuantity(outTable.getInt("ORDER_QTY"));
			shippedItemBO.setShippedQuantity(outTable.getInt("SHIPPED_QTY"));
			shippedItemBO.setUnit(outTable.getString("SALES_UNIT"));
			shippedItemBO.setPartNumber(outTable.getString("MATERIAL"));
			shippedItemBO.setDescription(outTable.getString("DESCRIPTION"));
			shippedItemBO.setUnitPrice(outTable.getDouble("PROD_UNITPRICE"));
			shippedItemBO.setTotalPrice(outTable.getDouble("NET_AMOUNT"));

			orderDetailsList.add(shippedItemBO);
		}
	}
	
	private void processInvoiceSummary(JCoTable outTable) {
		
		outTable.setRow(0);
		this.invoiceDetails.setSubTotal(outTable.getDouble("SUBTOTAL"));
		/*this.invoiceDetails.setUnitExt(outTable.getString("UNIT_EXT"));*/
		this.invoiceDetails.setDeliveryCharges(outTable.getString("DELIVERY_CHARGES"));
		this.invoiceDetails.setTax(outTable.getDouble("TAXES"));
		this.invoiceDetails.setTotal(outTable.getDouble("TOTAL"));
	}
	
}
