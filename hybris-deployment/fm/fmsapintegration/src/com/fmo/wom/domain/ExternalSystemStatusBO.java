
package com.fmo.wom.domain;

import com.fmo.wom.domain.enums.ExternalSystem;

//Table(name="EXTERNAL_SYSTEM_STATUS")
public class ExternalSystemStatusBO {

  
    private ExternalSystemStatusPK id;

    private StatusCategory statusCategory;

    private String ipoItemStatusCode;
    
    public ExternalSystemStatusPK getId() {
        return id;
    }

    public void setId(ExternalSystemStatusPK id) {
        this.id = id;
    }

    public String getIpoItemStatusCode() {
        return ipoItemStatusCode;
    }

    public void setIpoItemStatusCode(String ipoItemStatusCode) {
        this.ipoItemStatusCode = ipoItemStatusCode;
    }

    public StatusCategory getStatusCategory() {
        return statusCategory;
    }

    public void setStatusCategory(StatusCategory statusCategory) {
        this.statusCategory = statusCategory;
    }


    public String getStatusKey() {
        return getId().getStatusKey();
    }
                    
    public ExternalSystem getExternalSystem() {
        return getId().getExternalSystem();
    }

    public String getStatusCode() {
        return getId().getStatusCode();
    }
    
    public enum StatusCategory {
        
        PART_FOUND,
        PART_MIGRATED,
        PART_NOT_FOUND,
        MULTIPLE_PART_FOUND,
        PART_SETUP_ERROR,
        INVENTORY_ISSUE,
        INTERNAL_DATA_ISSUE,
        DATA_ISSUE,
        ORDER_PROCESSING_ERROR,
        ORDER_METHOD,
        PACKAGE_STATUS,
        ORDER_STATUS,
        INVENTORY_STATUS;
    }

}
