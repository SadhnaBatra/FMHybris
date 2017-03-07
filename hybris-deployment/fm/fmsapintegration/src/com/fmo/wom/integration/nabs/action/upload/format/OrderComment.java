package com.fmo.wom.integration.nabs.action.upload.format;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.GenericValidator;

import com.fmo.wom.domain.OrderBO;

public class OrderComment extends Comment {
       
    private static final String code = "3O"; 
    
    public OrderComment() {
        setCode(code);
    }
    @Override
    public List<String> getFormattedStringList(int transactionId, OrderBO theOrder) {

        List<String> results = new ArrayList<String>();
        if (GenericValidator.isBlankOrNull(theOrder.getComment1())) {
            return results;
        }
        
        List<String> splitData = new ArrayList<String>();
        getSplitStringList(splitData, theOrder.getComment1());
        
        String code = getPaddedCodeValue();
        
        for (String aSplitString : splitData) {
            results.add(code + aSplitString);
        }

        return results;
    }

    
}
    