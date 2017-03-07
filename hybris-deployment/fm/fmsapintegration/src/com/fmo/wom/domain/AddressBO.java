/*
 * Created on Jun 1, 2011
 */
package com.fmo.wom.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Business object to hold Address information.
 * 
 * @author vishws74
 */
@XmlAccessorType(XmlAccessType.FIELD)

@XmlType( propOrder={"addrName","addrLine1","addrLine2", "city","stateOrProv"
		,"zipOrPostalCode","country","phoneNum","faxNum","emailAddr"})

public class AddressBO {

	private static final long serialVersionUID = 8855181540756758070L;

	public static final String US_CODE = "US";
    public static final String CANADA_CODE = "CA";
    
	private String addrName;

	private String addrLine1;

	private String addrLine2;

	private String city;

	private String stateOrProv;

	private String zipOrPostalCode;

	private CountryBO country;

	private String phoneNum;

	private String faxNum;
	
	private String emailAddr;

    public AddressBO() {
    }

    public String getAddrName() {
        return addrName;
    }

    public void setAddrName(String addrName) {
        this.addrName = addrName;
    }

    public String getAddrLine1() {
		return this.addrLine1;
	}

	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}

	public String getAddrLine2() {
		return this.addrLine2;
	}

	public void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateOrProv() {
		return this.stateOrProv;
	}

	public void setStateOrProv(String stateOrProv) {
		this.stateOrProv = stateOrProv;
	}

	public String getZipOrPostalCode() {
		return this.zipOrPostalCode;
	}

	public void setZipOrPostalCode(String zipOrPostalCode) {
		this.zipOrPostalCode = zipOrPostalCode;
	}

	public CountryBO getCountry() {
		return country;
	}

	public void setCountry(CountryBO country) {
		this.country = country;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getFaxNum() {
		return faxNum;
	}

	public void setFaxNum(String faxNum) {
		this.faxNum = faxNum;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

    @Override
    public String toString() {
        return "AddressBO [addrName=" + addrName + ", addrLine1=" + addrLine1
                + ", addrLine2=" + addrLine2 + ", city=" + city
                + ", stateOrProv=" + stateOrProv + ", zipOrPostalCode="
                + zipOrPostalCode + ", country=" + country + ", phoneNum="
                + phoneNum + ", faxNum=" + faxNum + ", emailAddr=" + emailAddr
                + "]";
    }

	
}
