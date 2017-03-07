package com.fmo.wom.domain;

public class EconnectPartBO {

	private String partNumber;
	private String ean11;
	private String brandDescription;
	private String application;
	private String productGroup;
	private String catalogReference;

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getEan11() {
		return ean11;
	}

	public void setEan11(String ean11) {
		this.ean11 = ean11;
	}

	public String getBrandDescription() {
		return brandDescription;
	}

	public void setBrandDescription(String brandDescription) {
		this.brandDescription = brandDescription;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public String getCatalogReference() {
		return catalogReference;
	}

	public void setCatalogReference(String catalogReference) {
		this.catalogReference = catalogReference;
	}

	@Override
	public String toString() {
		return "EconPartBO [partNumber=" + partNumber + ", ean11=" + ean11
				+ ", brandDescription=" + brandDescription + ", application="
				+ application + ", productGroup=" + productGroup
				+ ", catalogReference=" + catalogReference + "]";
	}

}
