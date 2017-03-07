package com.fmo.wom.integration.util;

import com.fmo.wom.common.util.PropertiesUtil;

public class PartNumberSquasher {

    private static final String INVALIAD_CHARS_SAP_KEY = "invalid.part.characters.sap";
    private static final String INVALIAD_CHARS_SAP_DEFAULT = "";

    private static final String INVALIAD_CHARS_NABS_KEY = "invalid.part.characters.nabs";
    private static final String INVALIAD_CHARS_NABS_DEFAULT = "";

    
    public static String squashSapPartNumber(String sapPartNumber) {
        String invalidCharacters = PropertiesUtil.getOrderProperty(INVALIAD_CHARS_SAP_KEY, INVALIAD_CHARS_SAP_DEFAULT);
        return squashPartNumber(sapPartNumber, invalidCharacters);
    }
    
    public static String squashNabsPartNumber(String nabsPartNumber) {
        String invalidCharacters = PropertiesUtil.getOrderProperty(INVALIAD_CHARS_NABS_KEY, INVALIAD_CHARS_NABS_DEFAULT);
        return squashPartNumber(nabsPartNumber, invalidCharacters);
    }
    
    private static String squashPartNumber(String source, String invalidCharacters) {
        
        // quick input check
        if (invalidCharacters == null || source == null || source.length() <= 0) {
            return source;
        }
        
        StringBuffer newString = new StringBuffer();
        
        for (int i = 0; i < source.length(); i++) {
            if (invalidCharacters.indexOf(source.substring(i, i + 1)) == -1) {
                // didn't find it
                newString.append(source.substring(i, i + 1));
            }
        }
        return newString.toString();
    }
      
}