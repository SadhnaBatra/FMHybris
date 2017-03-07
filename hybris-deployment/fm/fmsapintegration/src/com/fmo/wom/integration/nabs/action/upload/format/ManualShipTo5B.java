package com.fmo.wom.integration.nabs.action.upload.format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.ManualShipToAddressBO;
import com.fmo.wom.domain.OrderBO;

public class ManualShipTo5B extends Record {
       
    private static final String code = "5B"; 
        
    public ManualShipTo5B() {
        setCode(code);
    }
    
    @Override
    public List<String> getFormattedStringList(int transactionId, OrderBO theOrder) {

        List<String> results = new ArrayList<String>();
        if (theOrder.isManualShipTo() == false) {
            return results;
        }
        
        StringBuffer input = new StringBuffer();
        
        input.append(getPaddedCodeValue());
        
        AddressBO address = theOrder.getManualShipToAddress().getAddress();
        
        input.append(HeaderFieldFormats.CITY.getPaddedValue(address.getCity()));    
        input.append(HeaderFieldFormats.STATE_PROVINCE.getPaddedValue(address.getStateOrProv())); 
        
        String zipOrPostalCode =address.getZipOrPostalCode();
        if (StringUtil.isAValidCanadianPostalCode(zipOrPostalCode) == true) {
        	zipOrPostalCode= StringUtil.getFormattedUpldCanadianPostalCode(zipOrPostalCode);
        }
        
        input.append(HeaderFieldFormats.ZIP_POSTAL_CODE.getPaddedValue(zipOrPostalCode));    
        input.append(HeaderFieldFormats.FILLER1.getPaddedValue(""));    
        input.append(HeaderFieldFormats.COUNTRY_CODE.getPaddedValue(address.getCountry().getIsoCountryCode()));    
        results.add(input.toString());
        
        return results;
    }


    private enum HeaderFieldFormats {
        
        CITY(19, ' '),
        STATE_PROVINCE(2, ' '),
        ZIP_POSTAL_CODE(11, ' '),
        FILLER1(2, ' '),
        COUNTRY_CODE(2, ' ');
        
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

        public String getPaddedValue(Date value) {
            String dateString = "";
            if (value != null) {
                dateString = dateFormat.format(value);
            }
            return StringUtil.stringPad(dateString, padder, fieldSize);
        }
    
    }
}
    