package com.fmo.wom.integration.sap;

import java.text.SimpleDateFormat;

public class SapConstants {

	public static final String RETURN="RETURN";
	public static final String RETURN_TYPE="TYPE";
	public static final String RETURN_CODE="CODE";
	public static final String RETURN_MESSAGE="MESSAGE";
	public static final String RETURN_TYPE_ERROR = "E";
	public static final String RETURN_TYPE_INFO = "I";
	public static final String RETURN_NUMBER = "NUMBER";

	public static final String YES = "Y";
	public static final String NO = "N";
	public static final String MESSAGE_KEY = "MESSAGE";
	
	
	//public static final String CUSTOMER_EDIT_FUNC_NAME = "Z_WOM_CUSTOMER_PART_EDIT";
	public static final String CUSTOMER_EDIT_FUNC_NAME = "Z_HYBRIS_CUSTOMER_PART_EDIT";
	
	/* Check Inventory Start */
	public static final String CHECK_INVENTORY_FUNC_NAME = "Z_HYBRIS_CHECK_INVENTORY";//"Z_WOM_CHECK_INVENTORY";
	public static final String TSC_PICKUP_CHECK_INVENTORY_FUNC_NAME = "Z_HYBRIS_CHK_INVENTORY_PICKUP";
	
	public static final String SOLD_TO_CUSTOMER_KEY = "SOLDTO_CUSTOMER";
 	public static final String SHIP_TO_CUSTOMER_KEY = "SHIPTO_CUSTOMER";
	public static final String SALES_ORGANIZATION_KEY = "SALES_ORGANIZATION";
	public static final String DISTRIBUTION_CHANNEL_KEY = "DISTR_CHANNEL";
	public static final String DIVISION_KEY = "DIVISION";
	public static final String ORDER_TYPE		= "DOC_TYPE";
	public static final String ORDER_SOURCE     = "PO_TYPE";
	
	public static final String SHIP_TYPE   		= "SHIP_TYPE";    /*New 12 - shipping, 13- Store Pickup */
	public static final String AVAILABLE_DATE   = "AVAIL_DATE";
	public static final int    SHIP_TYPE_SHIPPING = 12;
	public static final int    SHIP_TYPE_STOREPICKUP = 13;
	public static final String PAYMENT_METHOD		= "PAYMENT_METHOD";
	/* In table Parameter - for SHIP_TYPE = Store Pickup  Pass the plant location */
	
	public static final String PLANT_LOCATION   = "PLANT_LOC"; /*4 DIGIT Name/num*/
	
	/* Check Inventory End*/	
	
	
	public static final String PROCESS_ORDER_FUNC_NAME = "Z_HYBRIS_CREATE_ORDER";//"Z_WOM_CREATE_ORDER";
	
	public static final String GET_SHIPMENT_INFO_FUNC_NAME = "ZOTC_HYBRIS_ORD_SEARCH";//Z_WOM_ORDER_SEARCH";
	
	public static final String GET_SHIPMENT_DETAIL_FUNC_NAME = "ZOTC_HYBRIS_ORD_VIEW";//"Z_WOM_VIEW_ORDER";
	
	
	public static final String GET_BACKORDERS_FUNC_NAME = "Z_WOM_BACKORDER_REQUEST";
	public static final String CREDIT_CHECK_FUNC_NAME   = "Z_HYBR_CUSTOM_CREDIT_CHECK";
	public static final String CREDIT_CHECK_IN_CUST_NUM    = "I_KUNNR";
	public static final String CREDIT_CHECK_IN_SALES_ORG   = "I_VKORG";
	public static final String CREDIT_CHECK_OUT_CREDIT_BLOCK    = "E_CREDITBLOCK";
	public static final String CREDIT_CHECK_OUT_ORDER_BLOCK   = "E_SALESORDERBLOCK";
	
	
	/* Invoices */
	public static final String GET_INVOICES_FUNC_NAME    =    "Z_HYBR_INVOICE_HEADER";
	public static final String INVOICE_NUMBER 			 = "INVOICE";
	public static final String CUSTOMER_NUMBER           = "CUST_NO";
	public static final String INVOICE_SEARCH_START_DATE = "FROM_DATE";
	public static final String INVOICE_SEARCH_END_DATE   = "TO_DATE";
	public static final String DOCUMENT_CATEGORY         = "DOC_CAT";
	public static final String INVOICE_TO                = "BILL_TO";
	public static final String DELIVER_TO                = "SHIP_TO";
	public static final String INVOICE_DATE              = "INV_DATE";
	public static final String INVOICE_AMOUNT            = "NET_VALUE";
	public static final String SALES_ORGANIZATION        = "SALES_ORG";
	public static final String PURCHASE_ORDER_NUMBER     = "PO_NUM";
	
	public static final String GET_INVOICE_DETAILS_FUNC_NAME = "Z_HYBR_INVOIC_DETAILS";
	
	public static final String MATERIAL_ID_KEY = "MATERIAL_ID";
	public static final String MATERIAL_DESC_KEY = "MATERIAL_DESC";
	public static final String SAP_PART_NUM_KEY = "SAP_MATERIAL";
	public static final String ITEM_TEXT_KEY = "ITEM_TEXT";
	public static final String QUANTITY_KEY = "QUANTITY";
	public static final String AVAILABLE_QUANTITY_KEY = "AVAIL_QTY";
    public static final String ASSIGNED_QUANTITY_KEY = "ASSIGNED_QTY";
	public static final String CONFIRMED_QUANTITY_KEY = "CONFIRMED_QTY";
	public static final String BACKORDERED_QUANTITY_KEY = "BACKORDERED_QTY";
	public static final String ORIGINAL_ORDER_QUANTITY_KEY = "ORIG_ORDER_QTY";
	
	public static final String PRICE_KEY = "PR00_PRICE";
	public static final String PRICE_TYPE_KEY = "PRICE_TYPE";
	public static final String NET_PRICE_KEY = "NET_PRICE";
	public static final String CURRENCY_KEY = "CURRENCY";
	public static final String DELIVERY_UNIT_KEY = "DELIVERY_UNIT";
	public static final String UNIT_WEIGHT_KEY = "UNIT_WEIGHT";
	public static final String BULK_PART_KEY = "BULK_PART";
	public static final String SAFETY_STOCK_KEY = "SAFETY_STOCK";
	public static final String PART_NOT_RETURNABLE_KEY = "PART_NOT_RETURNABLE";
	
	public static final String PLANT_CODE_KEY = "PLANT";
	public static final String INPUT_INDEX_KEY = "INPUT_INDEX";
	
	public static final String SUPERCEDED_PART_KEY = "SUPER_PART";
	
	public static final String FREIGHT_COST_KEY = "FRTCOST";
	public static final String FREIGHT_COST_CURRENCY_CODE_KEY = "WAERK";
	
	// Address related fields
	public static final String NAME_KEY = "NAME1";
	public static final String STREET1_KEY = "STREET1";
	public static final String STREET2_KEY = "STREET2";
	public static final String CITY_KEY = "CITY";
	public static final String STATE_KEY = "STATE";
	public static final String ZIPCODE_KEY = "ZIPCODE";
	public static final String COUNTRY_KEY = "COUNTRY_CODE";
    
	// for some reason, different SAP functions redefine these
	public static final String SHIP_TO_NAME_KEY = "SHIPTO_NAME";
    public static final String SHIP_TO_STREET1_KEY = "SHIPTO_STREET1";
    public static final String SHIP_TO_STREET2_KEY = "SHIPTO_STREET2";
    public static final String SHIP_TO_CITY_KEY = "SHIPTO_CITY";
    public static final String SHIP_TO_STATE_KEY = "SHIPTO_STATE";
    public static final String SHIP_TO_ZIPCODE_KEY = "SHIPTO_ZIP";
    public static final String SHIP_TO_COUNTRY_KEY = "SHIPTO_COUNTRY";
    
	// Order
	public static final String PO_NUMBER_KEY = "PO_NUMBER";
	public static final String PO_TYPE_KEY = "PO_TYPE";
	public static final String ORDER_NUMBER_KEY = "ORDER_NUMBER";   
	public static final String PO_DATE_KEY = "PO_DATE";
	public static final String REQUESTED_DELIVERY_DATE_KEY = "REQUESTED_DELIVERY";
	public static final String FREE_FREIGHT_FLAG_KEY = "FREE_FREIGHT_FLAG";
	public static final String MASTER_ORDER_NUMBER_KEY = "MASTER_ORDER";
	public static final String ORDERER_NAME_KEY = "NAME_OF_ORDERER";

	public static final String EMERGENCY_FLAG_KEY = "EMERGENCY_FLAG";
	public static final String SHIP_CONDITIONS_KEY = "SHIP_CONDITIONS";
	public static final String STOCK_DEFAULT_SHIP_CONDITION = "02";
	public static final String EMERGENCY_DEFAULT_SHIP_CONDITION = "06";
	public static final String PKUP_DEFAULT_SHIP_CONDITION = "11";
	public static final String PKUP_STANDARD_SHIP_CONDITION = "06";
	public static final String PKUP_DELIVERY_SHIP_CONDITION = "08";
	
	public static final String SMALL_PACKAGE_FLAG_KEY = "SMALL_PACKAGE_FLAG";
	public static final String HEADER_TEXT_1_KEY = "HEADER_TEXT_1";
	public static final String HEADER_TEXT_2_KEY = "HEADER_TEXT_2";
	public static final String HEADER_TEXT_3_KEY = "HEADER_TEXT_3";
	public static final String BACKORD_CANCEL_FLAG_KEY = "BACKORD_CANCEL_FLAG";
    public static final String CARRIER_KEY = "CARRIER";
    public static final String INCO_TERMS_KEY = "INCO_TERMS";
    public static final String ROUTE_KEY = "ROUTE";
    public static final String SAP_ORDER_KEY = "SAP_ORDER";

    // Shipment related fields
    public static final String START_DATE_KEY = "START_DATE";
    public static final String END_DATE_KEY = "END_DATE";
    public static final String REQUESTED_STATUS_KEY = "REQUESTED_STATUS";
    public static final String SORT_ORDER_KEY = "SORT_ORDER";
    public static final String MAX_COUNT_KEY = "MAX_COUNT";
    public static final String ORDER_RETURN_FLAG = "ORDER_FLAG";
    public static final String ORDER_RETURN_REASON = "RETURN_ORD_REASON";
    
    public static final String SAP_DATE_FORMAT = "yyyyMMdd";
    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat(SAP_DATE_FORMAT);
    public static final String CUSTOMER_KEY = "CUSTOMER";
    public static final String SHIP_TO_KEY = "SHIP_TO";
    public static final String ORDER_DATE_KEY = "ORDER_DATE";
    public static final String INVOICE_DATE_KEY = "INVOICE_DATE";
    public static final String CANCEL_DATE_KEY = "CANCEL_DATE";
    public static final String REQ_DELIVERY_DATE_KEY = "REQUESTED_DELIVERY";
    
    public static final String ORDER_TYPE_KEY = "ORDER_TYPE";
    public static final String DELIVERY_NUMBER_KEY = "DELIVERY_NUMBER";
    public static final String DELIVERY_KEY = "DELIVERY";
    public static final String TRACKING_STATUS_KEY = "TRACKING_STATUS";
    public static final String SHIP_DATE_KEY = "SHIP_DATE";
    public static final String TRACKING_NUMBER_KEY = "TRACKING_NUMBER";
    public static final String ORDER_METHOD_KEY = "ORDER_METHOD";
    public static final String FREIGHT_TEXT_KEY = "FREIGHT_TEXT";
    public static final String ITEM_NUMBER_KEY = "ITEM_NUMBER";
    public static final String CUSTOMER_MATERIAL_KEY = "CUSTOMER_MATERIAL";
    public static final String SHIPPED_QTY_KEY = "SHIPPED_QTY";
    public static final String ITEM_QUANTITY_KEY = "ITEM_QUANTITY";
    public static final String ORD_PKG_STATUS_KEY = "ORD_PKG_STATUS";
    public static final String ORDERED_BY_KEY = "ORDERED_BY";
    public static final String IN_PROCESS_KEY = "IN_PROCESS";
 
    
    
}
