package com.fmo.wom.domain.enums;

import java.util.ArrayList;
import java.util.List;

public enum UploadOrderStatus {

    NEW("NE", false, true),
    FILE_PARSED("FP", false, true), 
    FILE_PARSE_ERROR("FE", false, true), 
    PART_RESOLUTION_ERROR("PE", true, true),
    RESTART_ERROR("RE", true, true),
    ORDER_ERROR("OE", false, false),
    IN_PROGRESS("IP", false, false), 
    PART_RESOLUTION_ISSUE("PI", true, true),
    PARTS_RESOLVED("PR", false, true),
    SUBMITTED("SU", false, false), 
    CANCELLED("CA", false, false),
    // this gets set when something unexpected happens.  Since we don't
    // know the exact state of this order, it is unrestartable and not cancellable
    UNKNOWN_ERROR("UE", false, false);
    
    private String code;
    private boolean restartable;
    private boolean cancelable;
    private static List<UploadOrderStatus> restartableList = null;

    UploadOrderStatus(final String code, final boolean restartable, final boolean cancelable) {
        this.code = code;
        this.restartable = restartable;
        this.cancelable = cancelable;
    }

    public String getCode() {
        return this.code;
    }

    public boolean isRestartable() {
        return restartable;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public static UploadOrderStatus getFromCode(final String aCode) {

        if (aCode == null)
            return null;

        for (UploadOrderStatus aStatus : UploadOrderStatus.values()) {

            if (aStatus.getCode().equals(aCode) == true) {
                return aStatus;
            }
        }

        throw new IllegalStateException("UploadOrderStatus " + aCode + " does not exist.");

    }
    
    public static List<UploadOrderStatus> getAllRestartable() {

        if (restartableList == null) {
            restartableList = new ArrayList<UploadOrderStatus>();
            for (UploadOrderStatus aStatus : UploadOrderStatus.values()) {
                if (aStatus.isRestartable()) {
                    restartableList.add(aStatus);
                }
            }
        }
        return restartableList;
    }
    
   
}
