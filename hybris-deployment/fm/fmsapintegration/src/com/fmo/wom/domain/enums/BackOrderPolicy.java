package com.fmo.wom.domain.enums;

import java.util.ArrayList;
import java.util.List;

public enum BackOrderPolicy {
    SHIP_AND_BACKORDER("SB", "B", "Backorder"),
	BACKORDER_ALL("BA", "B", "Backorder All"),
	SHIP_AND_CANCEL("SC", "C", "Cancel"),
	CANCEL_ALL("CA", "CA", "Cancel All"),
	DEFAULT("","","Default");
	 
    private static List<BackOrderPolicy> defaultValues;
	private String dbCode;
    private String code;
    private String displayName;

	BackOrderPolicy(final String dbCode, final String code, final String displayName) {
        this.dbCode = dbCode;
	    this.code = code;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getCode() {
        return this.code;
    }

    public String getDbCode() {
        return dbCode;
    }

    public void setDbCode(String dbCode) {
        this.dbCode = dbCode;
    }

    public static synchronized List<BackOrderPolicy> getDefaultValues() {
        if (defaultValues == null) {
            defaultValues = new ArrayList<BackOrderPolicy>();
            for (BackOrderPolicy aBOP : BackOrderPolicy.values()) {
                if (aBOP == BackOrderPolicy.CANCEL_ALL || aBOP == BackOrderPolicy.DEFAULT ) {
                    continue;
                }
                defaultValues.add(aBOP);
            }
        }
        return defaultValues;
    }
    
    public static BackOrderPolicy getFromCode(final String aCode) {

        if (aCode == null) return null;
        
        for (BackOrderPolicy aBOP : BackOrderPolicy.values()) {

            if (aBOP.getCode().equals(aCode) == true) {
                return aBOP;
            }
        }

        throw new IllegalStateException("BackOrderPolicy " + aCode + " does not exist.");

    }

    public static BackOrderPolicy getFromDBCode(final String aDBCode) {

        if (aDBCode == null) return null;
        
        for (BackOrderPolicy aBOP : BackOrderPolicy.values()) {

            if (aBOP.getDbCode().equals(aDBCode) == true) {
                return aBOP;
            }
        }

        throw new IllegalStateException("BackOrderPolicy " + aDBCode + " does not exist.");

    }
}
