package com.fmo.wom.integration.nabs;

import java.text.SimpleDateFormat;

public class NabsConstants {

    public static final String NABS_YES = "Y";
    public static final String NABS_NO = "N";
    
    public static final String NABS_CUSTOMER_EDIT_TRANS_CODE = "DD112";
    public static final String NABS_ORDERABLE_TRANS_CODE = "DD114";
    public static final String NABS_KIT_COMPONENTS_TRANS_CODE = "DD116";
    public static final String NABS_BACKORDER_PARTS_TRANS_CODE = "DD124";
    public static final String NABS_MULTI_ORDER_INQUIRY_TRANS_CODE = "DD123";
    public static final String NABS_SINGLE_ORDER_INQUIRY_TRANS_CODE = "DD126";
    public static final String NABS_PROCESSOR_ORDER_TRANS_CODE = "DD125";
    public static final String NABS_CHECK_INVENTORY_TRANS_CODE = "DD120";
    
    public static final String NABS_SUCCESS = "00";
    public static final String NABS_NO_LINE_ITEMS = "55";
    public static final String NABS_MISSING_REQUIRED_DATA = "73";
    public static final String NABS_CALL_FAILURE = "99";

    public static final String NABS_WOM_ORDER_NOT_FOUND = "90";
    public static final String NABS_INVALID_SHIP_TO = "90";
    public static final String NABS_ACCOUNT_BEING_DELETED = "80";
    public static final String NABS_MISSING_MON = "70";
    public static final String NABS_NO_RESULTS_FOUND = "30";
    
    public static final String NABS_MANUAL_SHIP_TO_CODE = "*MAN*";
    public static final String NABS_BLANK = " ";
    public static final String NABS_DATE_FORMAT = "MMddyyyy";
    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat(NABS_DATE_FORMAT);
    //The date format below is Nabs future order date specific format
    public static final String NABS_FUTURE_DATE_FORMAT = "MM/dd/yyyy";
    public static final SimpleDateFormat futureDateFormatter = new SimpleDateFormat(NABS_FUTURE_DATE_FORMAT);


    // @TODO look into moving these to ENUMS
    
//    public static final String WOM_REQUEST_INITIAL_ASSIGNMENT = "10";
//    public static final String WOM_REQUEST_CHANGE_ORDER       = "20";
//    public static final String WOM_REQUEST_CONFIRM_ORDER      = "30";
//    public static final String WOM_REQUEST_CANCEL_ORDER       = "40";

    // Constants needed for the ADD_OR_REMOVE_IND field.
    public static final String ADD_LINE_ITEM = "A";
    public static final String REMOVE_LINE_ITEM = "R";
    public static final String CHANGE_EXISTING_LINE_ITEM = NABS_BLANK;;
    
    // CHANGE STATUSES
    // CHANGE_STATUS: set for line item change or component item change
    //                to designate this line item should not be processed.
    public static final String LINE_CHANGE_PROCESSED_SUCCESSFULLY = "00";

    /**
     * CHANGE_STATUS: set for line item change or component item change
     *                to designate this line item should be processed.
     */
    public static final String PROCESS_LINE_CHANGE = "10";

    /**
     * CHANGE_STATUS: set to designate component line items should be
     *                processed for a kit instead of kit level processing.
     */
    public static final String PROCESS_COMPONENT_CHANGE = "11";
    
    public static final String HYBRIS_TRANSACTION_IDENTIFIER = "H";
    
    /*When Reading from Oracle DB Sequence*/
    /*public static final String HYBRIS_TRANSACTION_IDENTIFIER = "";*/
    
}
