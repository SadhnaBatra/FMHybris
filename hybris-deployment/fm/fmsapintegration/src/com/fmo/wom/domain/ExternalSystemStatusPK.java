package com.fmo.wom.domain;

import java.io.Serializable;

import com.fmo.wom.domain.enums.ExternalSystem;

public class ExternalSystemStatusPK implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    private ExternalSystem externalSystem;

    private String statusKey;

    private String statusCode;

    public ExternalSystemStatusPK() {
    }

    public ExternalSystem getExternalSystem() {
        return externalSystem;
    }

    public void setExternalSystem(ExternalSystem externalSystem) {
        this.externalSystem = externalSystem;
    }


    public String getStatusKey() {
        return statusKey;
    }


    public void setStatusKey(String statusKey) {
        this.statusKey = statusKey;
    }


    public String getStatusCode() {
        return statusCode;
    }


    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }


    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExternalSystemStatusPK)) {
            return false;
        }
        ExternalSystemStatusPK castOther = (ExternalSystemStatusPK) other;
        return this.externalSystem == castOther.externalSystem
                && this.statusKey.equals(castOther.statusKey)
                && this.statusCode.equals(castOther.statusCode);

    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.statusKey.hashCode();
        hash = hash * prime + this.statusCode.hashCode();
        hash = hash * prime + this.externalSystem.hashCode();
        
        return hash;
    }
}