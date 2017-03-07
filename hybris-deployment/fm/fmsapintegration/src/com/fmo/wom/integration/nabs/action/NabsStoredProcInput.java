package com.fmo.wom.integration.nabs.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.validator.GenericValidator;

import com.fmo.wom.integration.nabs.NabsConstants;

public abstract class NabsStoredProcInput {

    protected abstract CallableStatement createCallableStatement(Connection con) throws SQLException;
    
    protected abstract String getProcedureName();
    
    protected String formatProcCall(String sql) {
    	
        return "{call " + sql + "}";
    }
    
    public static String getNabsDateString(Date aDate) {
    	
        if (aDate == null) return "";
        
        return NabsConstants.futureDateFormatter.format(aDate);
    }
    
    public static Date getNabsDate(String dateAsString) {
        
        if (GenericValidator.isBlankOrNull(dateAsString)) {
            return null;
        }
        
        try {
            return NabsConstants.futureDateFormatter.parse(dateAsString);
        } catch (ParseException e) {
            return null;
        }
    }
    
    public static String getNabsStringInput(String arg) {
    	
        if (arg == null) { 
            return ""; 
        }
        return arg;
    }
    
}
