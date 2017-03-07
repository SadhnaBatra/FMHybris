/*
 * Created on May 31, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.fmo.wom.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

/**
 * The persistent class for the INVENTORY_LOCATION database table.
 * 
 * @author yangx017
 */
//Table(name = "INVENTORY_LOCATION")
public class InventoryBO extends WOMBaseBO implements Serializable {

	private static final long serialVersionUID = 1L;

	private long invntryLocId;

	private long distCntrId;

	private int availableQty;
	
	private boolean selectedLocation; // yes or no

	private DistributionCenterBO distributionCenter;
	
	private boolean isMainDC;

	private int assignedQty;
	
	private ShippingCodeBO shippingCode;

	//bi-directional many-to-one association to ItemBO
	private ItemBO item;
	
    public InventoryBO() {
        selectedLocation = false;
    }

	public long getInvntryLocId() {
		return this.invntryLocId;
	}

	public void setInvntryLocId(long invntryLocId) {
		this.invntryLocId = invntryLocId;
	}

	public long getDistCntrId() {
		return distCntrId;
	}

	public void setDistCntrId(long distCntrId) {
		this.distCntrId = distCntrId;
	}

	public int getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(int availableQty) {
		this.availableQty = availableQty;
	}

	public boolean isSelectedLocation() {
		return selectedLocation;
	}

	public void setSelectedLocation(boolean selectedLocation) {
		this.selectedLocation = selectedLocation;
	}

	public DistributionCenterBO getDistributionCenter() {
		return distributionCenter;
	}

	public void setDistributionCenter(DistributionCenterBO distributionCenter) {
		this.distributionCenter = distributionCenter;
	}

	public boolean isMainDC() {
		return isMainDC;
	}

	public void setMainDC(boolean isMainDC) {
		this.isMainDC = isMainDC;
	}

	public int getAssignedQty() {
		return assignedQty;
	}

	public void setAssignedQty(int assignedQty) {
		this.assignedQty = assignedQty;
	}

	public ItemBO getItem() {
		return item;
	}
	
	public ShippingCodeBO getShippingCode() {
		return shippingCode;
	}

	public void setShippingCode(ShippingCodeBO shippingCode) {
		this.shippingCode = shippingCode;
	}

	@XmlTransient
	public void setItem(ItemBO item) {
		this.item = item;
	}
	/*
	public void afterUnmarshal(Unmarshaller u, Object parent){
		this.item = (ItemBO) parent;
	}*/

	

	@Override
	public String toString() {
		return "InventoryBO [invntryLocId=" + invntryLocId + ", distCntrId="
				+ distCntrId + ", availableQty=" + availableQty
				+ ", selectedLocation=" + selectedLocation
				+ ", distributionCenter=" + distributionCenter+ ", isMainDC="
				+ isMainDC+ ", createUserId="
				+ createUserId + ", createTimestamp=" + createTimestamp
				+ ", updateUserId=" + updateUserId + ", updateTimestamp="
				+ updateTimestamp + "]";
	}


}
