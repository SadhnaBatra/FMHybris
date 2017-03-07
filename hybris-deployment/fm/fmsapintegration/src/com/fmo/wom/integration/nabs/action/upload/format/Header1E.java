package com.fmo.wom.integration.nabs.action.upload.format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.domain.OrderBO;

public class Header1E extends Record {
       
    private static final String code = "1E"; 
    private static final String TRK = "TRK"; 
    private static final String CUSTOMER_SERVICE_FLAG = "N"; 
           
    public Header1E() {
        setCode(code);
    }
    
    @Override
    public List<String> getFormattedStringList(int transactionId, OrderBO theOrder) {

        List<String> results = new ArrayList<String>();
        
        StringBuffer input = new StringBuffer();
        
        input.append(getPaddedCodeValue());
        
        input.append(FieldFormats.CUSTOMER_SHIP_TO_CODE.getPaddedValue());
        input.append(FieldFormats.REQUIRED_SHIP_DATE.getPaddedValue());
        input.append(FieldFormats.NAPA_PROGRAM_CODE.getPaddedValue());
        input.append(FieldFormats.CUSTOMER_NAME.getPaddedValue());
        input.append(FieldFormats.SHIP_VIA_CODE.getPaddedValue(TRK));
        input.append(FieldFormats.FPRO_SEA_ORDER_NUMBER.getPaddedValue());
        input.append(FieldFormats.LEVEL_DEMAND_DAY.getPaddedValue());
        input.append(FieldFormats.PLANNED_SHIP_DATE.getPaddedValue());
        input.append(FieldFormats.PPP_IND.getPaddedValue());
        input.append(FieldFormats.VENDOR_NUMBER.getPaddedValue());
        input.append(FieldFormats.BACK_ORDER_TYPE.getPaddedValue());
        input.append(FieldFormats.CUSTOMER_SERVICE_HOLD_FLAG.getPaddedValue(CUSTOMER_SERVICE_FLAG));
             
        results.add(input.toString());
        
        return results;
    }


    private enum FieldFormats {
        
        /* Copy book for reference.  WOM doesn't really need to populate most (if not all) 
         * of these fields, but i have coded them for proper future formatting if necessary
         * 
         * TRNSNET-CUST-SHIP-TO-CODE  PIC X(17).  
          05  TRNSNET-REQ-SHIP-DATE   PIC X(6).   
          05  TRNSNET-NAPA-PGM-CODE   PIC X(8).   
          05  TRNSNET-CUST-NAME-DCOPDEMA  PIC X(10).  
          05  TRNSNET-SHIP-VIA-CODE   PIC X(5).   
          05  TRNSNET-FPRO-SFA-ORD-NBR  PIC X(15).  
          05  TRNSNET-LEVEL-DEMAND-DAY  PIC X(2).   
          05  TRNSNET-PLANNED-SHIP-DATE  PIC X(6).   
          05  TRNSNET-PPP-IND         PIC X.      
          05  TRNSNET-850-VENDOR-NBR  PIC X(5).   
          05  TRNSNET-BACKORDER-TYPE  PIC XX.     
          05  TRNSNET-CUST-SRVC-HOLD-FLG PIC X.            
         */
        
        CUSTOMER_SHIP_TO_CODE(17, ' '),
        REQUIRED_SHIP_DATE(6, ' '),
        NAPA_PROGRAM_CODE(8, ' '), 
        CUSTOMER_NAME(10, ' '),
        SHIP_VIA_CODE(5, ' '),          // current TRK
        FPRO_SEA_ORDER_NUMBER(15, ' '), // currently master order num
        LEVEL_DEMAND_DAY(2, ' '),
        PLANNED_SHIP_DATE(6, ' '),
        PPP_IND(1, ' '),
        VENDOR_NUMBER(5, ' '),
        BACK_ORDER_TYPE(2, ' '),
        CUSTOMER_SERVICE_HOLD_FLAG(1, ' ');  // currently N
    
        private int fieldSize;
        private char padder;
        
        private FieldFormats(int fieldSize, char padder) {
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

        public String getPaddedValue(Date value) {
            return StringUtil.stringPad(dateFormat.format(value), padder, fieldSize);
        }
    
        public String getPaddedValue() {
            return StringUtil.stringPad("", padder, fieldSize);
        }
    }
}
    