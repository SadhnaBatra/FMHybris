package com.fmo.wom.integration.nabs.action.shipment;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.validator.GenericValidator;

import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.domain.enums.GetShipmentOrderStatus;
import com.fmo.wom.domain.enums.OrderSearchFilter;
import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.nabs.action.NabsInputBase;

public class NabsGetShipmentInfoInput extends NabsInputBase {

    // These are all the possible inputs for the call to NABS.
    private String customerCode;
    // Default to all orders
    private GetShipmentOrderStatus orderStatus = GetShipmentOrderStatus.ALL;
    private String purchaseOrderNumber;
    private String orderNumber;
    private Date startDate;
    private Date endDate;
    
    // TODO get from property
    private int recordLimit = 800;
    
    /**
     * Constructor when only master order number and customer PO number are known with default dates.  We'll migrate this
     * for more generic search as necessary
     * @param masterOrderNumber
     * @param customerPONumber
     */
    public NabsGetShipmentInfoInput(String accountCode, String masterOrderNumber, String customerPONumber) {

        // set the dates to go back a year by default and call normal search.
        
        endDate = new Date();
        Calendar endCal = Calendar.getInstance();
        endCal.add(Calendar.DAY_OF_YEAR, 1);
        endDate.setTime(endCal.getTimeInMillis());

        startDate = new Date();
        Calendar startCal = Calendar.getInstance();
        startCal.add(Calendar.YEAR, -1);
        startDate.setTime(startCal.getTimeInMillis());

        init(accountCode, masterOrderNumber, customerPONumber, startDate, endDate, null);
    }
    
    /**
     * Constructor when only master order number and customer PO number are known.  We'll migrate this
     * for more generic search as necessary
     * @param masterOrderNumber
     * @param customerPONumber
     */
    public NabsGetShipmentInfoInput(String accountCode, String masterOrderNumber, String customerPONumber, 
                                    Date startDate, Date endDate, OrderSearchFilter filter) {
       init(accountCode, masterOrderNumber, customerPONumber, startDate, endDate, filter);
    }

    private void init(String accountCode, String masterOrderNumber, String customerPONumber, Date startDate, Date endDate, OrderSearchFilter filter) {
        this.customerCode = accountCode;
        this.transactionCode = NabsConstants.NABS_MULTI_ORDER_INQUIRY_TRANS_CODE;
        this.purchaseOrderNumber = customerPONumber;
        
        if (GenericValidator.isBlankOrNull(masterOrderNumber) == false) {
            masterOrderNumber = masterOrderNumber.toUpperCase();
            // we have a master order number - do not need dates.
            startDate = null;
            endDate = null;
        } else {
            
            // if the master order number is not specified, set the start and end times to 
            // the given values if they're not set.  This will get results, but not execute a search
            // on the entire system
            if (endDate != null) {
                this.endDate = endDate;
            } else {
                this.endDate = new Date();
                Calendar endCal = Calendar.getInstance();
                endCal.add(Calendar.DAY_OF_YEAR, 1);
                this.endDate.setTime(endCal.getTimeInMillis());
            }
            
            if (startDate != null) {
                this.startDate = startDate;
            } else {
                this.startDate = new Date();
                Calendar startCal = Calendar.getInstance();
                startCal.add(Calendar.YEAR, -1);
                this.startDate.setTime(startCal.getTimeInMillis());
            }
        }
        
        this.orderNumber = masterOrderNumber;
        if (filter != null) {
            orderStatus = filter.getShipmentSearchOrderStatus();
        }
    }
 
    @Override
    public String getInputString() {
        StringBuffer input = new StringBuffer();
        input.append(InputFields.TRANSACTION_CODE.getPaddedValue(transactionCode));
        input.append(InputFields.CUSTOMER_CODE.getPaddedValue(customerCode));
        input.append(InputFields.ORDER_STATUS.getPaddedValue(orderStatus.getCode()));
        // let's allow wild carding on long strings
        input.append(InputFields.PURCHASE_ORDER_NUMBER.getWildCardedPaddedValue(purchaseOrderNumber));  
        input.append(InputFields.ORDER_NUMBER.getPaddedValue(orderNumber));  
        input.append(InputFields.START_DATE.getPaddedValue(getNabsDateString(startDate)));
        input.append(InputFields.END_DATE.getPaddedValue(getNabsDateString(endDate)));
        input.append(InputFields.RECORD_LIMIT.getPaddedValue(recordLimit));
        return input.toString();
    }
    
    @Override
    public String getNabsCallID() {
        return customerCode;
    }
    
    private enum InputFields
    {
       TRANSACTION_CODE         (9,  ' '),
       CUSTOMER_CODE            (5,  ' '),
       ORDER_STATUS             (1,  ' '),
       PURCHASE_ORDER_NUMBER    (12, ' '),  
       ORDER_NUMBER             (15, ' '),  
       START_DATE               (8,  ' '),
       END_DATE                 (8,  ' '),
       RECORD_LIMIT             (7,  '0');
       
       private int fieldSize;
       private char padder;
       
       private InputFields(int fieldSize, char padder) {
           this.fieldSize = fieldSize;
           this.padder = padder;
       }
       
       public int getFieldSize() {
           return fieldSize;
       }
       
       public char getPadder() {
           return padder;
       }
       
       public String getPaddedValue(String value) {
           if (value == null) value = "";
           if (value.length() > fieldSize) {
              return value.substring(0, fieldSize);
           }
           
           return StringUtil.stringPad(value, padder, fieldSize);
       }
       
       public String getWildCardedPaddedValue(String value) {
           if (value == null) value = "";
           if (value.length() > fieldSize) {
               StringBuilder truncatedValue = new StringBuilder(value);
               truncatedValue.setLength(fieldSize-1);
               truncatedValue.append("*");
               return truncatedValue.toString();
           }
           
           return StringUtil.stringPad(value, padder, fieldSize);
       }
       
       
       public String getPaddedValue(long value) {
           return StringUtil.stringPad(value, fieldSize);
       }
       
     }

	@Override
	public String toString() {
		return "NabsGetShipmentInfoInput [customerCode=" + customerCode
				+ ", orderStatus=" + orderStatus + ", purchaseOrderNumber="
				+ purchaseOrderNumber + ", orderNumber=" + orderNumber
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", recordLimit=" + recordLimit + ", transactionCode="
				+ transactionCode + ", transactionId=" + transactionId + "]";
	}
    
    
}
