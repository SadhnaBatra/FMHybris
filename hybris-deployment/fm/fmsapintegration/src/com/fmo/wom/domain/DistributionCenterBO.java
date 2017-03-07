/*
 * Created on May 31, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.fmo.wom.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.Market;

/**
 * The persistent class for the DISTRIBUTION_CENTER database table.
 * 
 * @author yangx017
 */
@XmlRootElement(name="DistributionCenterBO")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DistributionCenterBO", namespace = "http://webservice.wom.fmo.com/")
public class DistributionCenterBO extends WOMBaseBO implements Serializable{

	private static final long serialVersionUID = 1L;
	public static final String FEDERAL_MOGUL_CODE = "FMO";
	public static final String NOT_FOUND = "DIST CENTER NOT FOUND";

	private long distCenterId;
   
    private String name;
    
    private String code;
	
    private String marketCode;  
    
	private String externalSysCode;  // SAP or NABS
	
	private AddressBO address;
	
	private Double latitude;
	
	private Double longitude;
	
	private Integer distance;
	
	public Date getAvailabilityDate() {
		return availabilityDate; //new SimpleDateFormat("YYYY-MM-dd").format(availabilityDate)
	}

	public void setAvailabilityDate(Date availabilityDate) {
		this.availabilityDate = availabilityDate;
	}

	private boolean participatesInDaylightSavings;
	
    private int timezoneOffset;
	
	private boolean active;
	
	private Date emergencyCutoffTime;
	
	private Date availabilityDate = null;

	private int tscFlag;
	
	private String freightCostCurrencyCode;
	
	private BigDecimal freightCost;
	
	@XmlTransient
    private List<ShippingMethodBO> shippingMethods;   //List of shippingMethodBO
	
	//bi-directional many-to-one association to DistCntrCarrierShipMthdBO
	@XmlTransient
	private List<DcShippingMethodBO> dcShippingMethods;
	
	public DistributionCenterBO() {
	}
	
	public long getDistCenterId() {
        return distCenterId;
    }
	
    public void setDistCenterId(long distCenterId) {
        this.distCenterId = distCenterId;
    }
    
    public String getCode() {
		return code;
	}
    
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
        return name;
    }
	
    public void setName(String name) {
        this.name = name;
    }

    public ExternalSystem getExternalSystem() {
	    if (externalSysCode == null) return null;
		return ExternalSystem.valueOf(externalSysCode.toUpperCase());
	}

    public void setExternalSystem(ExternalSystem externalSystem) {
        externalSysCode = externalSystem.name();
    }
    
	protected String getExternalSysCode() {
        return externalSysCode;
    }
	
	protected void setExternalSysCode(String externalSysCode) {
		this.externalSysCode = externalSysCode;
	}

	public AddressBO getAddress() {
		return address;
	}
	
	public void setAddress(AddressBO address) {
		this.address = address;
	}
	
	public boolean isParticipatesInDaylightSavings() {
		return participatesInDaylightSavings;
	}
	
	public void setParticipatesInDaylightSavings(
			boolean participatesInDaylightSavings) {
		this.participatesInDaylightSavings = participatesInDaylightSavings;
	}
	
	public int getTimezoneOffset() {
		return timezoneOffset;
	}
	
	public void setTimezoneOffset(int timezoneOffset) {
		this.timezoneOffset = timezoneOffset;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getTscFlag() {
		return tscFlag;
	}

	public void setTscFlag(int tscFlag) {
		this.tscFlag = tscFlag;
	}

	public List<ShippingMethodBO> getShippingMethods() {
		return shippingMethods;
	}
	
	public void setShippingMethods(List<ShippingMethodBO> shippingMethods) {
		this.shippingMethods = shippingMethods;
	}
	
	@XmlTransient
	public List<DcShippingMethodBO> getDcShippingMethods() {
		return dcShippingMethods;
	}

	public void setDcShippingMethods(List<DcShippingMethodBO> dcShippingMethods) {
		this.dcShippingMethods = dcShippingMethods;
	}

	public Market getMarketCode() {
        return Market.getFromCode(marketCode);
    }

    public void setMarketCode(Market market) {
        this.marketCode = market.getCode();
    }

    public Date getEmergencyCutoffTime() {
		return emergencyCutoffTime;
	}

	public void setEmergencyCutoffTime(Date emergencyCutoffTime) {
		this.emergencyCutoffTime = emergencyCutoffTime;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public String getFreightCostCurrencyCode() {
		return freightCostCurrencyCode;
	}

	public void setFreightCostCurrencyCode(String freightCostCurrencyCode) {
		this.freightCostCurrencyCode = freightCostCurrencyCode;
	}

	public BigDecimal getFreightCost() {
		return freightCost;
	}

	public void setFreightCost(BigDecimal freightCost) {
		this.freightCost = freightCost;
	}

	@Override
	public String toString() {
		return "DistributionCenterBO [distCenterId=" + distCenterId + ", name="
				+ name + ", code=" + code + ", externalSysCode="
				+ externalSysCode + ", address=" + address
				+ ", participatesInDaylightSavings="
				+ participatesInDaylightSavings + ", timezoneOffset="
				+ timezoneOffset + ", active=" + active 
				+ ", emergencyCutoffTime=" + emergencyCutoffTime 
				+ " availabilityDate = " + availabilityDate 
				+ ", tscFlag=" + tscFlag 
				+ ", freightCostCurrencyCode=" + freightCostCurrencyCode
				+ ", freightCost=" + freightCost
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		
		if(obj != null && obj instanceof DistributionCenterBO){
			return this.getCode().equals(((DistributionCenterBO)obj).getCode());
		}
		return false;
	}
	
}
