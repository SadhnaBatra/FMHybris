/*
 * Created on Jun 1, 2011
 */
package com.fmo.wom.domain;

/**
 * @author vishws74
 */
public class SalesChannelBO {
	private WOMCodeDescBO salesChannelCodeAndDesc;
	private ExternalSystemBO externalSystem;
	private boolean activeFlag;

	/**
	 * @return Returns the activeFlag.
	 */
	public boolean isActiveFlag() {
		return activeFlag;
	}
	/**
	 * @param activeFlag The activeFlag to set.
	 */
	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
	/**
	 * @return Returns the salesChannelCodeAndDesc.
	 */
	public WOMCodeDescBO getSalesChannelCodeAndDesc() {
		return salesChannelCodeAndDesc;
	}
	/**
	 * @param salesChannelCodeAndDesc The salesChannelCodeAndDesc to set.
	 */
	public void setSalesChannelCodeAndDesc(WOMCodeDescBO salesChannelCodeAndDesc) {
		this.salesChannelCodeAndDesc = salesChannelCodeAndDesc;
	}
	/**
	 * @return Returns the externalSystem.
	 */
	public ExternalSystemBO getExternalSystem() {
		return externalSystem;
	}
	/**
	 * @param externalSystem The externalSystem to set.
	 */
	public void setExternalSystem(ExternalSystemBO externalSystem) {
		this.externalSystem = externalSystem;
	}
}
