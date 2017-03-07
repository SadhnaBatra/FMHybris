package com.fmo.wom.domain.enums;

import java.util.ArrayList;
import java.util.List;

public enum UploadOrderDetailStatus {
//                                       code   processable resolutionNeeded    resolved    modifiable)
    ERROR                               ("ER",  false,      false,              false,      true), 
    UNRESOLVED                          ("UR",  false,      true,               false,      true), 
    RESOLVED_USER_ACTION_MODIFIABLE     ("RU",  false,      true,               false,      true),
    RESOLVED_USER_ACTION_NOT_MODIFIABLE ("RM",  false,      true,               false,      false),
    RESOLVED_SYSTEM_ACTION_PERFORMED    ("RS",  true,       true,               true,       false), 
    FOUND                               ("FO",  true,       true,               true,       false), 
    SUBMITTED                           ("SU",  false,      false,              true,       false), 
    COMPLETE                            ("CO",  false,      false,              true,       false), 
    REMOVED                             ("RE",  false,      false,              true,       false);

    private String                               code;
    private boolean                              processable;
    private boolean                              resolutionNeeded;
    private boolean                              resolved;
    private boolean                              modifiable;
    private static List<UploadOrderDetailStatus> resultionNeededList = null;
    private static List<UploadOrderDetailStatus> unresolvedList = null;

    UploadOrderDetailStatus(final String code, final boolean processable, final boolean resolutionNeeded, final boolean resolved, final boolean modifiable) {
        this.code = code;
        this.processable = processable;
        this.resolutionNeeded = resolutionNeeded;
        this.resolved = resolved;
        this.modifiable = modifiable;
    }

    public String getCode() {
        return this.code;
    }

    public boolean isProcessable() {
        return processable;
    }

    public boolean isResolutionNeeded() {
        return resolutionNeeded;
    }

    public boolean isResolved() {
        return resolved;
    }

    public boolean isModifiable() {
        return modifiable;
    }

    public static UploadOrderDetailStatus getFromCode(final String aCode) {

        if (aCode == null)
            return null;

        for (UploadOrderDetailStatus aStatus : UploadOrderDetailStatus.values()) {

            if (aStatus.getCode().equals(aCode) == true) {
                return aStatus;
            }
        }

        throw new IllegalStateException("UploadOrderDetailStatus " + aCode + " does not exist.");

    }

    public static List<UploadOrderDetailStatus> getAllResultionNeeded() {

        if (resultionNeededList == null) {
            resultionNeededList = new ArrayList<UploadOrderDetailStatus>();
            for (UploadOrderDetailStatus aStatus : UploadOrderDetailStatus.values()) {
                if (aStatus.isResolutionNeeded()) {
                    resultionNeededList.add(aStatus);
                }
            }
        }
        return resultionNeededList;
    }
    
    public static List<UploadOrderDetailStatus> getAllUnresolved() {

        if (unresolvedList == null) {
            unresolvedList = new ArrayList<UploadOrderDetailStatus>();
            for (UploadOrderDetailStatus aStatus : UploadOrderDetailStatus.values()) {
                if (aStatus.isResolved() == false) {
                    unresolvedList.add(aStatus);
                }
            }
        }
        return unresolvedList;
    }


}
