package com.fmo.wom.integration.nabs.action.upload.format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.domain.OrderBO;

public class Header extends Record {
       
    private static final String code = "1"; 
    private static final String WOM_UPLOAD = "WU";
    
    public Header() {
        setCode(code);
    }
    
    @Override
    public List<String> getFormattedStringList(int transactionId, OrderBO theOrder) {

        List<String> results = new ArrayList<String>();
        
        StringBuffer input = new StringBuffer();
        
        input.append(getPaddedCodeValue());
        
        input.append(HeaderFieldFormats.CUSTOMER_PURCHASE_ORDER_NUMBER.getPaddedValue(theOrder.getCustPoNbr()));
        input.append(HeaderFieldFormats.FILLER1.getPaddedValue(""));
        input.append(HeaderFieldFormats.CURRENT_DATE_TIME.getPaddedValue(new Date()));
        input.append(HeaderFieldFormats.CREATION_DATE_TIME.getPaddedValue(theOrder.getCreateTimestamp()));
        input.append(HeaderFieldFormats.FILLER2.getPaddedValue(""));
        input.append(HeaderFieldFormats.BILL_TO_ACCOUNT_CODE.getPaddedValue(theOrder.getBillToAcctCd()));
        
        // always need a value here by the looks of it
        if (theOrder.isManualShipTo()) {
            input.append(HeaderFieldFormats.SHIP_TO_ACCOUNT_CODE.getPaddedValue(theOrder.getBillToAcctCd()));
        } else {
            input.append(HeaderFieldFormats.SHIP_TO_ACCOUNT_CODE.getPaddedValue(theOrder.getShipToAcctCd()));
        }
        input.append(HeaderFieldFormats.FILLER3.getPaddedValue(""));
        input.append(HeaderFieldFormats.WOM_UPLOAD.getPaddedValue(WOM_UPLOAD));
        input.append(HeaderFieldFormats.FREE_FREIGHT_INDICATOR.getPaddedValue(theOrder.receivesFreeFreight()));
            
        results.add(input.toString());
        
        return results;
    }


    private enum HeaderFieldFormats {
        
        CUSTOMER_PURCHASE_ORDER_NUMBER(12, ' '),
        FILLER1(11, ' '),
        CURRENT_DATE_TIME(10,' '),
        CREATION_DATE_TIME(10,' '),
        FILLER2(17, ' '),
        BILL_TO_ACCOUNT_CODE(5, ' '),
        SHIP_TO_ACCOUNT_CODE(5, ' '),
        FILLER3(2, ' '),
        WOM_UPLOAD(2, ' '),
        FREE_FREIGHT_INDICATOR(1, ' ');
    
        private int fieldSize;
        private char padder;
        
        private HeaderFieldFormats(int fieldSize, char padder) {
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
        
        public String getPaddedValue(boolean value) {
            return StringUtil.stringPad(value ? "Y" : "N", padder, fieldSize);
        }

        public String getPaddedValue(Date value) {
            String dateString = "";
            if (value != null) {
                dateString = dateFormat.format(value);
            }
            return StringUtil.stringPad(dateString, padder, fieldSize);
        }
    
    }
}
    