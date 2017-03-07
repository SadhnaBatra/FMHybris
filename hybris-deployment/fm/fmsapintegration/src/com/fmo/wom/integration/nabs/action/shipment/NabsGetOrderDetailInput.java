package com.fmo.wom.integration.nabs.action.shipment;

import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.nabs.action.NabsInputBase;

public class NabsGetOrderDetailInput extends NabsInputBase {

    // These are all the possible inputs for the call to NABS.
    private String accountCode;
    private String orderNumber;
    private String systemOrderNumber;

    public NabsGetOrderDetailInput(String accountCode, String masterOrderNumber, String systemOrderNumber) {

        this.transactionCode = NabsConstants.NABS_SINGLE_ORDER_INQUIRY_TRANS_CODE;
        this.accountCode = accountCode;

        if (masterOrderNumber != null) {
            masterOrderNumber = masterOrderNumber.toUpperCase();
        }
        this.orderNumber = masterOrderNumber;
        this.systemOrderNumber = systemOrderNumber;
    }

    @Override
    public String getInputString() {

        StringBuffer input = new StringBuffer();
        input.append(InputFields.TRANSACTION_CODE.getPaddedValue(transactionCode));
        input.append(InputFields.CUSTOMER_CODE.getPaddedValue(accountCode));
        input.append(InputFields.ORDER_NUMBER.getPaddedValue(orderNumber));
        input.append(InputFields.SYSTEM_ORDER_NUMBER.getPaddedValue(systemOrderNumber));
        return input.toString();
    }

    @Override
    public String getNabsCallID() {
        return systemOrderNumber;
    }
    

    @Override
	public String toString() {
		return "NabsGetOrderDetailInput [accountCode=" + accountCode
				+ ", orderNumber=" + orderNumber + ", systemOrderNumber="
				+ systemOrderNumber + ", transactionCode=" + transactionCode
				+ ", transactionId=" + transactionId + "]";
	}

	private enum InputFields {
        TRANSACTION_CODE        (9, ' '), 
        CUSTOMER_CODE           (5, ' '), 
        ORDER_NUMBER            (15, ' '), 
        SYSTEM_ORDER_NUMBER     (10, ' ');

        private int  fieldSize;
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
            if (value == null)
                value = "";
            if (value.length() > fieldSize) {
                return value.substring(0, fieldSize);
            }

            return StringUtil.stringPad(value, padder, fieldSize);
        }

        public String getPaddedValue(long value) {
            return StringUtil.stringPad(value, fieldSize);
        }

    }
}
