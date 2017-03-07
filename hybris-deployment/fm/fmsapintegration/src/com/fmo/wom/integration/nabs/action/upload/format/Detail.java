package com.fmo.wom.integration.nabs.action.upload.format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.enums.ExternalSystem;

public class Detail extends Record {
       
    private static final String code = "2"; 
    
    public Detail() {
        setCode(code);
    }
    
    @Override
    public List<String> getFormattedStringList(int transactionId, OrderBO theOrder) {

        List<String> results = new ArrayList<String>();
        
        // go through the items and set the data if its nabs and processable
        for (ItemBO anItem : theOrder.getItemList()) {
            
            // only put this in if we can process it and its for NABS
            if (ExternalSystem.NABS == anItem.getExternalSystem() &&
                anItem.canProcessItem() == true) {
                
                StringBuffer input = new StringBuffer();
                
                input.append(getPaddedCodeValue());
                
                input.append(FieldFormats.PRODUCT_FLAG.getPaddedValue(anItem.getProductFlag()));
                input.append(FieldFormats.PART_NUMBER.getPaddedValue(anItem.getPartNumber()));
                input.append(FieldFormats.FILLER1.getPaddedValue());
                input.append(FieldFormats.QUANTITY.getPaddedValue(anItem.getItemQty().getRequestedQuantity()));
                input.append(FieldFormats.FILLER2.getPaddedValue());
                results.add(input.toString());
            }
        }
        return results;
    }


    private enum FieldFormats {
       
        PRODUCT_FLAG(1, ' '),
        PART_NUMBER(15, ' '), 
        FILLER1(4, ' '),
        QUANTITY(6, '0'),
        FILLER2(13, ' ');
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
        
        public String getPaddedValue(int value) {
            return StringUtil.lpad(value+"", fieldSize, ""+padder);
        }
    }
}
    