package com.fmo.wom.integration.nabs.action.backorder;

import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.nabs.action.NabsInputBase;

public class NabsGetBackOrdersInput extends NabsInputBase {

    private String nabsAcctCode;
    
    public NabsGetBackOrdersInput() {;}
    
    public NabsGetBackOrdersInput(String nabsAcctCode) {
    	this.nabsAcctCode = nabsAcctCode;
    	this.transactionCode = NabsConstants.NABS_BACKORDER_PARTS_TRANS_CODE;
    }

	@Override
	public String getInputString() {
        StringBuffer input = new StringBuffer();
        input.append(InputFields.TRANSACTION_CODE.getPaddedValue(this.transactionCode));
        input.append(InputFields.NABS_ACCOUNT_CODE.getPaddedValue(this.nabsAcctCode));
        return input.toString();
	}

    public String getNabsAcctCode() {
        return nabsAcctCode;
    }
    
    private enum InputFields {
        TRANSACTION_CODE     (9, ' '),
        NABS_ACCOUNT_CODE    (5, ' ');
     
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
           return StringUtil.stringPad(value, padder, fieldSize);
       }
       
       public String getPaddedValue(long value) {
           return StringUtil.stringPad(value, fieldSize);
       }
     }
}
