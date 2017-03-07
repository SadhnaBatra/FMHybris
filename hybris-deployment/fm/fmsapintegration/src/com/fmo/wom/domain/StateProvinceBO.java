package com.fmo.wom.domain;

import java.io.Serializable;

//import javax.persistence.*;

/**
 * The persistent class for the STATE_OR_PROV database table.
 * 
 */
/*//Entity
//Table(name="STATE_OR_PROV")
//NamedQueries({
	//NamedQuery(name = "findStateOrProvinceByCountryCd",
	           	query = "from StateProvinceBO stateProv " +
	           			"where upper(stateProv.countryISOCode) = upper(:cntryCode) " +
	           			"order by stateProv.stateProvName")
})
*/
public class StateProvinceBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Id
	//Column(name="STATE_OR_PROV_CODE")
    private String stateProvCode;
	
	//Column(name="STATE_OR_PROV_NAME")
    private String stateProvName;
	
	//Column(name="ISO_COUNTRY_CODE")
    private String countryISOCode;
	
	public String getStateProvCode() {
		return stateProvCode;
	}

	public void setStateProvCode(String stateProvCode) {
		this.stateProvCode = stateProvCode;
	}

	public String getStateProvName() {
		return stateProvName;
	}

	public void setStateProvName(String stateProvName) {
		this.stateProvName = stateProvName;
	}

	public String getCountryISOCode() {
		return countryISOCode;
	}

	public void setCountryISOCode(String countryISOCode) {
		this.countryISOCode = countryISOCode;
	}	

}
