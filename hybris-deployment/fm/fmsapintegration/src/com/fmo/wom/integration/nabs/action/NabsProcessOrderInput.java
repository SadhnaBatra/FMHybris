package com.fmo.wom.integration.nabs.action;

import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.nabs.action.ProcessOrderAction.CheckoutCommand;

public class NabsProcessOrderInput extends NabsInputBase {

    private String masterOrderNumber;
    private CheckoutCommand command;
    
    private NabsProcessOrderInput() {;}
    
    public NabsProcessOrderInput(String masterOrderNumber, CheckoutCommand command) {
        this.masterOrderNumber = masterOrderNumber;
        this.command = command;
        this.transactionCode = NabsConstants.NABS_PROCESSOR_ORDER_TRANS_CODE;
    }
    
    @Override
    public String getNabsCallID() {
        return getMasterOrderNumber();
    }
    
    public String getMasterOrderNumber() {
        return masterOrderNumber;
    }

    public CheckoutCommand getCommand() {
        return command;
    }

    @Override
    public String getInputString() {
        StringBuffer input = new StringBuffer();
        input.append(InputFields.TRANSACTION_CODE.getPaddedValue(transactionCode));
        //input.append(InputFields.WOM8_IDENTIFIER.getPaddedValue(WOM8_IDENTIFIER));
        input.append(InputFields.MASTER_ORDER_NUMBER.getPaddedValue(masterOrderNumber));
        input.append(InputFields.PURPOSE.getPaddedValue(command.getNabsPurpose()));
        return input.toString();
    }

    private enum InputFields
    {
        TRANSACTION_CODE     (9, ' '),
       // WOM8_IDENTIFIER      (4, ' '),
        MASTER_ORDER_NUMBER  (20, ' '),
        PURPOSE              (15, ' ');
     
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
    
     }
}
