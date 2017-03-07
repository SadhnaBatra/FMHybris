package com.fmo.wom.domain;

import java.io.Serializable;

//import javax.persistence.SqlResultSetMapping;
//import javax.persistence.EntityResult;
//import javax.persistence.ColumnResult;

import java.util.List;

/**
 * The persistent class for the LOCALIZATION_KEY database table.
 * 
 */
public class LocalizationKeyBO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long keyId;

	private String baseName;

	private String messageKey;

	// bi-directional one-to-many association to LocalizationValueBO
	private List<LocalizationValueBO> localizationValues;

	public LocalizationKeyBO() {
	}

	public long getKeyId() {
		return this.keyId;
	}

	public void setKeyId(long keyId) {
		this.keyId = keyId;
	}

	public String getBaseName() {
		return this.baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public String getMessageKey() {
		return this.messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public List<LocalizationValueBO> getLocalizationValues() {
		return this.localizationValues;
	}

	public void setLocalizationValues(
			List<LocalizationValueBO> localizationValues) {
		this.localizationValues = localizationValues;
	}

	@Override
	public String toString() {
		return "LocalizationKeyBO [keyId=" + keyId + ", baseName=" + baseName
				+ ", messageKey=" + messageKey + "]";
	}

}