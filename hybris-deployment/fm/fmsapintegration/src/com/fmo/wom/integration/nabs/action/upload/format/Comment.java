package com.fmo.wom.integration.nabs.action.upload.format;

import java.util.Date;
import java.util.List;

import com.fmo.wom.common.util.StringUtil;

public abstract class Comment extends Record {
       
    protected void getSplitStringList(List<String> results, String inputString) {

        if (inputString == null) return;
        
        if (inputString.length() <= FieldFormats.DATA.getFieldSize()) {
            // less than max, can stop
            results.add(inputString);
            return;
        }
        
        // over the field size.
        results.add(inputString.substring(0, FieldFormats.DATA.getFieldSize()));
        getSplitStringList(results, inputString.substring(FieldFormats.DATA.getFieldSize(), inputString.length()));
    }


    protected enum FieldFormats {
       
        DATA(60, ' ');
        
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
    