package com.fmo.wom.integration.nabs.action;

import com.fmo.wom.common.util.StringUtil;

public abstract class NabsInput extends NabsInputBase {

    protected String billToAccountCode;
    protected String shipToAccountCode;
    
    protected NabsInput() {;}
    
    protected NabsInput(String transactionId, String transactionCode, String billToAcctCode, String shipToAcctCode) {
        this.transactionCode = transactionCode;
        this.transactionId = transactionId;
        this.billToAccountCode = billToAcctCode;
        this.shipToAccountCode = shipToAcctCode;
    }
    
    @Override
    public String getInputString() {
        StringBuffer input = new StringBuffer();
        input.append(InputFields.TRANSACTION_CODE.getPaddedValue(transactionCode));
        input.append(InputFields.WOM8_IDENTIFIER.getPaddedValue(WOM8_IDENTIFIER));
        input.append(InputFields.TRANSACTION_ID.getPaddedValue(transactionId));
        input.append(InputFields.BILL_TO_ACCT_CODE.getPaddedValue(billToAccountCode));
        input.append(InputFields.SHIP_TO_ACCT_CODE.getPaddedValue(shipToAccountCode));
        return input.toString();
    }
    
    public String getBillToAccountCode() {
        return billToAccountCode;
    }

    public String getShipToAccountCode() {
        return shipToAccountCode;
    }


    protected enum InputFields
    {
       TRANSACTION_CODE     (9, ' '),
       WOM8_IDENTIFIER      (4, ' '),  
       TRANSACTION_ID       (9, ' '),
       BILL_TO_ACCT_CODE    (5, ' '),
       SHIP_TO_ACCT_CODE    (5, ' ');
     
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
           if (value == null) return null;
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
