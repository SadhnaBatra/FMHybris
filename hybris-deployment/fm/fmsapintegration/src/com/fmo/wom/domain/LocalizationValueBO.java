package com.fmo.wom.domain;

import java.io.Serializable;


/**
 * The persistent class for the LOCALIZATION_VALUE database table.
 * 
 */
//Table(name="LOCALIZATION_VALUE")
public class LocalizationValueBO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long valueId;

	private String countryCode;

	private String languageCode;

	private String messageValue;

	//bi-directional many-to-one association to LocalizationKeyBO
	private LocalizationKeyBO localizationKey;

		
    public LocalizationValueBO() {
    }

	public long getValueId() {
		return this.valueId;
	}

	public void setValueId(long valueId) {
		this.valueId = valueId;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getLanguageCode() {
		return this.languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getMessageValue() {
		return this.messageValue;
	}

	public void setMessageValue(String messageValue) {
		this.messageValue = messageValue;
	}

	public LocalizationKeyBO getLocalizationKey() {
		return this.localizationKey;
	}

	public void setLocalizationKey(LocalizationKeyBO localizationKey) {
		this.localizationKey = localizationKey;
	}

	@Override
	public String toString() {
		return "LocalizationValueBO [valueId=" + valueId + ", countryCode="
				+ countryCode + ", languageCode=" + languageCode
				+ ", messageValue=" + messageValue
				+ ", localizationKey=" + localizationKey + "]";
	}

}