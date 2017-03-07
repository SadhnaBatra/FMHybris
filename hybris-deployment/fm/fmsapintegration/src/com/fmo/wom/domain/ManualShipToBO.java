package com.fmo.wom.domain;

import com.fmo.wom.domain.WOMBaseBO;
import java.io.Serializable;

/**
 * Defines the entity object associated with the <code>MANUAL_SHIP_TO</code>
 * table.
 * <p>
 * 
 * <pre>
 * Table Name = MANUAL_SHIP_TO
 * 
 * PF 	CART_HEADER_ID        	NUMBER         NOT NULL
 *    	SHIP_TO_NAME          	VARCHAR2 (50)  NOT NULL
 *    	SHIP_TO_ADDRESS1      	VARCHAR2 (50)  NOT NULL
 *    	SHIP_TO_ADDRESS2      	VARCHAR2 (50)          
 *    	SHIP_TO_CITY          	VARCHAR2 (50)  NOT NULL
 *    	SHIP_TO_STATE_PROV    	VARCHAR2 (2)   NOT NULL
 *    	SHIP_TO_ZIP_POSTAL    	VARCHAR2 (10)  NOT NULL
 *    	ISO_COUNTRY_CODE      	VARCHAR2 (2)   NOT NULL
 *    	CREATE_USERID         	VARCHAR2 (10)  NOT NULL
 *    	CREATE_TIMESTAMP      	TIMESTAMP      NOT NULL
 *    	UPDATE_USERID         	VARCHAR2 (10)  NOT NULL
 *    	UPDATE_TIMESTAMP      	TIMESTAMP      NOT NULL
 * </pre>
 * 
 * </p>
 * 
 * @author Jim Miller
 * 
 */
public class ManualShipToBO extends WOMBaseBO implements Serializable {

	private static final long serialVersionUID = 1L;

	public ManualShipToBO() {
		super();
	}

	/**
	 * Identifies MANUAL_SHIP_TO table row in database
	 */
	private long cartHeaderId;

	private String shipToName;

	private AddressBO address;

	/**
	 * Gets the address
	 * 
	 * @return the <code>AddressBO</code> entity containing the Ship To address
	 */
	public AddressBO getAddress() {
		return address;
	}

	/**
	 * Sets the embedded address to the value specified by the
	 * <code>address</code> parameter.
	 * 
	 * @param address
	 *            the <code>AddressBO</code> entity object representing the Ship
	 *            To address
	 */
	public void setAddress(AddressBO address) {
		this.address = address;
	}

	/**
	 * Gets the manual Ship To address line 1
	 * 
	 * @return <code>String</code> value for Ship To addrress line 1.
	 */
	public String getShipToAddress1() {
		String shipToAddress1 = null;
		if (this.address != null) {
			shipToAddress1 = address.getAddrLine1();
		}
		return shipToAddress1;
	}

	/**
	 * Gets the manual Ship To address line 2
	 * 
	 * @return <code>String</code> value for Ship To address line 2.
	 */
	public String getShipToAddress2() {
		String shipToAddress2 = null;
		if (this.address != null) {
			shipToAddress2 = address.getAddrLine2();
		}
		return shipToAddress2;
	}

	/**
	 * Gets the manual Ship To city.
	 * 
	 * @return <code>String</code> value for Ship To city.
	 */
	public String getShipToCity() {
		String shipToCity = null;
		if (this.address != null) {
			shipToCity = address.getCity();
		}
		return shipToCity;
	}

	/**
	 * Gets the manual Ship To state or province
	 * @return <code>String</code> value for Ship To state or province.
	 */
	public String getShipToStateOrProv() {
		String shipToStateOrProv = null;
		if (this.address != null) {
			shipToStateOrProv = address.getStateOrProv();
		}
		return shipToStateOrProv;
	}

	/**
	 * Gets the manual Ship To zip code or postal code. 
	 * @return <code>String</code> value for Ship To zip code or postal code.
	 */
	public String getShipToZipOrPostalCode() {
		String shipToZipOrPostalCode = null;
		if (this.address != null) {
			shipToZipOrPostalCode = address.getZipOrPostalCode();
		}
		return shipToZipOrPostalCode;
	}

	/**
	 * Gets the ISO country code for the manual ship to address.
	 * @return country code value (e.g. US or CA)
	 */
	public String getIsoCountryCode() {
		String isoCountryCode = null;
		if (this.address != null) {
			CountryBO country = address.getCountry();
			if (country != null) {
				isoCountryCode = country.getIsoCountryCode();
			}
		}
		return isoCountryCode;
	}

	/**
	 * Gets the cart header ID
	 * 
	 * @return the <code>long</code> value of the cart header ID
	 */
	public long getCartHeaderId() {
		return cartHeaderId;
	}

	/**
	 * Sets the value of the cart header ID to the value specified by the
	 * <code>cartHeaderId</code> parameter
	 * 
	 * @param cartHeaderId
	 *            <code>long</code> value for ID of cart header
	 */
	public void setCartHeaderId(long cartHeaderId) {
		this.cartHeaderId = cartHeaderId;
	}

	/**
	 * Gets the name field of the ship to address
	 * 
	 * @return <code>String</code> value of ship to name
	 */
	public String getShipToName() {
		return shipToName;
	}

	/**
	 * Sets the name field of the ship to address to the value specified by the
	 * <code>shipToName</code> parameter.
	 * 
	 * @param shipToName
	 *            <code>String</code> value of name field
	 */
	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}

}
