package com.fmo.wom.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the BRAND database table.
 * 
 */

public class BrandBO extends WOMBaseBO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long brandId;

	private String typeCode;

	private String brandCode;

	private String brandDesc;

	private String marketCode;

	private boolean display; //yes/no

	private boolean active; //yes/no

	private Date inactiveFromDate;

    public BrandBO() {
    }

	public long getBrandId() {
		return this.brandId;
	}

	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}

	public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public boolean getActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getBrandCode() {
		return this.brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getBrandDesc() {
		return this.brandDesc;
	}

	public void setBrandDesc(String brandDesc) {
		this.brandDesc = brandDesc;
	}

	public boolean getDisplay() {
		return this.display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public Date getInactiveFromDate() {
		return this.inactiveFromDate;
	}

	public void setInactiveFromDate(Date inactiveFromDate) {
		this.inactiveFromDate = inactiveFromDate;
	}

	public String getMarketCode() {
		return this.marketCode;
	}

	public void setMarketCode(String marketCode) {
		this.marketCode = marketCode;
	}

	public enum BrandPrefixType {
        
        QUICK_ORDER("QOE"),
        UPLOAD_ORDER("UPL");
        
        private String code;
        
        private BrandPrefixType(String code) {
            this.code = code;
        }
        
        public String getCode() {
            return code;
        }
    }
}